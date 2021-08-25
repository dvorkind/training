package by.dvorkin.web.controller.command;

import by.dvorkin.web.controller.Helper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CommandManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String context = req.getContextPath();
        String url = Helper.extractPath(req.getRequestURI(), context);
        try {
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
        } catch (ServletException e) {
            Logger logger = LogManager.getLogger();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.error(sw.toString());
            resp.sendError(500);
        }
    }
}
