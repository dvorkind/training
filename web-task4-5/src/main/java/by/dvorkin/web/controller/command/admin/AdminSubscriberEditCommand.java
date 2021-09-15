package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.AccountService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.AccountLoginNotUniqueException;
import by.dvorkin.web.model.service.exceptions.SubscriberPhoneNotUniqueException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AdminSubscriberEditCommand implements Command {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁё\\s'-]{1,20}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+[0-9]{12})";
    private static final String LOGIN_REGEX = "^[A-Za-z0-9._-]{5,20}$";

    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        if (req.getParameter("subscriberId") == null) {
            return new Forward("/admin/admin.html");
        }
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = subscriberService.getById(Long.parseLong(req.getParameter("subscriberId")));

            //Blocking subscriber
            if (req.getParameter("block") != null) {
                boolean blocked = subscriber.isBlocked();
                subscriber.setBlocked(!subscriber.isBlocked());
                subscriberService.update(subscriber);
                if (blocked) {
                    Helper.log("UserID #" + subscriber.getId() + " was unblocked by Administrator");
                    session.setAttribute("success", "admin.subscriberEditUnblockSuccess");
                } else {
                    Helper.log("UserID #" + subscriber.getId() + " was blocked by Administrator");
                    session.setAttribute("success", "admin.subscriberEditBlockSuccess");
                }
                return new Forward("/success.html");
            }

            AccountService accountService = serviceFactory.getAccountService();
            Account account = accountService.getById(subscriber.getAccountId());

            //Subscriber's password reset
            if (req.getParameter("resetPassword") != null) {
                accountService.resetPassword(account.getLogin(), subscriber.getPhoneNumber());
                Helper.log("User " + account.getLogin() + " password reset by Administrator");
                session.setAttribute("success", "admin.subscriberEditResetPasswordSuccess");
                return new Forward("/success.html");
            }

            TariffService tariffService = serviceFactory.getTariffService();
            ServiceService serviceService = serviceFactory.getServiceService();
            List<Tariff> tariffs = tariffService.getAll();
            List<Service> allServices = serviceService.getAll();
            List<Service> subscribersServices = serviceService.getSubscribersService(subscriber.getId());
            req.setAttribute("allServices", allServices);
            req.setAttribute("subscribersServices", subscribersServices);
            req.setAttribute("subscriber", subscriber);
            req.setAttribute("account", account);
            req.setAttribute("tariffs", tariffs);

            //Subscriber's personal data edit
            if (req.getParameter("confirmation") != null) {
                if (isLoginValid(req, account) && isPersonalInfoValid(req, subscriber)) {
                    String login = req.getParameter("login");
                    String phoneNumber = req.getParameter("phoneNumber");
                    subscriber.setFirstname(req.getParameter("firstname"));
                    subscriber.setLastname(req.getParameter("lastname"));
                    accountService.changeSubscribersPersonalData(account, subscriber, login, phoneNumber);
                    Helper.log("UserID #" + subscriber.getId() + " was updated by Administrator");
                    session.setAttribute("success", "admin.subscriberEditSuccess");
                    return new Forward("/success.html");
                }
            }

            //Edit subscriber's services
            if (req.getParameter("serviceId") != null) {
                long serviceId = Long.parseLong(req.getParameter("serviceId"));
                if (req.getParameter("on") != null) {
                    subscriberService.switchOnService(subscriber, serviceId);
                    Helper.log("User #" + subscriber.getId() + " added service #" + serviceId + " by Administrator");
                    session.setAttribute("success", "admin.subscriberEditServiceOnSuccess");
                } else {
                    subscriberService.switchOffService(subscriber.getId(), serviceId);
                    Helper.log("User #" + subscriber.getId() + " deleted service #" + serviceId + " by Administrator");
                    session.setAttribute("success", "admin.subscriberEditServiceOffSuccess");
                }
                return new Forward("/success.html");
            }

            //Change subscriber's tariff
            if (req.getParameter("newTariff") != null) {
                Long newTariff = Long.parseLong(req.getParameter("newTariff"));
                subscriberService.changeTariffByAdmin(subscriber, newTariff);
                Helper.log("User #" + subscriber.getId() + " changed tariff to #" + newTariff + " by Administrator");
                session.setAttribute("success", "admin.subscriberEditTariffChangeSuccess");
                return new Forward("/success.html");
            }
        } catch (AccountLoginNotUniqueException e) {
            req.removeAttribute("loginIsValid");
            req.setAttribute("loginError", "admin.subscriberEditErrorExistAccountError");
            return null;
        } catch (SubscriberPhoneNotUniqueException e) {
            req.removeAttribute("phoneNumberIsValid");
            req.setAttribute("phoneNumberError", "admin.subscriberEditErrorExistSubscriberError");
            return null;
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
