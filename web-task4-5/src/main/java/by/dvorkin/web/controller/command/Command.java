package by.dvorkin.web.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    /**
     * Method processes the custom request and returns the Forward object
     *
     * @param req custom request
     * @return Forward class object
     * @throws ServletException in case of exception
     */
    Forward execute(HttpServletRequest req) throws ServletException;
}
