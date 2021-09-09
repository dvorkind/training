package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.entity.Tariff;
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
import java.util.List;

public class SubscriberTariffChangeCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        long DAY_IN_MILLISECONDS = 86400000L;
        int TARIFF_CHANGE_COST = 100;
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession();
            TariffService tariffService = serviceFactory.getTariffService();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            List<Tariff> tariffs = tariffService.getAll();
            req.setAttribute("id", subscriber.getTariff());
            req.setAttribute("tariffs", tariffs);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortTariffs(sortBy, tariffs);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortTariffs("nameUp", tariffs);
            }
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
                        SubscriberAction subscriberAction = Helper.createSubscriberAction(subscriber.getId(),
                                Action.CHANGE_TARIFF, TARIFF_CHANGE_COST);
                        subscriberActionService.create(subscriberAction);
                        Logger logger = LogManager.getLogger("User");
                        logger.info("User #" + subscriber.getId() + " changed his tariff to #" + newTariff);
                        session.setAttribute("success", "subscriber.changeTariffSuccess");
                        return new Forward("/subscriber/success.html");
                    } else {
                        req.removeAttribute("confirmation");
                        req.setAttribute("tariffChangeError", "subscriber.changeTariffError");
                    }
                    return null;
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
}
