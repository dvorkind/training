package by.dvorkin.web.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Forward execute(HttpServletRequest req) throws ServletException;
}
