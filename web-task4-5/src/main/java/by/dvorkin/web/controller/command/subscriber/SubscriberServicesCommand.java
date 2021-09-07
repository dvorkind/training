package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
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

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SubscriberServicesCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            ServiceService serviceService = serviceFactory.getServiceService();
            Account account = (Account) session.getAttribute("sessionAccount");
            Subscriber subscriber = subscriberService.findByAccountId(account.getId());
            if (req.getParameter("id") !=  null) {
                long serviceId = Long.parseLong(req.getParameter("id"));
                SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
                Logger logger = LogManager.getLogger("User");
                if (req.getParameter("on") != null) {
                    Service service = serviceService.readById(serviceId);
                    int newSubscriberBalance = subscriber.getBalance() - service.getPrice();
                    if (newSubscriberBalance >= 0 ) {
                        serviceService.switchOn(subscriber.getId(), serviceId);
                        subscriber.setBalance(newSubscriberBalance);
                        subscriberService.update(subscriber);
                        SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), Action.ADD_SERVICE, service.getPrice());
                        subscriberActionService.create(subscriberAction);
                        logger.info("User #" + subscriber.getId() + " added service # " + serviceId);
                    } else {
                        req.setAttribute("serviceError", "subscriber.serviceError");
                    }
                }
                if (req.getParameter("off") != null) {
                    serviceService.switchOff(subscriber.getId(), serviceId);
                    SubscriberAction subscriberAction = createSubscriberAction(subscriber.getId(), Action.DELETE_SERVICE, 0);
                    subscriberActionService.create(subscriberAction);
                    logger.info("User #" + subscriber.getId() + " deleted service # " + serviceId);
                }
            }
            List<Service> allServices = serviceService.getAll();
            req.setAttribute("allServices", allServices);
            List<Long> subscribersServices = serviceService.getSubscribersService(subscriber.getId());
            req.setAttribute("subscribersServices", subscribersServices);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                sortTable(sortBy, allServices);
                req.setAttribute("sort", sortBy);
            } else {
                allServices.sort(Comparator.comparing(Service::getName));
            }
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private void sortTable(String sortBy, List<Service> services) {
        switch (sortBy) {
            case "nameUp":
                services.sort(Comparator.comparing(Service::getName));
                break;
            case "nameDown":
                services.sort(Comparator.comparing(Service::getName).reversed());
                break;
            case "priceUp":
                services.sort(Comparator.comparing(Service::getPrice));
                break;
            case "priceDown":
                services.sort(Comparator.comparing(Service::getPrice).reversed());
                break;
        }
    }

    private SubscriberAction createSubscriberAction(Long subscriberId, Action action, int sum) {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(action);
        subscriberAction.setSubscriberId(subscriberId);
        subscriberAction.setDate(new Date());
        subscriberAction.setSum(0);
        return subscriberAction;
    }
}
