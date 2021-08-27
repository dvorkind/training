package by.dvorkin.web.controller.command.admin;

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

import java.util.Comparator;
import java.util.List;

public class AdminNewSubscribersCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            if (req.getParameter("id") != null) {
                subscriberService.activate(Integer.parseInt(req.getParameter("id")));
                Logger logger = LogManager.getLogger("User");
                logger.info("UserID #" + req.getParameter("id") + " was activated by Administrator");
            }
            List<Subscriber> subscribers = subscriberService.getAll();
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                sortTable(sortBy, subscribers);
                req.setAttribute("sort", sortBy);
            } else {
                subscribers.sort(Comparator.comparing(Subscriber::getFirstname));
            }
            req.setAttribute("subscribers", subscribers);
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private void sortTable(String sortBy, List<Subscriber> subscribers) {
        switch (sortBy) {
            case "firstNameUp":
                subscribers.sort(Comparator.comparing(Subscriber::getFirstname));
                break;
            case "firstNameDown":
                subscribers.sort(Comparator.comparing(Subscriber::getFirstname).reversed());
                break;
            case "lastNameUp":
                subscribers.sort(Comparator.comparing(Subscriber::getLastname));
                break;
            case "lastNameDown":
                subscribers.sort(Comparator.comparing(Subscriber::getLastname).reversed());
                break;
        }
    }
}
