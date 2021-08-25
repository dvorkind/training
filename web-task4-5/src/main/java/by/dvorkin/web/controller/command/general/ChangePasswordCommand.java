package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.exceptions.AccountPasswordIncorrectException;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangePasswordCommand implements Command {
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9~!@#$%^&*()-_=+'/|.]{5,20}$";

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession(false);
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        if (session != null) {
            Account account = (Account) session.getAttribute("sessionAccount");
            if (account != null && isOldPasswordValid(req) & isNewPasswordValid(req) & isConfirmedNewPassword(req)) {
                try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                    AccountService accountService = serviceFactory.getAccountService();
                    accountService.changePassword(oldPassword, newPassword, account);
                    req.setAttribute("success", "changePassword.success");
                    Logger logger = LogManager.getLogger("User");
                    logger.info("User " + account.getLogin() + " changed the password. IP [" + req.getRemoteAddr() + "]");
                    return null;
                } catch (FactoryException e) {
                    throw new ServletException(e);
                } catch (AccountPasswordIncorrectException e) {
                    req.setAttribute("oldPasswordError", "changePassword.errorOldPassword");
                    return null;
                } catch (Exception ignored) {
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private boolean isOldPasswordValid(HttpServletRequest req) {
        String password = req.getParameter("oldPassword");
        req.setAttribute("oldPassword", password);
        if (password == null) {
            return false;
        } else {
            if (password.trim()
                    .equals("")) {
                req.setAttribute("oldPasswordError", "changePassword.errorEmpty");
                return false;
            }
            if (!password.matches(PASSWORD_REGEX)) {
                req.setAttribute("oldPasswordError", "changePassword.errorPassword");
                return false;
            }
        }
        req.setAttribute("oldPasswordIsValid", true);
        return true;
    }

    private boolean isNewPasswordValid(HttpServletRequest req) {
        String password = req.getParameter("newPassword");
        req.setAttribute("newPassword", password);
        if (password == null) {
            return false;
        } else {
            if (password.trim()
                    .equals("")) {
                req.setAttribute("newPasswordError", "changePassword.errorEmpty");
                return false;
            }
            if (!password.matches(PASSWORD_REGEX)) {
                req.setAttribute("newPasswordError", "changePassword.errorPassword");
                return false;
            }
        }
        req.setAttribute("newPasswordIsValid", true);
        return true;
    }

    private boolean isConfirmedNewPassword(HttpServletRequest req) {
        String newPassword = req.getParameter("newPassword");
        String confirmedNewPassword = req.getParameter("confirmedNewPassword");
        req.setAttribute("confirmedNewPassword", confirmedNewPassword);
        if (confirmedNewPassword == null) {
            return false;
        } else {
            if (confirmedNewPassword.trim()
                    .equals("")) {
                req.setAttribute("confirmedNewPasswordError", "changePassword.errorConfirmedPasswordDifferent");
                return false;
            }
            if (!confirmedNewPassword.matches(PASSWORD_REGEX)) {
                req.setAttribute("confirmedNewPasswordError", "changePassword.errorPassword");
                return false;
            }
            if (!confirmedNewPassword.equals(newPassword)) {
                req.setAttribute("confirmedNewPasswordError", "changePassword.errorConfirmedPasswordDifferent");
                return false;
            }
        }
        req.setAttribute("confirmedNewPasswordIsValid", true);
        return true;
    }
}
