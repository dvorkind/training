package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.AccountDao;
import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDaoImpl implements AccountDao {
    private Connection connection;

    //TODO: создать константы запросов
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account readByLogin(String login) throws DaoException {
        String sql = "SELECT * FROM `account` WHERE BINARY `login` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                account = createAccount(resultSet);
            }
            resultSet.close();
            return account;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Account readByLoginAndPassword(String login, String password) throws DaoException {
        String sql = "SELECT * FROM `account` WHERE BINARY `login` = ? AND `password` = ? AND `is_deleted` = 0";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, login);
            statement.setString(2, passwordToSHA(password));
            ResultSet resultSet = statement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                account = createAccount(resultSet);
            }
            resultSet.close();
            return account;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long create(Account account) throws DaoException {
        String sql = "INSERT INTO `account` (`login`, `password`, `role`, `is_deleted`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, passwordToSHA(account.getPassword()));
            statement.setInt(3, account.getRole()
                    .ordinal());
            statement.setInt(4,0);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            account.setPassword(passwordToSHA(account.getPassword()));
            Long result = resultSet.getLong(1);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Account account) throws DaoException {
        String sql = "UPDATE `account` SET `login` = ?, `password` = ?, `role` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getRole()
                    .ordinal());
            statement.setLong(4, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "UPDATE `account` SET `is_deleted` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, 1);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Account read(Long id) throws DaoException {
        String sql = "SELECT * FROM `account` WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                account = createAccount(resultSet);
            }
            resultSet.close();
            return account;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public String passwordToSHA(String sourcePassword) {
        MessageDigest messageDigest;
        byte[] bytesEncoded = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(sourcePassword.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
        } catch (NoSuchAlgorithmException ignored) {
        }
        BigInteger bigInt;
        if (bytesEncoded != null) {
            bigInt = new BigInteger(1, bytesEncoded);
            return bigInt.toString(16);
        }
        return sourcePassword;
    }

    private Account createAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setLogin(resultSet.getString("login"));
        account.setPassword(resultSet.getString("password"));
        account.setRole(Role.values()[resultSet.getInt("role")]);
        return account;
    }
}
