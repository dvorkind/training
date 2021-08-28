package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.TariffLastException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminTariffDeleteCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        if (req.getParameter("id") == null) {
            return new Forward("/admin/tariff_list.html");
        }
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            Tariff tariff = tariffService.readById(Long.parseLong(req.getParameter("id")));
            req.setAttribute("tariffName", tariff.getName());
            req.setAttribute("id", tariff.getId());
            int usingTariff = tariffService.getCountUsingTariff(tariff.getId());
            if (usingTariff > 0) {
                req.setAttribute("using", usingTariff);
                req.setAttribute("tariffs", tariffService.getAll());
            }
            if (req.getParameter("confirmation") != null) {
                if (req.getParameter("newTariff") != null) {
                    tariffService.switchTariffs(tariff.getId(), Long.parseLong(req.getParameter("newTariff")));
                }
                tariffService.safetyDelete(tariff.getId());
                Logger logger = LogManager.getLogger("User");
                logger.info("TariffID #" + req.getParameter("id") + " was deleted by Administrator");
                return new Forward("/admin/tariff_list.html");
            } else {
                return null;
            }
        } catch (TariffLastException e) {
            req.removeAttribute("confirmation");
            req.setAttribute("tariffDeleteError", "admin.tariffDeleteError");
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }
}
