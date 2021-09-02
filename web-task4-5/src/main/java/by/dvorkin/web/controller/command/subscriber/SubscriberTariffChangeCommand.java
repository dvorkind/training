package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class SubscriberTariffChangeCommand implements Command {
    private final int TARIFF_CHANGE_COST = 100;

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        long DAY_IN_MILLISECONDS = 86400000L;
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession(false);
            TariffService tariffService = serviceFactory.getTariffService();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Account account = (Account) session.getAttribute("sessionAccount");
            Subscriber subscriber = subscriberService.findByAccountId(account.getId());
            req.setAttribute("id", subscriber.getTariff());
            req.setAttribute("tariffs", tariffService.getAll());
            if (req.getParameter("confirmation") != null) {
                if (req.getParameter("newTariff") != null) {
                    SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
                    Date dateNow = new Date();
                    Date dateLast = subscriberActionService.getLastChangeTariff(subscriber.getId());
                    if (dateLast.getTime() < dateNow.getTime() && (dateNow.getTime() - dateLast.getTime()) < DAY_IN_MILLISECONDS) {
                        req.removeAttribute("confirmation");
                        req.setAttribute("tariffChangeError", "subscriber.changeTariffDateError");
                        req.setAttribute("tariffChangeDate", String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",
                                new Date(dateLast.getTime() + DAY_IN_MILLISECONDS)));
                        return null;
                    }
                    Long newTariff = Long.parseLong(req.getParameter("newTariff"));
                    subscriber.setTariff(newTariff);
                    if (subscriber.getBalance() - TARIFF_CHANGE_COST >= 0) {
                        subscriber.setBalance(subscriber.getBalance() - TARIFF_CHANGE_COST);
                        subscriberService.update(subscriber);
                        SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId());
                        subscriberActionService.create(subscriberAction);
                        req.setAttribute("success", "subscriber.changeTariffSuccess");
                        req.removeAttribute("confirmation");
                        Logger logger = LogManager.getLogger("User");
                        logger.info("User #" + subscriber.getId() + " changed his tariff to #" + newTariff);
                        return null;
                    } else {
                        req.removeAttribute("confirmation");
                        req.setAttribute("tariffChangeError", "subscriber.changeTariffError");
                        return null;
                    }
                }
            } else {
                return null;
            }
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private SubscriberAction createSubscriberAction(Long subscriberId) {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(Action.CHANGE_TARIFF);
        subscriberAction.setSubscriberId(subscriberId);
        subscriberAction.setDate(new Date());
        subscriberAction.setSum(TARIFF_CHANGE_COST);
        return subscriberAction;
    }
}
