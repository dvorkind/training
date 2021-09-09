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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminServiceDeleteCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        if (req.getParameter("id") == null) {
            return new Forward("/admin/service_list.html");
        }
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            ServiceService serviceService = serviceFactory.getServiceService();
            Service service = serviceService.getById(Long.parseLong(req.getParameter("id")));
            req.setAttribute("serviceName", service.getName());
            req.setAttribute("id", service.getId());
            if (req.getParameter("confirmation") != null) {
                serviceService.delete(service.getId());
                Logger logger = LogManager.getLogger("User");
                logger.info("ServiceID #" + req.getParameter("id") + " was deleted by Administrator");
                return new Forward("/admin/service_list.html");
            } else {
                return null;
            }
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
