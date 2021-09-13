package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SubscriberSmsCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            TariffService tariffService = serviceFactory.getTariffService();
            Tariff tariff = tariffService.getById(subscriber.getTariff());
            req.setAttribute("tariff", tariff);
            if (req.getParameter("confirmation") != null) {
                int smsCost = tariff.getSmsCost();
                subscriberService.subtractBalance(subscriber, smsCost, Action.SEND_SMS);
                Helper.log("User #" + subscriber.getId() + " made SMS for the amount: " + smsCost);
                session.setAttribute("success", "subscriber.smsSuccess");
                return new Forward("/success.html");
            }
        } catch (SubscriberNotEnoughMoneyException e) {
            session.setAttribute("fail", "subscriber.smsError");
            return new Forward("/fail.html");
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
