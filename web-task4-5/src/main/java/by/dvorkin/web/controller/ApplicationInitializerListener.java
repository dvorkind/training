package by.dvorkin.web.controller;

import by.dvorkin.web.model.pool.ConnectionPool;
import by.dvorkin.web.model.pool.ConnectionPoolException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationInitializerListener implements ServletContextListener {
    private Logger logger = LogManager.getLogger("Application");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            String jdbcDriver = context.getInitParameter("jdbc-driver");
            String jdbcUrl = context.getInitParameter("jdbc-url");
            String jdbcUsername = context.getInitParameter("jdbc-username");
            String jdbcPassword = context.getInitParameter("jdbc-password");
            ConnectionPool.getInstance().init(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword, 5, 10, 0);
            logger.info("APPLICATION START");
        } catch (ConnectionPoolException e) {
            Logger loggerError = LogManager.getLogger();
            loggerError.error("Connection pool " + e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("APPLICATION FINISH");
        ConnectionPool.getInstance().destroy();
    }
}
