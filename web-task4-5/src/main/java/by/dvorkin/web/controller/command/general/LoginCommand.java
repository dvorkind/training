package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.exceptions.AccountNotExistException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        Account sessionAccount = (Account) session.getAttribute("sessionAccount");
        Forward forward = getForward(sessionAccount);
        if (forward != null) {
            return forward;
        }
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                AccountService accountService = serviceFactory.getAccountService();
                Account account = accountService.login(login, password);
                forward = getForward(account);
                session.setAttribute("sessionAccount", account);
                Helper.log("User " + login + " has logged in. IP [" + req.getRemoteAddr() + "]");
                return forward;
            } catch (AccountNotExistException e) {
                req.setAttribute("login", login);
                req.setAttribute("error", "login.messageIncorrectPassword");
                return null;
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        return null;
    }

    private Forward getForward(Account sessionAccount) {
        if (sessionAccount != null) {
            if (sessionAccount.getRole() == Role.ADMINISTRATOR) {
                return new Forward("/admin/admin.html");
            } else {
                return new Forward("/subscriber/subscriber.html");
            }
        }
        return null;
    }
}
