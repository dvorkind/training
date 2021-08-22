package by.dvorkin.web.controller;

import by.dvorkin.web.model.pool.ConnectionPool;
import by.dvorkin.web.model.pool.ConnectionPoolException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ApplicationInitializerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            String jdbcDriver   = context.getInitParameter("jdbc-driver");
            String jdbcUrl = context.getInitParameter("jdbc-url");
            String jdbcUsername = context.getInitParameter("jdbc-username");
            String jdbcPassword = context.getInitParameter("jdbc-password");
            ConnectionPool.getInstance().init(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword, 5, 10, 0);
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroy();
    }
}
