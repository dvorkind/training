package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SubscriberSummaryCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            ServiceService serviceService = serviceFactory.getServiceService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            req.setAttribute("tariff", tariffService.getById(subscriber.getTariff()));
            List<Service> subscribersServices = serviceService.getSubscribersService(subscriber.getId());
            req.setAttribute("subscribersServices", subscribersServices);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
