package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Comparator;
import java.util.List;

public class AdminTariffsListCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            List<Tariff> tariffs = tariffService.getAll();
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                sortTable(sortBy, tariffs);
                req.setAttribute("sort", sortBy);
            } else {
                tariffs.sort(Comparator.comparing(Tariff::getName));
            }
            req.setAttribute("tariffs", tariffs);
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private void sortTable(String sortBy, List<Tariff> tariffs) {
        switch (sortBy) {
            case "nameUp":
                tariffs.sort(Comparator.comparing(Tariff::getName));
                break;
            case "nameDown":
                tariffs.sort(Comparator.comparing(Tariff::getName).reversed());
                break;
            case "subscriptionFeeUp":
                tariffs.sort(Comparator.comparing(Tariff::getSubscriptionFee));
                break;
            case "subscriptionFeeDown":
                tariffs.sort(Comparator.comparing(Tariff::getSubscriptionFee).reversed());
                break;
            case "callCostUp":
                tariffs.sort(Comparator.comparing(Tariff::getCallCost));
                break;
            case "callCostDown":
                tariffs.sort(Comparator.comparing(Tariff::getCallCost).reversed());
                break;
            case "smsCostUp":
                tariffs.sort(Comparator.comparing(Tariff::getSmsCost));
                break;
            case "smsCostDown":
                tariffs.sort(Comparator.comparing(Tariff::getSmsCost).reversed());
                break;
        }
    }
}
