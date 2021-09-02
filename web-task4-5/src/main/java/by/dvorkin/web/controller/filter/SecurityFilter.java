package by.dvorkin.web.controller.filter;

import by.dvorkin.web.controller.Helper;
import by.dvorkin.web.model.entity.Account;
import by.dvorkin.web.model.entity.Role;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecurityFilter implements Filter {
    private static Map<String, Set<Role>> permissions = new HashMap<>();

    static {
        Set<Role> all = new HashSet<>(Arrays.asList(Role.values()));
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMINISTRATOR);
        Set<Role> subscriber = new HashSet<>();
        subscriber.add(Role.SUBSCRIBER);

        permissions.put("/logout", all);
        permissions.put("/change_password", all);

        permissions.put("/admin/admin", admin);
        permissions.put("/admin/subscribers_new", admin);
        permissions.put("/admin/subscribers_all", admin);
        permissions.put("/admin/tariff_list", admin);
        permissions.put("/admin/tariff_manage", admin);
        permissions.put("/admin/tariff_delete", admin);
        permissions.put("/admin/service_list", admin);
        permissions.put("/admin/service_delete", admin);
        permissions.put("/admin/service_manage", admin);

        permissions.put("/subscriber/subscriber", subscriber);
        permissions.put("/subscriber/tariff", subscriber);
        permissions.put("/subscriber/services", subscriber);
        permissions.put("/subscriber/refill_balance", subscriber);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        String context = httpReq.getContextPath();
        String url = Helper.extractPath(httpReq.getRequestURI(), context);
        Set<Role> roles = permissions.get(url);
        if (roles != null) {
            HttpSession session = httpReq.getSession(false);
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
        httpResp.sendError(403);
    }
}
