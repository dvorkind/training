package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageCommand implements Command {
    private static final String PAGE_PATH_REGEX = "(/jsp/)([A-Za-z0-9_\\-/]+)\\.jsp$";

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String locale = req.getParameter("locale");
        session.setAttribute("locale", locale);
        return new Forward(getURI(req));
    }

    private String getURI(HttpServletRequest request) {
        String pagePath = request.getParameter("pagePath");
        Pattern pattern = Pattern.compile(PAGE_PATH_REGEX);
        Matcher matcher = pattern.matcher(pagePath);
        String URI = "/error_404";
        if (matcher.find()) {
            URI = String.format("/%s.html", matcher.group(2));
        }
        return URI;
    }
}
