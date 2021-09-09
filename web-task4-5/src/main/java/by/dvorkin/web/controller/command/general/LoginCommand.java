package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession();
        Account sessionAccount = (Account) session.getAttribute("sessionAccount");
        Forward forward = getForward(sessionAccount);
        if (forward != null) {
            return forward;
        }
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {
            Logger logger;
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                AccountService accountService = serviceFactory.getAccountService();
                SubscriberService subscriberService = serviceFactory.getSubscriberService();
                Account account = accountService.login(login, password);
                forward = getForward(account);
                if (forward != null) {
                    session.setAttribute("sessionAccount", account);
                    logger = LogManager.getLogger("User");
                    logger.info("User " + login + " has logged in. IP [" + req.getRemoteAddr() + "]");
                    return forward;
                } else {
                    req.setAttribute("login", login);
                    req.setAttribute("error", "login.messageIncorrectPassword");
                    return null;
                }
            } catch (ServiceException | FactoryException e) {
                throw new ServletException(e);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private Forward getForward(Account sessionAccount) {
        if (sessionAccount != null) {
            switch (sessionAccount.getRole()) {
                case ADMINISTRATOR:
                    return new Forward("/admin/admin.html");
                case SUBSCRIBER:
                    return new Forward("/subscriber/subscriber.html");
            }
        }
        return null;
    }
}
