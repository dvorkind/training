package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.BillService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.exceptions.BillTooEarlyException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AdminSubscriberBillsCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        if (req.getParameter("subscriberId") == null) {
            return new Forward("/admin/subscribers_all.html");
        }
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            BillService billService = serviceFactory.getBillService();
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            Subscriber subscriber = subscriberService.getById(Long.parseLong(req.getParameter("subscriberId")));
            req.setAttribute("subscriberId", subscriber.getId());
            req.setAttribute("subscriberFirstName", subscriber.getFirstname());
            req.setAttribute("subscriberLastName", subscriber.getLastname());
            if (req.getParameter("billId") != null) {
                billService.delete(Long.parseLong(req.getParameter("billId")));
            }
            List<Bill> allBills = billService.getAllSubscribersBill(subscriber.getId());
            req.setAttribute("allBills", allBills);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortBills(sortBy, allBills);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortBills("dateUp", allBills);
                req.setAttribute("sort", "dateUp");
            }
            if (req.getParameter("new") != null) {
                subscriberService.issueBill(subscriber.getId());
                Helper.log("User # " + subscriber.getId() + " was billed by Administrator");
                HttpSession session = req.getSession();
                session.setAttribute("success", "admin.billsSuccess");
                return new Forward("/success.html");
            }
        } catch (BillTooEarlyException e) {
            req.setAttribute("error", "admin.billsError");
            return null;
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
