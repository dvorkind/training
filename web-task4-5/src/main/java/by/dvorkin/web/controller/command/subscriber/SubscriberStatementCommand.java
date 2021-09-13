package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.exceptions.SubscriberActionNotFoundException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SubscriberStatementCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
            String beforeDate = req.getParameter("before");
            String afterDate = req.getParameter("after");
            if (req.getParameter("show") != null && !beforeDate.equals("") && !afterDate.equals("")) {
                LocalDateTime dateBefore = LocalDate.parse(beforeDate).atStartOfDay();
                LocalDateTime dateAfter = LocalDate.parse(afterDate).atStartOfDay();
                req.setAttribute("before", beforeDate);
                req.setAttribute("after", afterDate);
                Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
                List<SubscriberAction> actionList = subscriberActionService.getActionsBetweenDates(subscriber.getId()
                        , dateBefore, dateAfter);
                String sortBy = req.getParameter("sort");
                if (sortBy != null) {
                    Helper.sortActions(sortBy, actionList);
                    req.setAttribute("sort", sortBy);
                } else {
                    Helper.sortActions("dateUp", actionList);
                    req.setAttribute("sort", "dateUp");
                }
                int total = actionList.stream().mapToInt(SubscriberAction::getSum).sum();
                req.setAttribute("total", total);
                req.setAttribute("actions", actionList);
                return null;
            }
            LocalDateTime nowDate = LocalDateTime.now();
            req.setAttribute("before", nowDate.withDayOfMonth(1).toLocalDate());
            req.setAttribute("after", nowDate.toLocalDate());
        } catch (SubscriberActionNotFoundException e) {
            req.setAttribute("error", "subscriber.statementError");
            return null;
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
