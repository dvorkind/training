package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.exceptions.ServiceNameNotUniqueException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminServiceManageCommand implements Command {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁё0-9\\s'-]{5,20}$";
    private static final String SUBSCRIPTION_REGEX = "^[A-Za-zА-Яа-яЁё0-9\\s'~!@#$%^&*()-_=+'/|.]{5,200}$";

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            Logger logger = LogManager.getLogger("User");
            ServiceService serviceService = serviceFactory.getServiceService();
            Service service;
            if (req.getParameter("id") == null) {
                if (isInputValid(req)) {
                    service = createService(req);
                    serviceService.save(service);
                    logger.info("ServiceID #" + service.getId() + " was added by Administrator");
                    return new Forward("/admin/service_list.html");
                }
            } else {
                if (isInputValid(req)) {
                    service = createService(req);
                    service.setId(Long.parseLong(req.getParameter("id")));
                    serviceService.save(service);
                    logger.info("ServiceID #" + req.getParameter("id") + " was updated by Administrator");
                    return new Forward("/admin/service_list.html");
                } else {
                    service = serviceService.getById(Long.parseLong(req.getParameter("id")));
                    setServiceToAttribute(req, service);
                    return null;
                }
            }
        } catch (ServiceNameNotUniqueException e) {
            req.setAttribute("id", req.getParameter("id"));
            req.removeAttribute("serviceNameIsValid");
            req.setAttribute("serviceNameError", "admin.errorExistServiceError");
            return null;
        } catch (ServiceException | FactoryException e) {
            throw new ServletException(e);
        } catch (Exception ignored) {
        }
        return null;
    }

    private void setServiceToAttribute(HttpServletRequest req, Service service) {
        req.setAttribute("id", service.getId());
        req.setAttribute("serviceName", service.getName());
        req.setAttribute("serviceDescription", service.getDescription());
        req.setAttribute("servicePriceRoubles", (service.getPrice() / 100));
        req.setAttribute("servicePriceKopecks", (service.getPrice() % 100));
    }

    private Service createService(HttpServletRequest req) {
        Service service = new Service();
        service.setName(req.getParameter("serviceName"));
        service.setDescription(req.getParameter("serviceDescription"));
        int servicePriceRoubles = Integer.parseInt(req.getParameter("servicePriceRoubles"));
        int servicePriceKopecks = Integer.parseInt(req.getParameter("servicePriceKopecks"));
        service.setPrice((servicePriceRoubles * 100) + servicePriceKopecks);
        return service;
    }

    private boolean isInputValid(HttpServletRequest req) {
        return isServiceNameValid(req) & isServiceDescriptionValid(req) & isServicePriceValid(req);
    }

    private boolean isServiceNameValid(HttpServletRequest req) {
        String serviceName = req.getParameter("serviceName");
        req.setAttribute("serviceName", serviceName);
        if (serviceName == null) {
            return false;
        } else {
            if (serviceName.trim().equals("")) {
                req.setAttribute("serviceNameError", "admin.serviceErrorEmpty");
                return false;
            }
            if (!serviceName.matches(NAME_REGEX)) {
                req.setAttribute("serviceNameError", "admin.serviceErrorName");
                return false;
            }
        }
        req.setAttribute("serviceNameIsValid", true);
        return true;
    }

    private boolean isServiceDescriptionValid(HttpServletRequest req) {
        String serviceDescription = req.getParameter("serviceDescription");
        req.setAttribute("serviceDescription", serviceDescription);
        if (serviceDescription == null) {
            return false;
        } else {
            if (serviceDescription.trim().equals("")) {
                req.setAttribute("serviceDescriptionError", "admin.serviceErrorEmpty");
                return false;
            }
            if (!serviceDescription.matches(SUBSCRIPTION_REGEX)) {
                req.setAttribute("serviceDescriptionError", "admin.serviceErrorDescription");
                return false;
            }
        }
        req.setAttribute("serviceDescriptionIsValid", true);
        return true;
    }

    private boolean isServicePriceValid(HttpServletRequest req) {
        String servicePriceRoubles = req.getParameter("servicePriceRoubles");
        String servicePriceKopecks = req.getParameter("servicePriceKopecks");
        req.setAttribute("servicePriceRoubles", servicePriceRoubles);
        req.setAttribute("servicePriceKopecks", servicePriceKopecks);
        if (servicePriceRoubles == null || servicePriceKopecks == null) {
            return false;
        } else {
            if (servicePriceRoubles.trim().equals("") || servicePriceKopecks.trim().equals("")) {
                req.setAttribute("servicePriceError", "admin.serviceErrorEmpty");
                return false;
            }
        }
        req.setAttribute("servicePriceIsValid", true);
        return true;
    }
}
