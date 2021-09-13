package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.BillService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.exceptions.SubscriberNotEnoughMoneyException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SubscriberBillsCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            BillService billService = serviceFactory.getBillService();
            Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
            if (req.getParameter("id") != null) {
                long billId = Long.parseLong(req.getParameter("id"));
                billService.payBill(billId, subscriber);
                Helper.log("User #" + subscriber.getId() + " paid the bill #" + billId);
                session.setAttribute("success", "subscriber.billsPaySuccess");
                return new Forward("/success.html");
            }
            List<Bill> allBills = billService.getAllSubscribersBill(subscriber.getId());
            req.setAttribute("allBills", allBills);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortBills(sortBy, allBills);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortBills("statusUp", allBills);
                req.setAttribute("sort", "statusUp");
            }
        } catch (SubscriberNotEnoughMoneyException e) {
            session.setAttribute("fail", "subscriber.billsError");
            return new Forward("/fail.html");
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
