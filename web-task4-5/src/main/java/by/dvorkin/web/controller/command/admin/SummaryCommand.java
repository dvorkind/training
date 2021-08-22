package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.UserService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SummaryCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Account account = (Account) session.getAttribute("sessionAccount");
            if (account != null && (account.getRole() == Role.ADMINISTRATOR)) {
                try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                    UserService userService = serviceFactory.getUserService();
                    //TODO: статистику
                    return null;
                } catch (ServiceException | FactoryException e) {
                    throw new ServletException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
