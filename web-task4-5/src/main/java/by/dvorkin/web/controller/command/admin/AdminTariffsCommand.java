package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminTariffsCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            req.setAttribute("tariffs", tariffService.getAll());
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
