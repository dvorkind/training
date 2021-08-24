package by.dvorkin.web.controller.command.general;

import by.dvorkin.web.controller.command.Command;
import by.dvorkin.web.controller.command.Forward;
import by.dvorkin.web.model.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements Command {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Account sessionAccount = (Account) session.getAttribute("sessionAccount");
            Logger logger = LogManager.getLogger("User");
            logger.info("User " + sessionAccount.getLogin() + " has logged out. IP [" + req.getRemoteAddr() + "]");
            session.invalidate();
        }
        return new Forward("/index.html");
    }
}
