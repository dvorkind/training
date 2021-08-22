package by.dvorkin.web.controller.command;

import by.dvorkin.web.controller.command.admin.SummaryCommand;
import by.dvorkin.web.controller.command.admin.NewUsersCommand;
import by.dvorkin.web.controller.command.admin.UsersCommand;
import by.dvorkin.web.controller.command.general.LanguageCommand;
import by.dvorkin.web.controller.command.general.LoginCommand;
import by.dvorkin.web.controller.command.general.LogoutCommand;
import by.dvorkin.web.controller.command.general.MainCommand;
import by.dvorkin.web.controller.command.general.RegistrationCommand;
import by.dvorkin.web.controller.command.user.UserCommand;
import jakarta.servlet.ServletException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Class<? extends Command>> commands = new HashMap<>();

    static {
        commands.put("/", MainCommand.class);
        commands.put("/login", LoginCommand.class);
        commands.put("/registration", RegistrationCommand.class);
        commands.put("/language", LanguageCommand.class);
        commands.put("/logout", LogoutCommand.class);
        commands.put("/admin/admin",  SummaryCommand.class);
        commands.put("/admin/new_users",  NewUsersCommand.class);
        commands.put("/admin/users",  UsersCommand.class);
        commands.put("/user/user",  UserCommand.class);
    }

    public static Command getCommand(String url) throws ServletException {
        Class<?> command = commands.get(url);
        if (command != null) {
            try {
                return (Command) command.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new ServletException(e);
            }
        } else {
            return null;
        }
    }
}
