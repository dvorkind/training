package by.dvorkin.web.controller.command.subscriber;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SubscriberSuccessCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        req.setAttribute("success", session.getAttribute("success"));
        session.removeAttribute("success");
        return new Forward("/subscriber/success", false);
    }
}
