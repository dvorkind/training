package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class AdminTariffsListCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            List<Tariff> tariffs = tariffService.getAll();
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortTariffs(sortBy, tariffs);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortTariffs("nameUp", tariffs);
            }
            req.setAttribute("tariffs", tariffs);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
