package by.dvorkin.web.model.dao.impl;

import by.dvorkin.web.model.dao.DaoException;
import by.dvorkin.web.model.dao.ServiceDao;
import by.dvorkin.web.model.entity.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoImpl implements ServiceDao {
    private Connection connection;

    //TODO: создать константы запросов
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(Service service) throws DaoException {
        String sql = "INSERT INTO `service` (`name`, `description`, `price`, `is_deleted`) " + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setInt(3, service.getPrice());
            statement.setInt(4, 0);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Service service) throws DaoException {
        String sql = "UPDATE `service` SET `name` = ?, `description` = ?, `price` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setInt(3, service.getPrice());
            statement.setLong(4, service.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "UPDATE `service` SET `is_deleted` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, 1);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        sql = "DELETE FROM `subscriber_service` WHERE `service_id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Service read(Long id) throws DaoException {
        String sql = "SELECT * FROM `service` WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Service service = null;
            if (resultSet.next()) {
                service = createService(resultSet);
            }
            return service;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Service> readAll() throws DaoException {
        String sql = "SELECT * FROM `service` WHERE `is_deleted` = 0";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Service> services = new ArrayList<>();
            while (resultSet.next()) {
                services.add(createService(resultSet));
            }
            return services;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Service readByName(String serviceName) throws DaoException {
        String sql = "SELECT * FROM `service` WHERE `name` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, serviceName);
            ResultSet resultSet = statement.executeQuery();
            Service service = null;
            if (resultSet.next()) {
                service = createService(resultSet);
            }
            return service;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Service createService(ResultSet resultSet) throws SQLException {
        Service service = new Service();
        service.setId(resultSet.getLong("id"));
        service.setName(resultSet.getString("name"));
        service.setDescription(resultSet.getString("description"));
        service.setPrice(resultSet.getInt("price"));
        return service;
    }
}
