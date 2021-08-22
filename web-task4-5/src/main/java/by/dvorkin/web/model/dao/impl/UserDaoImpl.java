package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.UserDao;
import by.dvorkin.web.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

public class UserDaoImpl implements UserDao {
    private Connection connection;
    //TODO: создать константы запросов
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(User user) throws DaoException {
        String sql = "INSERT INTO `user` (`account_id`, `firstname`, `lastname`, `phone_number`, `balance`, " +
                "`is_blocked`, `is_registered`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, user.getAccountId());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getPhoneNumber());
            statement.setInt(5, user.getBalance());
            statement.setBoolean(6, user.isBlocked());
            statement.setBoolean(7, user.isRegistered());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    @Override
    public Map<String, User> readAll() throws DaoException {
        String sql = "SELECT * FROM `user` INNER JOIN account ON account_id = account.id WHERE `role` <> 0";
        return getUserMap(sql);
    }

    @Override
    public Map<String, User> readNewUsers() throws DaoException {
        String sql = "SELECT * FROM `user` INNER JOIN account ON account_id = account.id WHERE `role` <> 0 AND " +
                "`is_registered` = 0";
        return getUserMap(sql);
    }

    private Map<String, User> getUserMap(String sql) throws DaoException {
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            Map<String, User> users = new TreeMap<>();
            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.put(resultSet.getString("account.login"), user);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User readByPhoneNumber(String phoneNumber) throws DaoException {
        String sql = "SELECT `id`, `account_id`, `firstname`, `lastname`, `balance`, `is_blocked`, `is_registered` "
                + "FROM `user` WHERE" + " `phone_number` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setAccountId(resultSet.getLong("account_id"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setPhoneNumber(phoneNumber);
                user.setBalance(resultSet.getInt("balance"));
                user.setBlocked(resultSet.getBoolean("is_blocked"));
                user.setRegistered(resultSet.getBoolean("is_registered"));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User readByAccountId(Long id) throws DaoException {
        String sql = "SELECT `id`, `firstname`, `lastname`, `phone_number`, `balance`, `is_blocked`, `is_registered` "
                + "FROM `user` WHERE" + " `account_id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setAccountId(id);
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setBalance(resultSet.getInt("balance"));
                user.setBlocked(resultSet.getBoolean("is_blocked"));
                user.setRegistered(resultSet.getBoolean("is_registered"));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void activate(int id) throws DaoException {
        String sql = "UPDATE `user` SET `is_registered` = ?, `is_blocked` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1);
            statement.setInt(2, 0);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("user.id"));
        user.setAccountId(resultSet.getLong("user.account_id"));
        user.setFirstname(resultSet.getString("user.firstname"));
        user.setLastname(resultSet.getString("user.lastname"));
        user.setPhoneNumber(resultSet.getString("user.phone_number"));
        user.setBalance(resultSet.getInt("user.balance"));
        user.setBlocked(resultSet.getBoolean("user.is_blocked"));
        user.setRegistered(resultSet.getBoolean("user.is_registered"));
        return user;
    }
}
