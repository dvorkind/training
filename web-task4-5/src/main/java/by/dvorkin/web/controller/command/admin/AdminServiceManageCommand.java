package by.dvorkin.web.controller.command.admin;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.ServiceService;
import by.dvorkin.web.model.service.exceptions.ServiceNameNotUniqueException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminServiceManageCommand implements Command {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁё0-9\\s'-]{5,20}$";
    private static final String SUBSCRIPTION_REGEX = "^[A-Za-zА-Яа-яЁё0-9\\s'~!@#$%^&*()-_=+'/|.]{5,200}$";

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
            ServiceService serviceService = serviceFactory.getServiceService();
            Service service;
            HttpSession session = req.getSession();
            if (req.getParameter("id") == null) {
                if (isInputValid(req)) {
                    service = createService(req);
                    serviceService.save(service);
                    Helper.log("ServiceID #" + service.getId() + " was added by Administrator");
                    session.setAttribute("success", "admin.serviceManageAddSuccess");
                    return new Forward("/success.html");
                }
            } else {
                if (isInputValid(req)) {
                    service = createService(req);
                    service.setId(Long.parseLong(req.getParameter("id")));
                    serviceService.save(service);
                    Helper.log("ServiceID #" + req.getParameter("id") + " was updated by Administrator");
                    session.setAttribute("success", "admin.serviceManageEditSuccess");
                    return new Forward("/success.html");
                } else {
                    service = serviceService.getById(Long.parseLong(req.getParameter("id")));
                    setServiceToAttribute(req, service);
                    return null;
                }
            }
        } catch (ServiceNameNotUniqueException e) {
            req.setAttribute("id", req.getParameter("id"));
            req.removeAttribute("serviceNameIsValid");
            req.setAttribute("serviceNameError", "admin.serviceManageErrorExists");
            return null;
        } catch (Exception e) {
            throw new ServletException(e);
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
                req.setAttribute("serviceNameError", "admin.serviceManageErrorEmpty");
                return false;
            }
            if (!serviceName.matches(NAME_REGEX)) {
                req.setAttribute("serviceNameError", "admin.serviceManageErrorName");
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
                req.setAttribute("serviceDescriptionError", "admin.serviceManageErrorEmpty");
                return false;
            }
            if (!serviceDescription.matches(SUBSCRIPTION_REGEX)) {
                req.setAttribute("serviceDescriptionError", "admin.serviceManageErrorDescription");
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
                req.setAttribute("servicePriceError", "admin.serviceManageErrorEmpty");
                return false;
            }
        }
        req.setAttribute("servicePriceIsValid", true);
        return true;
    }
}
