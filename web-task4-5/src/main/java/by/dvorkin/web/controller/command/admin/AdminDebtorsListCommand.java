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
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AdminDebtorsListCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            if (req.getParameter("id") != null) {
                HttpSession session = req.getSession();
                subscriberService.setBlock(Long.parseLong(req.getParameter("id")));
                Helper.log("UserID #" + req.getParameter("id") + " was blocked by Administrator");
                session.setAttribute("success", "admin.debtorsBlockSuccess");
                return new Forward("/success.html");
            }
            List<Subscriber> subscribers = subscriberService.getDebtors();
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
