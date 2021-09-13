package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.BillService;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AdminSummaryCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            SubscriberService subscriberService = serviceFactory.getSubscriberService();
            if (req.getParameter("new") != null) {
                int issuedBills = subscriberService.issueBillToAll();
                HttpSession session = req.getSession();
                if (issuedBills > 0) {
                    Helper.log("New bills were issued by the Administrator: " + issuedBills);
                    session.setAttribute("success", "admin.mainSuccess");
                    session.setAttribute("successTwo", issuedBills);
                    return new Forward("/success.html");
                } else {
                    session.setAttribute("fail", "admin.mainError");
                    return new Forward("/fail.html");
                }
            }
            ServiceService serviceService = serviceFactory.getServiceService();
            TariffService tariffService = serviceFactory.getTariffService();
            BillService billService = serviceFactory.getBillService();
            int countSubscribers = subscriberService.getAll().size();
            int countNewSubscribers = subscriberService.getNewSubscribers().size();
            List<Subscriber> debtors = subscriberService.getDebtors();
            int countDebtors = debtors.size();
            int debtorsSum = debtors.stream().mapToInt(i -> Math.abs(i.getBalance())).sum();
            int countServices = serviceService.getAll().size();
            int countTariffs = tariffService.getAll().size();
            List<Bill> unpaidBills = billService.getAllUnpaid();
            int countUnpaidBills = unpaidBills.size();
            int unpaidBillsSum = unpaidBills.stream().mapToInt(Bill::getSum).sum();
            req.setAttribute("countSubscribers", countSubscribers);
            req.setAttribute("countNewSubscribers", countNewSubscribers);
            req.setAttribute("countDebtors", countDebtors);
            req.setAttribute("debtorsSum", debtorsSum);
            req.setAttribute("countServices", countServices);
            req.setAttribute("countTariffs", countTariffs);
            req.setAttribute("countUnpaidBills", countUnpaidBills);
            req.setAttribute("unpaidBillsSum", unpaidBillsSum);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
