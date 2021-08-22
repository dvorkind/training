package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Account sessionAccount = (Account) session.getAttribute("sessionAccount");
            Forward forward = getForward(session, sessionAccount);
            if (forward != null) {
                return forward;
            }
        }
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                AccountService accountService = serviceFactory.getAccountService();
                Account account = accountService.login(login, password);
                Forward forward = getForward(session, account);
                if (forward != null) {
                    return forward;
                } else {
                    req.setAttribute("login", login);
                    req.setAttribute("error", "login.message.incorrect.password");
                    return null;
                }
            } catch (ServiceException | FactoryException e) {
                throw new ServletException(e);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private Forward getForward(HttpSession session, Account sessionAccount) {
        if (sessionAccount != null) {
            session.setAttribute("sessionAccount", sessionAccount);
            switch (sessionAccount.getRole()) {
                case ADMINISTRATOR:
                    return new Forward("/admin/admin.html");
                case SUBSCRIBER:
                    return new Forward("/user/user.html");
            }
        }
        return null;
    }
}
