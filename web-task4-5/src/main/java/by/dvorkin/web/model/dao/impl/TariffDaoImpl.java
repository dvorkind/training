package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.TariffDao;
import by.dvorkin.web.model.entity.Tariff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TariffDaoImpl implements TariffDao {
    private Connection connection;

    //TODO: создать константы запросов
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(Tariff tariff) throws DaoException {
        String sql = "INSERT INTO `tariff` (`name`, `description`, `subscription_fee`, `call_cost`, `sms_cost`, `is_deleted`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tariff.getName());
            statement.setString(2, tariff.getDescription());
            statement.setInt(3, tariff.getSubscriptionFee());
            statement.setInt(4, tariff.getCallCost());
            statement.setInt(5, tariff.getSmsCost());
            statement.setInt(6, 0);
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
    public void update(Tariff tariff) throws DaoException {
        String sql = "UPDATE `tariff` SET `name` = ?, `description` = ?, `subscription_fee` = ?, `call_cost` = ?, " +
                "`sms_cost` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tariff.getName());
            statement.setString(2, tariff.getDescription());
            statement.setInt(3, tariff.getSubscriptionFee());
            statement.setInt(4, tariff.getCallCost());
            statement.setInt(5, tariff.getSmsCost());
            statement.setLong(6, tariff.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "UPDATE `tariff` SET `is_deleted` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, 1);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Tariff read(Long id) throws DaoException {
        String sql = "SELECT * FROM `tariff` WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Tariff tariff = null;
            if (resultSet.next()) {
                tariff = createTariff(resultSet);
            }
            resultSet.close();
            return tariff;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Tariff> readAll() throws DaoException {
        String sql = "SELECT * FROM `tariff` WHERE `is_deleted` = 0";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Tariff> tariffs = new ArrayList<>();
            while (resultSet.next()) {
                tariffs.add(createTariff(resultSet));
            }
            resultSet.close();
            return tariffs;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Tariff readByName(String tariffName) throws DaoException {
        String sql = "SELECT * FROM `tariff` WHERE BINARY `name` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tariffName);
            ResultSet resultSet = statement.executeQuery();
            Tariff tariff = null;
            if (resultSet.next()) {
                tariff = createTariff(resultSet);
            }
            resultSet.close();
            return tariff;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isLastTariff() throws DaoException {
        String sql = "SELECT COUNT(*) AS tariffs FROM `tariff` WHERE `is_deleted` = 0";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int result = resultSet.getInt("tariffs");
                resultSet.close();
                return result == 1;
            }
            resultSet.close();
            return false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int readCountUsingTariff(Long id) throws DaoException {
        String sql = "SELECT COUNT(*) AS subscribers FROM `subscriber` WHERE `tariff` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int result = resultSet.getInt("subscribers");
                resultSet.close();
                return result;
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void switchTariffs(Long sourceId, Long destinationId) throws DaoException {
        String sql = "UPDATE subscriber SET tariff = ? WHERE tariff = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, destinationId);
            statement.setLong(2, sourceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Tariff createTariff(ResultSet resultSet) throws SQLException {
        Tariff tariff = new Tariff();
        tariff.setId(resultSet.getLong("id"));
        tariff.setName(resultSet.getString("name"));
        tariff.setDescription(resultSet.getString("description"));
        tariff.setSubscriptionFee(resultSet.getInt("subscription_fee"));
        tariff.setCallCost(resultSet.getInt("call_cost"));
        tariff.setSmsCost(resultSet.getInt("sms_cost"));
        return tariff;
    }
}
