package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();
        return new Forward("/index.html");
    }
}
