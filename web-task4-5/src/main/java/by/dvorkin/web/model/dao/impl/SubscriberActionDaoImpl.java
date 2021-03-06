package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.SubscriberActionDao;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.SubscriberAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SubscriberActionDaoImpl implements SubscriberActionDao {
    private static final String CREATE = "INSERT INTO `subscriber_action` (`subscriber_id`, `action`, `date`, `sum`) "
            + "VALUES (?, ?, ?, ?)";
    private static final String READ_LAST_CHANGE_TARIFF = "SELECT * FROM `subscriber_action` WHERE `date` IN (SELECT "
            + "max(date) FROM `subscriber_action` WHERE `subscriber_id` = ? AND `action` = 1)";
    private static final String READ_REGISTRATION_DATE =
            "SELECT * FROM `subscriber_action` WHERE `subscriber_id` = ? AND `action` = 0";
    private static final String READ_BETWEEN_DATES =
            "SELECT * FROM `subscriber_action` WHERE `subscriber_id` = ? AND `date` BETWEEN ? AND ?";
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(SubscriberAction subscriberAction) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, subscriberAction.getSubscriberId());
            statement.setInt(2, subscriberAction.getAction().ordinal());
            statement.setTimestamp(3, Timestamp.valueOf(subscriberAction.getDate()));
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
    public LocalDateTime readLastChangeTariff(Long subscriberId) throws DaoException {
        return getLastDate(subscriberId, READ_LAST_CHANGE_TARIFF);
    }

    @Override
    public LocalDateTime readRegistrationDate(Long subscriberId) throws DaoException {
        return getLastDate(subscriberId, READ_REGISTRATION_DATE);
    }

    private LocalDateTime getLastDate(Long subscriberId, String sql) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, subscriberId);
            ResultSet resultSet = statement.executeQuery();
            LocalDateTime lastDate = null;
            if (resultSet.next()) {
                lastDate = resultSet.getTimestamp("date").toLocalDateTime();
            }
            resultSet.close();
            return lastDate;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<SubscriberAction> readBetweenDates(Long subscriberId, LocalDateTime dateBefore,
                                                   LocalDateTime dateAfter) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BETWEEN_DATES)) {
            statement.setLong(1, subscriberId);
            statement.setTimestamp(2, Timestamp.valueOf(dateBefore.with(LocalTime.MIN)));
            statement.setTimestamp(3, Timestamp.valueOf(dateAfter.with(LocalTime.MAX)));
            ResultSet resultSet = statement.executeQuery();
            List<SubscriberAction> actions = new ArrayList<>();
            while (resultSet.next()) {
                actions.add(createAction(resultSet));
            }
            resultSet.close();
            return actions;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public SubscriberAction read(Long id) {
        return null;
    }

    @Override
    public void update(SubscriberAction subscriberAction) {
    }

    @Override
    public void delete(Long id) {
    }

    private SubscriberAction createAction(ResultSet resultSet) throws SQLException {
        SubscriberAction action = new SubscriberAction();
        action.setId(resultSet.getLong("subscriber_action.id"));
        action.setSubscriberId(resultSet.getLong("subscriber_action.subscriber_id"));
        action.setAction(Action.values()[resultSet.getInt("subscriber_action.action")]);
        action.setDate(resultSet.getTimestamp("subscriber_action.date").toLocalDateTime());
        action.setSum(resultSet.getInt("subscriber_action.sum"));
        return action;
    }
}
