package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SubscriberServicesCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            ServiceService serviceService = serviceFactory.getServiceService();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            if (req.getParameter("id") != null) {
                long serviceId = Long.parseLong(req.getParameter("id"));
                if (req.getParameter("on") != null) {
                    subscriberService.switchOnService(subscriber.getId(), serviceId);
                    Helper.log("User #" + subscriber.getId() + " added service #" + serviceId);
                    session.setAttribute("success", "subscriber.servicesOnSuccess");
                } else {
                    subscriberService.switchOffService(subscriber.getId(), serviceId);
                    Helper.log("User #" + subscriber.getId() + " deleted service #" + serviceId);
                    session.setAttribute("success", "subscriber.servicesOffSuccess");
                }
                return new Forward("/success.html");
            }
            List<Service> allServices = serviceService.getAll();
            req.setAttribute("allServices", allServices);
            List<Service> subscribersServices = serviceService.getSubscribersService(subscriber.getId());
            req.setAttribute("subscribersServices", subscribersServices);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortServices(sortBy, allServices);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortServices("nameUp", allServices);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
