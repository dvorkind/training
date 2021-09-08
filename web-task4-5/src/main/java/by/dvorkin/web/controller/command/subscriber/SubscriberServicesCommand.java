package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
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

import java.util.List;

public class SubscriberServicesCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession();
            ServiceService serviceService = serviceFactory.getServiceService();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            subscriber = subscriberService.readById(subscriber.getId());
            if (req.getParameter("id") != null) {
                long serviceId = Long.parseLong(req.getParameter("id"));
                SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
                Logger logger = LogManager.getLogger("User");
                if (req.getParameter("on") != null) {
                    Service service = serviceService.readById(serviceId);
                    int newSubscriberBalance = subscriber.getBalance() - service.getPrice();
                    if (newSubscriberBalance >= 0) {
                        serviceService.switchOn(subscriber.getId(), serviceId);
                        subscriber.setBalance(newSubscriberBalance);
                        subscriberService.update(subscriber);
                        session.setAttribute("sessionSubscriber", subscriber);
                        SubscriberAction subscriberAction = Helper.createSubscriberAction(subscriber.getId(),
                                Action.ADD_SERVICE, service.getPrice());
                        subscriberActionService.create(subscriberAction);
                        logger.info("User #" + subscriber.getId() + " added service # " + serviceId);
                        session.setAttribute("success", "subscriber.serviceOnSuccess");
                        return new Forward("/subscriber/success.html");
                    } else {
                        req.setAttribute("serviceError", "subscriber.serviceError");
                    }
                }
                if (req.getParameter("off") != null) {
                    serviceService.switchOff(subscriber.getId(), serviceId);
                    SubscriberAction subscriberAction = Helper.createSubscriberAction(subscriber.getId(),
                            Action.DELETE_SERVICE, 0);
                    subscriberActionService.create(subscriberAction);
                    logger.info("User #" + subscriber.getId() + " deleted service # " + serviceId);
                    session.setAttribute("success", "subscriber.serviceOffSuccess");
                    return new Forward("/subscriber/success.html");
                }
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
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
