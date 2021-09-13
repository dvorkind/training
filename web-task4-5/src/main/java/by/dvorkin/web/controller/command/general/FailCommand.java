package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class FailCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        req.setAttribute("fail", session.getAttribute("fail"));
        req.setAttribute("failTwo", session.getAttribute("failTwo"));
        session.removeAttribute("fail");
        session.removeAttribute("failTwo");
        return new Forward("/fail", false);
    }
}
