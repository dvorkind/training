package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.exceptions.AccountNotExistException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotExistException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ResetPasswordCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        Account sessionAccount = (Account) session.getAttribute("sessionAccount");
        if (sessionAccount != null) {
            return new Forward("/login.html");
        }
        if (isInputValid(req)) {
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                AccountService accountService = serviceFactory.getAccountService();
                String login = req.getParameter("login");
                session.setAttribute("sessionAccount", accountService.resetPassword(login, req.getParameter(
                        "phoneNumber")));
                Helper.log("User " + login + " reset password. IP [" + req.getRemoteAddr() + "]");
                session.setAttribute("success", "resetPassword.success");
                return new Forward("/success.html");
            } catch (AccountNotExistException | SubscriberNotExistException e) {
                req.removeAttribute("loginIsValid");
                req.removeAttribute("phoneNumberIsValid");
                req.setAttribute("loginError", "resetPassword.error");
                req.setAttribute("phoneNumberError", "resetPassword.error");
                return null;
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
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
