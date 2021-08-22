package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import by.dvorkin.web.model.entity.User;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.exceptions.AccountLoginNotUniqueException;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.UserPhoneNotUniqueException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁё\\s'-]{1,20}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+[0-9]{12})";
    private static final String LOGIN_REGEX = "^[A-Za-z0-9._-]{5,20}$";
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9~!@#$%^&*()-_=+'/|.]{5,20}$";

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        if (isInputValid(req)) {
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                AccountService accountService = serviceFactory.getAccountService();
                Account account = createAccount(req);
                User user = createUser(req);
                accountService.create(account, user);
                req.getSession(false).setAttribute("sessionAccount", account);
                return new Forward("/login.html");
            } catch (FactoryException e) {
                e.printStackTrace();
                throw new ServletException(e);
            } catch (AccountLoginNotUniqueException e) {
                req.removeAttribute("loginIsValid");
                req.setAttribute("loginError", "registration.error.existAccountError");
                return null;
            } catch (UserPhoneNotUniqueException e) {
                req.removeAttribute("phoneNumberIsValid");
                req.setAttribute("phoneNumberError", "registration.error.existUserError");
                return null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private Account createAccount(HttpServletRequest req) {
        Account account = new Account();
        account.setLogin(req.getParameter("login"));
        account.setPassword(req.getParameter("password"));
        account.setRole(Role.SUBSCRIBER);
        return account;
    }

    private User createUser(HttpServletRequest req) {
        User user = new User();
        user.setBalance(0);
        user.setBlocked(true);
        user.setRegistered(false);
        user.setFirstname(req.getParameter("firstname"));
        user.setLastname(req.getParameter("lastname"));
        user.setPhoneNumber(req.getParameter("phoneNumber"));
        return user;
    }

    private boolean isInputValid(HttpServletRequest req) {
        return isLoginValid(req) & isPasswordValid(req) & isConfirmedPassword(req) & isFirstnameValid(req) & isLastnameValid(req) & isPhoneValid(req);
    }

    private boolean isFirstnameValid(HttpServletRequest req) {
        String firstname = req.getParameter("firstname");
        req.setAttribute("firstname", firstname);
        if (firstname == null) {
            return false;
        } else {
            if (firstname.trim()
                    .equals("")) {
                req.setAttribute("firstnameError", "registration.error.empty");
                return false;
            }
            if (!firstname.matches(NAME_REGEX)) {
                req.setAttribute("firstnameError", "registration.error.firstname");
                return false;
            }
        }
        req.setAttribute("firstnameIsValid", true);
        return true;
    }

    private boolean isLastnameValid(HttpServletRequest req) {
        String lastname = req.getParameter("lastname");
        req.setAttribute("lastname", lastname);
        if (lastname == null) {
            return false;
        } else {
            if (lastname.trim()
                    .equals("")) {
                req.setAttribute("lastnameError", "registration.error.empty");
                return false;
            }
            if (!lastname.matches(NAME_REGEX)) {
                req.setAttribute("lastnameError", "registration.error.lastname");
                return false;
            }
        }
        req.setAttribute("lastnameIsValid", true);
        return true;
    }

    private boolean isPhoneValid(HttpServletRequest req) {
        String phoneNumber = req.getParameter("phoneNumber");
        if (phoneNumber == null) {
            return false;
        } else {
            phoneNumber = phoneNumber.replaceAll("\\s", "")
                    .replaceAll("-", "")
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "");
            req.setAttribute("phoneNumber", phoneNumber);
            if (phoneNumber.trim()
                    .equals("")) {
                req.setAttribute("phoneNumberError", "registration.error.empty");
                return false;
            }
            if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
                req.setAttribute("phoneNumberError", "registration.error.phoneNumber");
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
            if (login.trim()
                    .equals("")) {
                req.setAttribute("loginError", "registration.error.empty");
                return false;
            }
            if (!login.matches(LOGIN_REGEX)) {
                req.setAttribute("loginError", "registration.error.login");
                return false;
            }
        }
        req.setAttribute("loginIsValid", true);
        return true;
    }

    private boolean isPasswordValid(HttpServletRequest req) {
        String password = req.getParameter("password");
        req.setAttribute("password", password);
        if (password == null) {
            return false;
        } else {
            if (password.trim()
                    .equals("")) {
                req.setAttribute("passwordError", "registration.error.empty");
                return false;
            }
            if (!password.matches(PASSWORD_REGEX)) {
                req.setAttribute("passwordError", "registration.error.password");
                return false;
            }
        }
        req.setAttribute("passwordIsValid", true);
        return true;
    }

    private boolean isConfirmedPassword(HttpServletRequest req) {
        String password = req.getParameter("password");
        String confirmedPassword = req.getParameter("confirmedPassword");
        req.setAttribute("confirmedPassword", confirmedPassword);
        if (confirmedPassword == null) {
            return false;
        } else {
            if (confirmedPassword.trim()
                    .equals("")) {
                req.setAttribute("confirmedPasswordError", "registration.error.confirmedPasswordDifferent");
                return false;
            }
            if (!confirmedPassword.matches(PASSWORD_REGEX)) {
                req.setAttribute("confirmedPasswordError", "registration.error.password");
                return false;
            }
            if (!confirmedPassword.equals(password)) {
                req.setAttribute("confirmedPasswordError", "registration.error.confirmedPasswordDifferent");
                return false;
            }
        }
        req.setAttribute("confirmedPasswordIsValid", true);
        return true;
    }
}