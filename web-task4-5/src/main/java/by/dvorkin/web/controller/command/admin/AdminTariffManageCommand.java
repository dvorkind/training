package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Tariff;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.TariffService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.TariffNameNotUniqueException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminTariffManageCommand implements Command {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁё0-9\\s'-]{5,20}$";
    private static final String SUBSCRIPTION_REGEX = "^[A-Za-zА-Яа-яЁё0-9\\s'~!@#$%^&*()-_=+'/|.]{5,200}$";

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            TariffService tariffService = serviceFactory.getTariffService();
            Tariff tariff;
            if (req.getParameter("id") == null) {
                if (isInputValid(req)) {
                    tariff = createTariff(req);
                    tariffService.save(tariff);
                    Helper.log("TariffID #" + tariff.getId() + " was added by Administrator");
                    return new Forward("/admin/tariff_list.html");
                }
            } else {
                if (isInputValid(req)) {
                    tariff = createTariff(req);
                    tariff.setId(Long.parseLong(req.getParameter("id")));
                    tariffService.save(tariff);
                    Helper.log("TariffID #" + req.getParameter("id") + " was updated by Administrator");
                    return new Forward("/admin/tariff_list.html");
                } else {
                    tariff = tariffService.getById(Long.parseLong(req.getParameter("id")));
                    setTariffToAttribute(req, tariff);
                    return null;
                }
            }
        } catch (TariffNameNotUniqueException e) {
            req.setAttribute("id", req.getParameter("id"));
            req.removeAttribute("tariffNameIsValid");
            req.setAttribute("tariffNameError", "admin.errorExistTariffError");
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private void setTariffToAttribute(HttpServletRequest req, Tariff tariff) {
        req.setAttribute("id", tariff.getId());
        req.setAttribute("tariffName", tariff.getName());
        req.setAttribute("tariffDescription", tariff.getDescription());
        req.setAttribute("tariffSubscriptionFeeRoubles", (tariff.getSubscriptionFee() / 100));
        req.setAttribute("tariffSubscriptionFeeKopecks", (tariff.getSubscriptionFee() % 100));
        req.setAttribute("tariffCallCostRoubles", (tariff.getCallCost() / 100));
        req.setAttribute("tariffCallCostKopecks", (tariff.getCallCost() % 100));
        req.setAttribute("tariffSmsCostRoubles", (tariff.getSmsCost() / 100));
        req.setAttribute("tariffSmsCostKopecks", (tariff.getSmsCost() % 100));
    }

    private Tariff createTariff(HttpServletRequest req) {
        Tariff tariff = new Tariff();
        tariff.setName(req.getParameter("tariffName"));
        tariff.setDescription(req.getParameter("tariffDescription"));
        int tariffSubscriptionFeeRoubles = Integer.parseInt(req.getParameter("tariffSubscriptionFeeRoubles"));
        int tariffSubscriptionFeeKopecks = Integer.parseInt(req.getParameter("tariffSubscriptionFeeKopecks"));
        tariff.setSubscriptionFee((tariffSubscriptionFeeRoubles * 100) + tariffSubscriptionFeeKopecks);
        int tariffCallCostRoubles = Integer.parseInt(req.getParameter("tariffCallCostRoubles"));
        int tariffCallCostKopecks = Integer.parseInt(req.getParameter("tariffCallCostKopecks"));
        tariff.setCallCost((tariffCallCostRoubles * 100) + tariffCallCostKopecks);
        int tariffSmsCostRoubles = Integer.parseInt(req.getParameter("tariffSmsCostRoubles"));
        int tariffSmsCostKopecks = Integer.parseInt(req.getParameter("tariffSmsCostKopecks"));
        tariff.setSmsCost((tariffSmsCostRoubles * 100) + tariffSmsCostKopecks);
        return tariff;
    }

    private boolean isInputValid(HttpServletRequest req) {
        return isTariffNameValid(req) & isTariffDescriptionValid(req) & isTariffSubscriptionFeeValid(req) & isTariffCallCostValid(req) & isTariffSmsCostValid(req);
    }

    private boolean isTariffNameValid(HttpServletRequest req) {
        String tariffName = req.getParameter("tariffName");
        req.setAttribute("tariffName", tariffName);
        if (tariffName == null) {
            return false;
        } else {
            if (tariffName.trim().equals("")) {
                req.setAttribute("tariffNameError", "admin.tariffErrorEmpty");
                return false;
            }
            if (!tariffName.matches(NAME_REGEX)) {
                req.setAttribute("tariffNameError", "admin.tariffErrorName");
                return false;
            }
        }
        req.setAttribute("tariffNameIsValid", true);
        return true;
    }

    private boolean isTariffDescriptionValid(HttpServletRequest req) {
        String tariffDescription = req.getParameter("tariffDescription");
        req.setAttribute("tariffDescription", tariffDescription);
        if (tariffDescription == null) {
            return false;
        } else {
            if (tariffDescription.trim().equals("")) {
                req.setAttribute("tariffDescriptionError", "admin.tariffErrorEmpty");
                return false;
            }
            if (!tariffDescription.matches(SUBSCRIPTION_REGEX)) {
                req.setAttribute("tariffDescriptionError", "admin.tariffErrorDescription");
                return false;
            }
        }
        req.setAttribute("tariffDescriptionIsValid", true);
        return true;
    }

    private boolean isTariffSubscriptionFeeValid(HttpServletRequest req) {
        String tariffSubscriptionFeeRoubles = req.getParameter("tariffSubscriptionFeeRoubles");
        String tariffSubscriptionFeeKopecks = req.getParameter("tariffSubscriptionFeeKopecks");
        req.setAttribute("tariffSubscriptionFeeRoubles", tariffSubscriptionFeeRoubles);
        req.setAttribute("tariffSubscriptionFeeKopecks", tariffSubscriptionFeeKopecks);
        if (tariffSubscriptionFeeRoubles == null || tariffSubscriptionFeeKopecks == null) {
            return false;
        } else {
            if (tariffSubscriptionFeeRoubles.trim().equals("") || tariffSubscriptionFeeKopecks.trim().equals("")) {
                req.setAttribute("tariffSubscriptionFeeError", "admin.tariffErrorEmpty");
                return false;
            }
        }
        req.setAttribute("tariffSubscriptionFeeIsValid", true);
        return true;
    }

    private boolean isTariffCallCostValid(HttpServletRequest req) {
        String tariffCallCostRoubles = req.getParameter("tariffCallCostRoubles");
        String tariffCallCostKopecks = req.getParameter("tariffCallCostKopecks");
        req.setAttribute("tariffCallCostRoubles", tariffCallCostRoubles);
        req.setAttribute("tariffCallCostKopecks", tariffCallCostKopecks);
        if (tariffCallCostRoubles == null || tariffCallCostKopecks == null) {
            return false;
        } else {
            if (tariffCallCostRoubles.trim().equals("") || tariffCallCostKopecks.trim().equals("")) {
                req.setAttribute("tariffCallCostError", "admin.tariffErrorEmpty");
                return false;
            }
        }
        req.setAttribute("tariffCallCostIsValid", true);
        return true;
    }

    private boolean isTariffSmsCostValid(HttpServletRequest req) {
        String tariffSmsCostRoubles = req.getParameter("tariffSmsCostRoubles");
        String tariffSmsCostKopecks = req.getParameter("tariffSmsCostKopecks");
        req.setAttribute("tariffSmsCostRoubles", tariffSmsCostRoubles);
        req.setAttribute("tariffSmsCostKopecks", tariffSmsCostKopecks);
        if (tariffSmsCostRoubles == null || tariffSmsCostKopecks == null) {
            return false;
        } else {
            if (tariffSmsCostRoubles.trim().equals("") || tariffSmsCostKopecks.trim().equals("")) {
                req.setAttribute("tariffSmsCostError", "admin.tariffErrorEmpty");
                return false;
            }
        }
        req.setAttribute("tariffSmsCostIsValid", true);
        return true;
    }
}
