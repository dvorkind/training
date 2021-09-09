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

public class ResetPasswordCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession();
        Account sessionAccount = (Account) session.getAttribute("sessionAccount");
        if (sessionAccount != null) {
            switch (sessionAccount.getRole()) {
                case ADMINISTRATOR:
                    return new Forward("/admin/admin.html");
                case SUBSCRIBER:
                    return new Forward("/subscriber/subscriber.html");
            }
        }
        if (isInputValid(req)) {
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                AccountService accountService = serviceFactory.getAccountService();
                Account account = accountService.getByLogin(req.getParameter("login"));
                if (account != null) {
                    SubscriberService subscriberService = serviceFactory.getSubscriberService();
                    Subscriber subscriber = subscriberService.getByPhoneNumber(req.getParameter("phoneNumber"));
                    if (subscriber != null && subscriber.getAccountId().equals(account.getId())) {
                        session.setAttribute("sessionAccount", accountService.resetPassword(account));
                        Logger logger = LogManager.getLogger("User");
                        logger.info("User " + account.getLogin() + " reset password. IP [" + req.getRemoteAddr() + "]");
                        session.setAttribute("success", "resetPassword.success");
                        return new Forward("/subscriber/success.html");
                    }
                }
                req.setAttribute("loginError", "resetPassword.error");
                req.setAttribute("phoneNumberError", "resetPassword.error");
            } catch (ServiceException | FactoryException e) {
                throw new ServletException(e);
            } catch (Exception ignored) {
            }
        }
        req.removeAttribute("loginIsValid");
        req.removeAttribute("phoneNumberIsValid");
        return null;
    }

    private boolean isInputValid(HttpServletRequest req) {
        return isLoginValid(req) & isPhoneValid(req);
    }

    private boolean isPhoneValid(HttpServletRequest req) {
        String phoneNumber = req.getParameter("phoneNumber");
        if (phoneNumber == null) {
            return false;
        } else {
            phoneNumber = phoneNumber.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\(", "")
                    .replaceAll("\\)", "");
            req.setAttribute("phoneNumber", phoneNumber);
            if (phoneNumber.trim().equals("")) {
                req.setAttribute("phoneNumberError", "resetPassword.errorEmpty");
                return false;
            }
        }
        req.setAttribute("phoneNumberIsValid", true);
        return true;
    }

    private boolean isLoginValid(HttpServletRequest req) {
        String login = req.getParameter("login");
        req.setAttribute("login", login);
        if (login == null) {
            return false;
        } else {
            if (login.trim().equals("")) {
                req.setAttribute("loginError", "resetPassword.errorEmpty");
                return false;
            }
        }
        req.setAttribute("loginIsValid", true);
        return true;
    }
}
