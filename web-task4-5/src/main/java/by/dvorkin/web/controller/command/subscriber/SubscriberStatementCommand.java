package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberActionNotFoundException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SubscriberStatementCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
            String beforeDate = req.getParameter("before");
            String afterDate = req.getParameter("after");
            if (req.getParameter("show") != null && !beforeDate.equals("") && !afterDate.equals("")) {
                Date dateBefore = new SimpleDateFormat("yyyy-MM-dd").parse(beforeDate);
                Date dateAfter = new SimpleDateFormat("yyyy-MM-dd").parse(afterDate);
                req.setAttribute("before", beforeDate);
                req.setAttribute("after", afterDate);
                Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
                List<SubscriberAction> actionList = subscriberActionService.getActionsBetweenDates(subscriber.getId()
                        , dateBefore, dateAfter);
                actionList.sort(Comparator.comparing(SubscriberAction::getDate));
                req.setAttribute("before", new SimpleDateFormat("yyyy-MM-dd").format(dateBefore));
                req.setAttribute("after", new SimpleDateFormat("yyyy-MM-dd").format(dateAfter));
                req.setAttribute("actions", actionList);
                return null;
            }
            req.setAttribute("before", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            req.setAttribute("after", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } catch (SubscriberActionNotFoundException e) {
            req.setAttribute("error", "subscriber.statementError");
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
