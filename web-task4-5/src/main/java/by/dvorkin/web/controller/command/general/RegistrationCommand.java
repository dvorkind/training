package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.exceptions.AccountLoginNotUniqueException;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberPhoneNotUniqueException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationCommand implements Command {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁё\\s'-]{1,20}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+[0-9]{12})";
    private static final String LOGIN_REGEX = "^[A-Za-z0-9._-]{5,20}$";
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9~!@#$%^&*()-_=+'/|.]{5,20}$";

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Account sessionAccount = (Account) session.getAttribute("sessionAccount");
            if (sessionAccount != null) {
                switch (sessionAccount.getRole()) {
                    case ADMINISTRATOR:
                        return new Forward("/admin/admin.html");
                    case SUBSCRIBER:
                        return new Forward("/subscriber/subscriber.html");
                }
            }
        }

        if (isInputValid(req)) {
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                AccountService accountService = serviceFactory.getAccountService();
                Account account = createAccount(req);
                Subscriber subscriber = createSubscriber(req);
                accountService.create(account, subscriber);
                req.getSession(false).setAttribute("sessionAccount", account);
                Logger logger = LogManager.getLogger("User");
                logger.info("User " + account.getLogin() + " has registered. IP [" + req.getRemoteAddr() + "]");
                return new Forward("/login.html");
            } catch (AccountLoginNotUniqueException e) {
                req.removeAttribute("loginIsValid");
                req.setAttribute("loginError", "registration.errorExistAccountError");
                return null;
            } catch (SubscriberPhoneNotUniqueException e) {
                req.removeAttribute("phoneNumberIsValid");
                req.setAttribute("phoneNumberError", "registration.errorExistSubscriberError");
                return null;
            } catch (ServiceException |FactoryException e) {
                throw new ServletException(e);
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

    private Subscriber createSubscriber(HttpServletRequest req) {
        Subscriber subscriber = new Subscriber();
        subscriber.setBalance(0);
        subscriber.setTariff(1L);
        subscriber.setBlocked(true);
        subscriber.setRegistered(false);
        subscriber.setFirstname(req.getParameter("firstname"));
        subscriber.setLastname(req.getParameter("lastname"));
        subscriber.setPhoneNumber(req.getParameter("phoneNumber"));
        return subscriber;
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
                req.setAttribute("firstnameError", "registration.errorEmpty");
                return false;
            }
            if (!firstname.matches(NAME_REGEX)) {
                req.setAttribute("firstnameError", "registration.errorFirstname");
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
                req.setAttribute("lastnameError", "registration.errorEmpty");
                return false;
            }
            if (!lastname.matches(NAME_REGEX)) {
                req.setAttribute("lastnameError", "registration.errorLastname");
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
                req.setAttribute("phoneNumberError", "registration.errorEmpty");
                return false;
            }
            if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
                req.setAttribute("phoneNumberError", "registration.errorPhoneNumber");
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
                req.setAttribute("loginError", "registration.errorEmpty");
                return false;
            }
            if (!login.matches(LOGIN_REGEX)) {
                req.setAttribute("loginError", "registration.errorLogin");
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
                req.setAttribute("passwordError", "registration.errorEmpty");
                return false;
            }
            if (!password.matches(PASSWORD_REGEX)) {
                req.setAttribute("passwordError", "registration.errorPassword");
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
                req.setAttribute("confirmedPasswordError", "registration.errorConfirmedPasswordDifferent");
                return false;
            }
            if (!confirmedPassword.matches(PASSWORD_REGEX)) {
                req.setAttribute("confirmedPasswordError", "registration.errorPassword");
                return false;
            }
            if (!confirmedPassword.equals(password)) {
                req.setAttribute("confirmedPasswordError", "registration.errorConfirmedPasswordDifferent");
                return false;
            }
        }
        req.setAttribute("confirmedPasswordIsValid", true);
        return true;
    }
}
