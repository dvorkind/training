package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AdminSubscriberEditCommand implements Command {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁё\\s'-]{1,20}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+[0-9]{12})";
    private static final String LOGIN_REGEX = "^[A-Za-z0-9._-]{5,20}$";

    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        if (req.getParameter("id") == null) {
            return new Forward("/admin/admin.html");
        }
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = subscriberService.getById(Long.parseLong(req.getParameter("id")));
            if (req.getParameter("block") != null) {
                subscriber.setBlocked(!subscriber.isBlocked());
                subscriberService.update(subscriber);
                Helper.log("UserID #" + subscriber.getId() + " was blocked by Administrator");
                return new Forward("/admin/subscribers_all.html");
            }
            AccountService accountService = serviceFactory.getAccountService();
            Account account = accountService.getById(subscriber.getAccountId());
            if (req.getParameter("resetPassword") != null) {
                accountService.resetPassword(account.getLogin(), subscriber.getPhoneNumber());
                Helper.log("User " + account.getLogin() + " password reset by Administrator");
                return new Forward("/admin/subscribers_all.html");
            }
            TariffService tariffService = serviceFactory.getTariffService();
            List<Tariff> tariffs = tariffService.getAll();
            req.setAttribute("subscriber", subscriber);
            req.setAttribute("account", account);
            req.setAttribute("tariffs", tariffs);
            if (req.getParameter("confirmation") != null) {
                if (isLoginValid(req, account) && !account.getLogin().equals(req.getParameter("login"))) {
                    if (accountService.getByLogin(req.getParameter("login")) != null) {
                        req.removeAttribute("loginIsValid");
                        req.setAttribute("loginError", "admin.subscriberEditErrorExistAccountError");
                        return null;
                    }
                }
                if (isPersonalInfoValid(req, subscriber) && !subscriber.getPhoneNumber()
                        .equals(req.getParameter("phoneNumber"))) {
                    if (subscriberService.getByPhoneNumber((String) req.getAttribute("phoneNumber")) != null) {
                        req.removeAttribute("phoneNumberIsValid");
                        req.setAttribute("phoneNumberError", "admin.subscriberEditErrorExistSubscriberError");
                        return null;
                    }
                }
                account.setLogin(req.getParameter("login"));
                accountService.update(account);
                subscriber.setFirstname(req.getParameter("firstname"));
                subscriber.setLastname(req.getParameter("lastname"));
                subscriber.setPhoneNumber(req.getParameter("phoneNumber"));
                subscriber.setTariff(Long.parseLong(req.getParameter("newTariff")));
                subscriberService.update(subscriber);
                Helper.log("UserID #" + subscriber.getId() + " was updated by Administrator");
                return new Forward("/admin/subscribers_all.html");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }

    private boolean isPersonalInfoValid(HttpServletRequest req, Subscriber subscriber) {
        return isFirstnameValid(req, subscriber) & isLastnameValid(req, subscriber) & isPhoneValid(req, subscriber);
    }

    private boolean isLoginValid(HttpServletRequest req, Account account) {
        String login = req.getParameter("login");
        req.setAttribute("login", login);
        if (account.getLogin().equals(login)) {
            req.setAttribute("loginIsValid", true);
            return true;
        }
        if (login == null) {
            return false;
        } else {
            if (login.trim().equals("")) {
                req.setAttribute("loginError", "admin.subscriberEditErrorEmpty");
                return false;
            }
            if (!login.matches(LOGIN_REGEX)) {
                req.setAttribute("loginError", "admin.subscriberEditErrorLogin");
                return false;
            }
        }
        req.setAttribute("loginIsValid", true);
        return true;
    }

    private boolean isFirstnameValid(HttpServletRequest req, Subscriber subscriber) {
        String firstname = req.getParameter("firstname");
        req.setAttribute("firstname", firstname);
        if (subscriber.getFirstname().equals(firstname)) {
            req.setAttribute("firstnameIsValid", true);
            return true;
        }
        if (firstname == null) {
            return false;
        } else {
            if (firstname.trim().equals("")) {
                req.setAttribute("firstnameError", "admin.subscriberEditErrorEmpty");
                return false;
            }
            if (!firstname.matches(NAME_REGEX)) {
                req.setAttribute("firstnameError", "admin.subscriberEditErrorFirstname");
                return false;
            }
        }
        req.setAttribute("firstnameIsValid", true);
        return true;
    }

    private boolean isLastnameValid(HttpServletRequest req, Subscriber subscriber) {
        String lastname = req.getParameter("lastname");
        req.setAttribute("lastname", lastname);
        if (subscriber.getLastname().equals(lastname)) {
            req.setAttribute("lastnameIsValid", true);
            return true;
        }
        if (lastname == null) {
            return false;
        } else {
            if (lastname.trim().equals("")) {
                req.setAttribute("lastnameError", "admin.subscriberEditErrorEmpty");
                return false;
            }
            if (!lastname.matches(NAME_REGEX)) {
                req.setAttribute("lastnameError", "admin.subscriberEditErrorLastname");
                return false;
            }
        }
        req.setAttribute("lastnameIsValid", true);
        return true;
    }

    private boolean isPhoneValid(HttpServletRequest req, Subscriber subscriber) {
        String phoneNumber = req.getParameter("phoneNumber");
        if (phoneNumber == null) {
            return false;
        } else {
            phoneNumber = phoneNumber.replaceAll("\\s", "").replaceAll("-", "").replaceAll("\\(", "")
                    .replaceAll("\\)", "");
            req.setAttribute("phoneNumber", phoneNumber);
            if (subscriber.getPhoneNumber().equals(phoneNumber)) {
                req.setAttribute("phoneNumberIsValid", true);
                return true;
            }
            if (phoneNumber.trim().equals("")) {
                req.setAttribute("phoneNumberError", "admin.subscriberEditErrorEmpty");
                return false;
            }
            if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
                req.setAttribute("phoneNumberError", "admin.subscriberEditErrorPhoneNumber");
                return false;
            }
        }
        req.setAttribute("phoneNumberIsValid", true);
        return true;
    }
}
