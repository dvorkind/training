package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Comparator;
import java.util.List;

public class AdminServicesListCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            ServiceService serviceService = serviceFactory.getServiceService();
            List<Service> services = serviceService.getAll();
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                sortTable(sortBy, services);
                req.setAttribute("sort", sortBy);
            } else {
                services.sort(Comparator.comparing(Service::getName));
            }
            req.setAttribute("services", services);
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private void sortTable(String sortBy, List<Service> services) {
        switch (sortBy) {
            case "nameUp":
                services.sort(Comparator.comparing(Service::getName));
                break;
            case "nameDown":
                services.sort(Comparator.comparing(Service::getName).reversed());
                break;
            case "priceUp":
                services.sort(Comparator.comparing(Service::getPrice));
                break;
            case "priceDown":
                services.sort(Comparator.comparing(Service::getPrice).reversed());
                break;
        }
    }
}
