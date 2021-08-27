package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberDao;
import by.dvorkin.web.model.entity.Subscriber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDaoImpl implements SubscriberDao {
    private Connection connection;

    //TODO: создать константы запросов
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(Subscriber subscriber) throws DaoException {
        String sql = "INSERT INTO `subscriber` (`account_id`, `firstname`, `lastname`, `phone_number`, `balance`, " +
                "`tariff`," + "`is_blocked`, `is_registered`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, subscriber.getAccountId());
            statement.setString(2, subscriber.getFirstname());
            statement.setString(3, subscriber.getLastname());
            statement.setString(4, subscriber.getPhoneNumber());
            statement.setInt(5, subscriber.getBalance());
            statement.setLong(6, subscriber.getTariff());
            statement.setBoolean(7, subscriber.isBlocked());
            statement.setBoolean(8, subscriber.isRegistered());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Subscriber subscriber) throws DaoException {

    }

    @Override
    public void delete(Long id) throws DaoException {

    }

    @Override
    public Subscriber read(Long id) throws DaoException {
        return null;
    }

    @Override
    public List<Subscriber> readAll() throws DaoException {
        String sql = "SELECT * FROM `subscriber`";
        return getSubscriberList(sql);
    }

    @Override
    public List<Subscriber> readNewSubscribers() throws DaoException {
        String sql = "SELECT * FROM `subscriber` WHERE  `is_registered` = 0";
        return getSubscriberList(sql);
    }

    private List<Subscriber> getSubscriberList(String sql) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Subscriber> subscribers = new ArrayList<>();
            while (resultSet.next()) {
                subscribers.add(createSubscriber(resultSet));
            }
            return subscribers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Subscriber readByPhoneNumber(String phoneNumber) throws DaoException {
        String sql = "SELECT * FROM `subscriber` WHERE `phone_number` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            Subscriber subscriber = null;
            if (resultSet.next()) {
                subscriber = createSubscriber(resultSet);
            }
            return subscriber;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Subscriber readByAccountId(Long id) throws DaoException {
        String sql = "SELECT * FROM `subscriber` WHERE `account_id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Subscriber subscriber = null;
            if (resultSet.next()) {
                subscriber = createSubscriber(resultSet);
            }
            return subscriber;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void activate(int id) throws DaoException {
        String sql = "UPDATE `subscriber` SET `is_registered` = ?, `is_blocked` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1);
            statement.setInt(2, 0);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Subscriber createSubscriber(ResultSet resultSet) throws SQLException {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(resultSet.getLong("subscriber.id"));
        subscriber.setAccountId(resultSet.getLong("subscriber.account_id"));
        subscriber.setFirstname(resultSet.getString("subscriber.firstname"));
        subscriber.setLastname(resultSet.getString("subscriber.lastname"));
        subscriber.setPhoneNumber(resultSet.getString("subscriber.phone_number"));
        subscriber.setBalance(resultSet.getInt("subscriber.balance"));
        subscriber.setTariff(resultSet.getLong("subscriber.tariff"));
        subscriber.setBlocked(resultSet.getBoolean("subscriber.is_blocked"));
        subscriber.setRegistered(resultSet.getBoolean("subscriber.is_registered"));
        return subscriber;
    }
}
