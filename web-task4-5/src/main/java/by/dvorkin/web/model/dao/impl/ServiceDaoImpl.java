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
    private static final String SWITCH_OFF = "DELETE FROM `subscriber_service` WHERE `service_id` = ? AND " +
            "`subscriber_id` = ?";
    private static final String SWITCH_ON = "INSERT INTO `subscriber_service` (`subscriber_id`, `service_id`) " +
            "VALUES (?, ?)";
    private static final String READ_BY_NAME = "SELECT * FROM `service` WHERE `is_deleted` = 0 AND BINARY `name` = ?";
    private static final String READ_SUBSCRIBERS_SERVICE =
            "SELECT * FROM `service` INNER JOIN `subscriber_service` ON service.id = service_id  WHERE " +
                    "`subscriber_id` = ?";
    private static final String READ_ALL = "SELECT * FROM `service` WHERE `is_deleted` = 0";
    private static final String READ = "SELECT * FROM `service` WHERE `id` = ? AND `is_deleted` = 0";
    private static final String CREATE = "INSERT INTO `service` (`name`, `description`, `price`, `is_deleted`) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE `service` SET `name` = ?, `description` = ?, `price` = ? WHERE `id` "
            + "= ?";
    private static final String DELETE = "UPDATE `service` SET `is_deleted` = ? WHERE `id` = ?";
    private static final String DELETE_SUBSCRIBER_SERVICE = "DELETE FROM `subscriber_service` WHERE `service_id` = ?";
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long create(Service service) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setInt(3, service.getPrice());
            statement.setInt(4, 0);
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
    public void update(Service service) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, 1);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        try (PreparedStatement statement = connection.prepareStatement(DELETE_SUBSCRIBER_SERVICE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Service read(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(READ)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Service service = null;
            if (resultSet.next()) {
                service = createService(resultSet);
            }
            resultSet.close();
            return service;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Service> readAll() throws DaoException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(READ_ALL);
            List<Service> services = new ArrayList<>();
            while (resultSet.next()) {
                services.add(createService(resultSet));
            }
            resultSet.close();
            return services;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Service> readSubscribersService(Long subscriberId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(READ_SUBSCRIBERS_SERVICE)) {
            statement.setLong(1, subscriberId);
            ResultSet resultSet = statement.executeQuery();
            List<Service> services = new ArrayList<>();
            while (resultSet.next()) {
                services.add(createService(resultSet));
            }
            resultSet.close();
            return services;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Service readByName(String serviceName) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_NAME)) {
            statement.setString(1, serviceName);
            ResultSet resultSet = statement.executeQuery();
            Service service = null;
            if (resultSet.next()) {
                service = createService(resultSet);
            }
            resultSet.close();
            return service;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void switchOn(Long subscriberId, Long serviceId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SWITCH_ON)) {
            statement.setLong(1, subscriberId);
            statement.setLong(2, serviceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void switchOff(Long subscriberId, Long serviceId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SWITCH_OFF)) {
            statement.setLong(1, serviceId);
            statement.setLong(2, subscriberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Service createService(ResultSet resultSet) throws SQLException {
        Service service = new Service();
        service.setId(resultSet.getLong("service.id"));
        service.setName(resultSet.getString("service.name"));
        service.setDescription(resultSet.getString("service.description"));
        service.setPrice(resultSet.getInt("service.price"));
        return service;
    }
}
