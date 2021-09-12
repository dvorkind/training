package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.service.BillService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class AdminSubscriberBillsCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        if (req.getParameter("subscriberId") == null) {
            return new Forward("/admin/subscribers_all.html");
        }
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            Long subscriberId = Long.parseLong(req.getParameter("subscriberId"));
            req.setAttribute("subscriberId", subscriberId);
            BillService billService = serviceFactory.getBillService();
            if (req.getParameter("billId") != null) {
                billService.delete(Long.parseLong(req.getParameter("billId")));
            }
            if (req.getParameter("new") != null) {
                SubscriberService subscriberService = serviceFactory.getSubscriberService();
                if (subscriberService.invoiceBill(subscriberId)) {
                    Helper.log("User # " + subscriberId + " was billed by Administrator");
                } else {
                    req.setAttribute("error", "admin.billsError");
                }
            }
            List<Bill> allBills = billService.getAll(subscriberId);
            req.setAttribute("allBills", allBills);
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortBills(sortBy, allBills);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortBills("dateUp", allBills);
                req.setAttribute("sort", "dateUp");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
