package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class IndexPageCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            ServiceService serviceService = serviceFactory.getServiceService();
            List<Tariff> tariffs = tariffService.getAll();
            List<Service> services = serviceService.getAll();
            req.setAttribute("services", services);
            req.setAttribute("tariffs", tariffs);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
