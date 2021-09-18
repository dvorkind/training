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
    private static final String CREATE = "INSERT INTO `subscriber` (`account_id`, `firstname`, `lastname`, " +
            "`phone_number`, `balance`, `tariff`, `is_blocked`, `is_registered`, `is_deleted`) VALUES (?, ?, ?, " +
            "?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE `subscriber` SET `firstname` = ?, `lastname` = ?, `phone_number` = " +
            "?, `balance` = ?, `tariff` = ?, `is_blocked` = ?, `is_registered` = ? WHERE `id` = ?";
    private static final String DELETE = "UPDATE `subscriber` SET `is_deleted` = ? WHERE `id` = ?";
    private static final String READ = "SELECT * FROM `subscriber` WHERE `id` = ?";
    private static final String READ_BY_ACCOUNT_ID = "SELECT * FROM `subscriber` WHERE `account_id` = ?";
    private static final String READ_ALL = "SELECT * FROM `subscriber` WHERE `is_deleted` = 0";
    private static final String READ_NEW_SUBSCRIBERS = "SELECT * FROM `subscriber` WHERE  `is_registered` = 0 AND " +
            "`is_deleted` = 0";
    private static final String READ_DEBTORS = "SELECT * FROM `subscriber` WHERE  `balance` < 0 AND `is_deleted` = 0";
    private static final String READ_BY_PHONE_NUMBER = "SELECT * FROM `subscriber` WHERE `phone_number` = ?";
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(Subscriber subscriber) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, subscriber.getAccountId());
            statement.setString(2, subscriber.getFirstname());
            statement.setString(3, subscriber.getLastname());
            statement.setString(4, subscriber.getPhoneNumber());
            statement.setInt(5, subscriber.getBalance());
            statement.setLong(6, subscriber.getTariff());
            statement.setBoolean(7, subscriber.isBlocked());
            statement.setBoolean(8, subscriber.isRegistered());
            statement.setInt(9, 0);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long result = resultSet.getLong(1);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Subscriber subscriber) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, subscriber.getFirstname());
            statement.setString(2, subscriber.getLastname());
            statement.setString(3, subscriber.getPhoneNumber());
            statement.setInt(4, subscriber.getBalance());
            statement.setLong(5, subscriber.getTariff());
            statement.setBoolean(6, subscriber.isBlocked());
            statement.setBoolean(7, subscriber.isRegistered());
            statement.setLong(8, subscriber.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, 1);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Subscriber read(Long id) throws DaoException {
        return getSubscriber(id, READ);
    }

    @Override
    public Subscriber readByAccountId(Long id) throws DaoException {
        return getSubscriber(id, READ_BY_ACCOUNT_ID);
    }

    @Override
    public List<Subscriber> readAll() throws DaoException {
        return getSubscriberList(READ_ALL);
    }

    @Override
    public List<Subscriber> readNewSubscribers() throws DaoException {
        return getSubscriberList(READ_NEW_SUBSCRIBERS);
    }

    @Override
    public List<Subscriber> readDebtors() throws DaoException {
        return getSubscriberList(READ_DEBTORS);
    }

    @Override
    public Subscriber readByPhoneNumber(String phoneNumber) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_PHONE_NUMBER)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            Subscriber subscriber = null;
            if (resultSet.next()) {
                subscriber = createSubscriber(resultSet);
            }
            resultSet.close();
            return subscriber;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<Subscriber> getSubscriberList(String sql) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Subscriber> subscribers = new ArrayList<>();
            while (resultSet.next()) {
                subscribers.add(createSubscriber(resultSet));
            }
            resultSet.close();
            return subscribers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Subscriber getSubscriber(Long id, String sql) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Subscriber subscriber = null;
            if (resultSet.next()) {
                subscriber = createSubscriber(resultSet);
            }
            resultSet.close();
            return subscriber;
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
