package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class AdminServicesListCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            ServiceService serviceService = serviceFactory.getServiceService();
            List<Service> services = serviceService.getAll();
            String sortBy = req.getParameter("sort");
            if (sortBy != null) {
                Helper.sortServices(sortBy, services);
                req.setAttribute("sort", sortBy);
            } else {
                Helper.sortServices("nameUp", services);
            }
            req.setAttribute("services", services);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
