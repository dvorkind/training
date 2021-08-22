package by.dvorkin.web.controller.command;

import by.dvorkin.web.controller.PathExtractor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CommandManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String context = req.getContextPath();
        String url = PathExtractor.extractPath(req.getRequestURI(), context);
        Command command = CommandFactory.getCommand(url);
        Forward forward = null;
        if (command != null) {
            forward = command.execute(req, resp);
        }
        if (forward != null && forward.isRedirect()) {
            resp.sendRedirect(context + forward.getUrl());
        } else {
            if (forward != null && forward.getUrl() != null) {
                url = forward.getUrl();
            }
            req.getRequestDispatcher("/WEB-INF/jsp" + url + ".jsp").forward(req, resp);
        }
    }
}
