package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AdminSubscribersListCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            List<Subscriber> subscribers = subscriberService.getAll();
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortSubscribers(sortBy, subscribers);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortSubscribers("firstNameUp", subscribers);
            }
            req.setAttribute("subscribers", subscribers);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
