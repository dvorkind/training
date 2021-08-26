package by.dvorkin.web.controller.command;

import by.dvorkin.web.controller.command.admin.AdminDeleteTariffCommand;
import by.dvorkin.web.controller.command.admin.AdminManageTariffCommand;
import by.dvorkin.web.controller.command.admin.AdminSummaryCommand;
import by.dvorkin.web.controller.command.admin.AdminNewUsersCommand;
import by.dvorkin.web.controller.command.admin.AdminTariffsCommand;
import by.dvorkin.web.controller.command.admin.AdminUsersCommand;
import by.dvorkin.web.controller.command.general.ChangePasswordCommand;
import by.dvorkin.web.controller.command.general.LanguageCommand;
import by.dvorkin.web.controller.command.general.LoginCommand;
import by.dvorkin.web.controller.command.general.LogoutCommand;
import by.dvorkin.web.controller.command.general.MainPageCommand;
import by.dvorkin.web.controller.command.general.RegistrationCommand;
import by.dvorkin.web.controller.command.user.UserSummaryCommand;
import jakarta.servlet.ServletException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Class<? extends Command>> commands = new HashMap<>();

    static {
        commands.put("/", MainPageCommand.class);
        commands.put("/login", LoginCommand.class);
        commands.put("/registration", RegistrationCommand.class);
        commands.put("/language", LanguageCommand.class);
        commands.put("/logout", LogoutCommand.class);
        commands.put("/admin/admin",  AdminSummaryCommand.class);
        commands.put("/admin/users_new",  AdminNewUsersCommand.class);
        commands.put("/admin/users_all",  AdminUsersCommand.class);
        commands.put("/admin/tariff_list",  AdminTariffsCommand.class);
        commands.put("/admin/tariff_manage",  AdminManageTariffCommand.class);
        commands.put("/admin/tariff_delete",  AdminDeleteTariffCommand.class);
        commands.put("/user/user",  UserSummaryCommand.class);
        commands.put("/change_password",  ChangePasswordCommand.class);
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
