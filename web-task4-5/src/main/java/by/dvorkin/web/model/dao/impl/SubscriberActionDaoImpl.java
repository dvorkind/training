package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.entity.SubscriberAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class SubscriberActionDaoImpl implements SubscriberActionDao {
    private Connection connection;

    //TODO: создать константы запросов
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(SubscriberAction subscriberAction) throws DaoException {
        String sql = "INSERT INTO `subscriber_action` (`subscriber_id`, `action`, `date`, `sum`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, subscriberAction.getSubscriberId());
            statement.setInt(2, subscriberAction.getAction().ordinal());
            statement.setTimestamp(3, new Timestamp(subscriberAction.getDate().getTime()));
            statement.setInt(4, subscriberAction.getSum());
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
    public Date readLastChangeTariff(Long subscriberId) throws DaoException {
        String sql = "SELECT * FROM `subscriber_action` WHERE `date` IN (SELECT max(date) FROM `subscriber_action` " +
                "WHERE `subscriber_id` = ? AND `action` = 1)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, subscriberId);
            ResultSet resultSet = statement.executeQuery();
            Date lastDate = new java.util.Date();
            if (resultSet.next()) {
                lastDate = new java.util.Date(resultSet.getTimestamp("date").getTime());
            }
            resultSet.close();
            return lastDate;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public SubscriberAction read(Long id) throws DaoException {
        return null;
    }

    @Override
    public void update(SubscriberAction subscriberAction) throws DaoException {

    }

    @Override
    public void delete(Long id) throws DaoException {

    }
}
