package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SuccessCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        req.setAttribute("success", session.getAttribute("success"));
        req.setAttribute("successTwo", session.getAttribute("successTwo"));
        session.removeAttribute("success");
        session.removeAttribute("successTwo");
        return new Forward("/success", false);
    }
}
