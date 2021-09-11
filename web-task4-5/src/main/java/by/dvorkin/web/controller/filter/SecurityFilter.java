package by.dvorkin.web.controller.filter;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecurityFilter extends HttpFilter {
    private static final Map<String, Set<Role>> permissions = new HashMap<>();

    static {
        Set<Role> all = new HashSet<>(Arrays.asList(Role.values()));
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMINISTRATOR);
        Set<Role> subscriber = new HashSet<>();
        subscriber.add(Role.SUBSCRIBER);

        permissions.put("/logout", all);
        permissions.put("/change_password", all);
        permissions.put("/success", all);
        permissions.put("/fail", all);

        permissions.put("/admin/admin", admin);
        permissions.put("/admin/subscribers_new", admin);
        permissions.put("/admin/subscribers_all", admin);
        permissions.put("/admin/subscriber_edit", admin);
        permissions.put("/admin/subscriber_bills", admin);
        permissions.put("/admin/debtors", admin);
        permissions.put("/admin/tariff_list", admin);
        permissions.put("/admin/tariff_manage", admin);
        permissions.put("/admin/tariff_delete", admin);
        permissions.put("/admin/service_list", admin);
        permissions.put("/admin/service_delete", admin);
        permissions.put("/admin/service_manage", admin);

        permissions.put("/subscriber/subscriber", subscriber);
        permissions.put("/subscriber/tariff", subscriber);
        permissions.put("/subscriber/services", subscriber);
        permissions.put("/subscriber/bills", subscriber);
        permissions.put("/subscriber/refill_balance", subscriber);
        permissions.put("/subscriber/call", subscriber);
        permissions.put("/subscriber/sms", subscriber);
        permissions.put("/subscriber/statement", subscriber);
        permissions.put("/subscriber/blocked", subscriber);
        permissions.put("/subscriber/registration_success", subscriber);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        String url = Helper.extractPath(req.getRequestURI(), req.getContextPath());
        Set<Role> roles = permissions.get(url);
        if (roles != null) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                Account account = (Account) session.getAttribute("sessionAccount");
                if (account != null && roles.contains(account.getRole())) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        } else {
            chain.doFilter(req, resp);
            return;
        }
        resp.sendError(403);
    }
}
