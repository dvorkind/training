package by.dvorkin.web.controller;

import by.dvorkin.web.model.entity.Account;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
        Account sessionAccount = (Account) se.getSession().getAttribute("sessionAccount");
        if (sessionAccount != null) {
            Logger logger = LogManager.getLogger("User");
            logger.info("User " + sessionAccount.getLogin() + " has logged out by the system");
        }
    }
}