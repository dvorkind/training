package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.UserService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminNewUsersCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            UserService userService = serviceFactory.getUserService();
            if (req.getParameter("id") != null) {
                userService.activate(Integer.parseInt(req.getParameter("id")));
                Logger logger = LogManager.getLogger("User");
                logger.info("UserID #" + req.getParameter("id") + " was activated by Administrator");
            }
            req.setAttribute("users", userService.getNewUsers());
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
