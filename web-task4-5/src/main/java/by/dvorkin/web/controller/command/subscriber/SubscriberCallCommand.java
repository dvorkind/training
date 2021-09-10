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
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SubscriberCallCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            TariffService tariffService = serviceFactory.getTariffService();
            Tariff tariff = tariffService.getById(subscriber.getTariff());
            req.setAttribute("tariff", tariff);
            if (req.getParameter("confirmation") != null) {
                int callCost = Integer.parseInt(req.getParameter("callLength")) * tariff.getCallCost();
                subscriberService.subtractBalance(subscriber, callCost);
                SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
                SubscriberAction subscriberAction = Helper.createSubscriberAction(subscriber.getId(),
                        Action.MAKE_CALL, callCost);
                subscriberActionService.create(subscriberAction);
                Helper.log("User #" + subscriber.getId() + " made a call for the amount: " + callCost);
                session.setAttribute("success", "subscriber.callSuccess");
                return new Forward("/success.html");
            }
            return null;
        } catch (SubscriberNotEnoughMoneyException e) {
            req.setAttribute("callError", "subscriber.callError");
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
