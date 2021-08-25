package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainPageCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) {
        return new Forward("/index.html");
    }
}
