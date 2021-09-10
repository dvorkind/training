package by.dvorkin.web.controller;

import by.dvorkin.web.model.entity.Account;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
        Account sessionAccount = (Account) se.getSession().getAttribute("sessionAccount");
        if (sessionAccount != null) {
            Helper.log("User " + sessionAccount.getLogin() + " has logged out");
        }
    }
}