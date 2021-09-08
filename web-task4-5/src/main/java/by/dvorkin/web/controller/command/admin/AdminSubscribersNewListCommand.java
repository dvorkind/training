package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AdminSubscribersNewListCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            if (req.getParameter("id") != null) {
                Subscriber subscriber = subscriberService.readById(Long.parseLong(req.getParameter("id")));
                subscriber.setRegistered(true);
                subscriberService.update(subscriber);
                Logger logger = LogManager.getLogger("User");
                logger.info("UserID #" + req.getParameter("id") + " was activated by Administrator");
            }
            List<Subscriber> subscribers = subscriberService.getNewSubscribers();
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortSubscribers(sortBy, subscribers);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortSubscribers("firstNameUp", subscribers);
            }
            req.setAttribute("subscribers", subscribers);
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
