package by.dvorkin.web.controller.filter;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.service.ServiceFactory;
import by.dvorkin.web.model.service.SubscriberService;
import by.dvorkin.web.model.service.exceptions.FactoryException;
import by.dvorkin.web.model.service.exceptions.ServiceException;
import by.dvorkin.web.model.service.impl.ServiceFactoryImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SubscriberFilter extends HttpFilter {
    private static final Set<String> allowedBlockedSubscriberURI = new HashSet<>();
    private static final Set<String> allowedUnregisteredSubscriberURI = new HashSet<>();

    static {
        allowedBlockedSubscriberURI.add("/subscriber/refill_balance");
        allowedBlockedSubscriberURI.add("/subscriber/bills");
        allowedBlockedSubscriberURI.add("/subscriber/blocked");
        allowedBlockedSubscriberURI.add("/success");
        allowedBlockedSubscriberURI.add("/fail");
        allowedBlockedSubscriberURI.add("/logout");
        allowedBlockedSubscriberURI.add("/language");

        allowedUnregisteredSubscriberURI.add("/subscriber/registration_success");
        allowedUnregisteredSubscriberURI.add("/logout");
        allowedUnregisteredSubscriberURI.add("/language");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        String context = req.getContextPath();
        String url = Helper.extractPath(req.getRequestURI(), context);
        HttpSession session = req.getSession(false);
        if (session != null) {
            Account account = (Account) session.getAttribute("sessionAccount");
            if (account != null && account.getRole() == Role.SUBSCRIBER) {
                try (ServiceFactory serviceFactory = new ServiceFactoryImpl()) {
                    SubscriberService subscriberService = serviceFactory.getSubscriberService();
                    Subscriber subscriber = subscriberService.getByAccountId(account.getId());
                    session.setAttribute("sessionSubscriber", subscriber);
                    if (!subscriber.isRegistered()) {
                        if (!allowedUnregisteredSubscriberURI.contains(url)) {
                            resp.sendRedirect(context + "/subscriber/registration_success.html");
                            return;
                        }
                    }
                    if (subscriber.isBlocked()) {
                        if (!allowedBlockedSubscriberURI.contains(url)) {
                            resp.sendRedirect(context + "/subscriber/blocked.html");
                            return;
                        }
                    }                    
                } catch (ServiceException | FactoryException e) {
                    throw new ServletException(e);
                } catch (Exception ignored) {
                }
            }
        }
        chain.doFilter(req, resp);
    }
}
