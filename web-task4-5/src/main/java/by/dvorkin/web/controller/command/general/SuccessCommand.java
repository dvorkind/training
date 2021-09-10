package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SuccessCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        req.setAttribute("success", session.getAttribute("success"));
        session.removeAttribute("success");
        return new Forward("/success", false);
    }
}
