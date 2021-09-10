package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberActionService;
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

public class SubscriberRefillBalanceCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        if (req.getParameter("confirmation") != null) {
            try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                HttpSession session = req.getSession();
                SubscriberService subscriberService = serviceFactory.getSubscriberService();
                Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
                SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
                int balanceRefillSum = Integer.parseInt(req.getParameter("balanceRefillRoubles")) * 100;
                balanceRefillSum += Integer.parseInt(req.getParameter("balanceRefillKopecks"));
                subscriber.setBalance(subscriber.getBalance() + balanceRefillSum);
                subscriberService.update(subscriber);
                SubscriberAction subscriberAction = Helper.createSubscriberAction(subscriber.getId(),
                        Action.REFILL_BALANCE, balanceRefillSum);
                subscriberActionService.create(subscriberAction);
                Helper.log("User #" + subscriber.getId() + " refilled the balance to sum: " + balanceRefillSum);
                session.setAttribute("success", "subscriber.refillBalanceSuccess");
                return new Forward("/success.html");
            } catch (ServiceException | FactoryException e) {
                throw new ServletException(e);
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
