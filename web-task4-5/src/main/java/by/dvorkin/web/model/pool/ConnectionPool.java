package by.dvorkin.web.model.pool;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public final class ConnectionPool {
    private static ConnectionPool instance = new ConnectionPool();
    private final Queue<Connection> freeConnections = new ConcurrentLinkedQueue<>();
    private final Set<Connection> usedConnections =
            new ConcurrentSkipListSet<>(Comparator.comparingInt(Object::hashCode));
    private String jdbcUrl;
    private String user;
    private String password;
    private Driver driver;
    private int validationConnectionTimeout;
    private int maxSizeConnections;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void init(String jdbcDriver, String jdbcUrl, String user, String password, int minSizeConnections,
                     int maxSizeConnections, int validationConnectionTimeout) throws ConnectionPoolException {
        try {
            destroy();
            driver = (Driver) Class.forName(jdbcDriver).getConstructor().newInstance();
            DriverManager.registerDriver(driver);
            this.jdbcUrl = jdbcUrl;
            this.user = user;
            this.password = password;
            this.validationConnectionTimeout = validationConnectionTimeout;
            this.maxSizeConnections = maxSizeConnections;
            for (int i = 0; i < minSizeConnections; i++) {
                freeConnections.add(newConnection());
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        while (connection == null) {
            connection = freeConnections.poll();
            try {
                if (connection != null) {
                    if (!connection.isValid(validationConnectionTimeout)) {
                        try {
                            connection.close();
                        } catch (SQLException ignored) {
                        }
                        connection = null;
                    }
                } else {
                    if (usedConnections.size() < maxSizeConnections) {
                        connection = newConnection();
                    } else {
                        throw new ConnectionPoolException("The database connections amount is over limited");
                    }
                }
            } catch (SQLException e) {
                throw new ConnectionPoolException(e);
            }
        }
        usedConnections.add(connection);
        return new ConnectionWrapper(connection);
    }

    void freeConnection(Connection connection) {
        try {
            connection.clearWarnings();
            usedConnections.remove(connection);
            freeConnections.add(connection);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void destroy() {
        synchronized (freeConnections) {
            synchronized (usedConnections) {
                usedConnections.addAll(freeConnections);
                freeConnections.clear();
                for (Connection connection : usedConnections) {
                    try {
                        connection.close();
                    } catch (SQLException ignored) {
                    }
                }
                usedConnections.clear();
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ignored) {
                }
            }
        }
    }

    private Connection newConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }
}
