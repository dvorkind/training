package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.TariffLastException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AdminTariffDeleteCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) throws ServletException {
        if (req.getParameter("id") == null) {
            return new Forward("/admin/tariff_list.html");
        }
        HttpSession session = req.getSession();
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            Tariff tariff = tariffService.getById(Long.parseLong(req.getParameter("id")));
            req.setAttribute("tariffName", tariff.getName());
            req.setAttribute("id", tariff.getId());
            int usingTariff = tariffService.getCountUsingTariff(tariff.getId());
            if (usingTariff > 0) {
                req.setAttribute("using", usingTariff);
                req.setAttribute("tariffs", tariffService.getAll());
            }
            if (req.getParameter("confirmation") != null) {
                long newTariffId = 0L;
                if (req.getParameter("newTariff") != null) {
                    newTariffId = Long.parseLong(req.getParameter("newTariff"));
                }
                tariffService.safetyDelete(tariff.getId(), newTariffId);
                Helper.log("TariffID #" + req.getParameter("id") + " was deleted by Administrator");
                session.setAttribute("success", "admin.tariffDeleteSuccess");
                return new Forward("/success.html");
            }
        } catch (TariffLastException e) {
            session.setAttribute("fail", "admin.tariffDeleteError");
            return new Forward("/fail.html");
        } catch (Exception e) {
            throw new ServletException(e);
        }
        return null;
    }
}
