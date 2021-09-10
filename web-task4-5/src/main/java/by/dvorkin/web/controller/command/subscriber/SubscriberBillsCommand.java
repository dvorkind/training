package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.service.BillService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberActionService;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SubscriberBillsCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            HttpSession session = req.getSession();
            BillService billService = serviceFactory.getBillService();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            List<Bill> allBills = billService.getAll(subscriber.getId());
            req.setAttribute("allBills", allBills);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortBills(sortBy, allBills);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortBills("statusUp", allBills);
                req.setAttribute("sort", "statusUp");
            }
            if (req.getParameter("id") != null) {
                long billId = Long.parseLong(req.getParameter("id"));
                int billSum = billService.payBill(billId, subscriber);
                SubscriberActionService subscriberActionService = serviceFactory.getSubscriberActionService();
                subscriberService.update(subscriber);
                SubscriberAction subscriberAction = Helper.createSubscriberAction(subscriber.getId(), Action.PAY_BILL
                        , billSum);
                subscriberActionService.create(subscriberAction);
                Helper.log("User #" + subscriber.getId() + " paid the bill #" + billId);
                session.setAttribute("success", "subscriber.billsPaySuccess");
                return new Forward("/success.html");
            }
            return null;
        } catch (SubscriberNotEnoughMoneyException e) {
            req.setAttribute("billError", "subscriber.billsError");
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
