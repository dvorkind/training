package by.dvorkin.web.controller.filter;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import by.dvorkin.web.model.entity.Subscriber;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SubscriberFilter implements Filter {
    private static Set<String> allowedBlockedSubscriberURI = new HashSet<>();
    private static Set<String> allowedUnregisteredSubscriberURI = new HashSet<>();

    static {
        allowedBlockedSubscriberURI.add("/subscriber/refill_balance");
        allowedBlockedSubscriberURI.add("/subscriber/bills");
        allowedBlockedSubscriberURI.add("/subscriber/blocked");
        allowedBlockedSubscriberURI.add("/logout");
        allowedBlockedSubscriberURI.add("/language");

        allowedUnregisteredSubscriberURI.add("/subscriber/registration_success");
        allowedUnregisteredSubscriberURI.add("/logout");
        allowedUnregisteredSubscriberURI.add("/language");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        String context = httpReq.getContextPath();
        String url = Helper.extractPath(httpReq.getRequestURI(), context);
        HttpSession session = httpReq.getSession(false);
        if (session != null) {
            Account account = (Account) session.getAttribute("sessionAccount");
            if (account != null && account.getRole() == Role.SUBSCRIBER) {
                Subscriber subscriber = (Subscriber) session.getAttribute("sessionSubscriber");
                if (!subscriber.isRegistered()) {
                    if (!allowedUnregisteredSubscriberURI.contains(url)) {
                        httpResp.sendRedirect(context + "/subscriber/registration_success.html");
                        return;
                    }
                }
                if (subscriber.isBlocked()) {
                    if (!allowedBlockedSubscriberURI.contains(url)) {
                        httpResp.sendRedirect(context + "/subscriber/blocked.html");
                        return;
                    }
                }
            }
        }
        chain.doFilter(req, resp);
    }
}
