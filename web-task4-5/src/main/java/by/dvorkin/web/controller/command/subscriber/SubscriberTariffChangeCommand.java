package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.SubscriberCanNotChangeTariffException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SubscriberTariffChangeCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            List<Tariff> tariffs = tariffService.getAll();
            req.setAttribute("id", subscriber.getTariff());
            req.setAttribute("tariffs", tariffs);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortTariffs(sortBy, tariffs);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortTariffs("nameUp", tariffs);
            }
            if (req.getParameter("newTariff") != null) {
                Long newTariff = Long.parseLong(req.getParameter("newTariff"));
                subscriberService.changeTariff(subscriber, newTariff);
                Helper.log("User #" + subscriber.getId() + " changed his tariff to #" + newTariff);
                session.setAttribute("success", "subscriber.changeTariffSuccess");
                return new Forward("/success.html");
            }
        } catch (SubscriberCanNotChangeTariffException e) {
            session.setAttribute("fail", "subscriber.changeTariffDateError");
            session.setAttribute("failTwo", e.getMessage());
            return new Forward("/fail.html");
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
