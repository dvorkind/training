package by.dvorkin.web.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
    Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException;
}
