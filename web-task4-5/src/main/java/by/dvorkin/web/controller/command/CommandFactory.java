package by.dvorkin.web.controller.command;

import by.dvorkin.web.controller.command.admin.AdminServiceDeleteCommand;
import by.dvorkin.web.controller.command.admin.AdminServiceManageCommand;
import by.dvorkin.web.controller.command.admin.AdminTariffDeleteCommand;
import by.dvorkin.web.controller.command.admin.AdminTariffManageCommand;
import by.dvorkin.web.controller.command.admin.AdminServicesListCommand;
import by.dvorkin.web.controller.command.admin.AdminSummaryCommand;
import by.dvorkin.web.controller.command.admin.AdminSubscribersNewListCommand;
import by.dvorkin.web.controller.command.admin.AdminTariffsListCommand;
import by.dvorkin.web.controller.command.admin.AdminSubscribersListCommand;
import by.dvorkin.web.controller.command.general.ChangePasswordCommand;
import by.dvorkin.web.controller.command.general.LanguageCommand;
import by.dvorkin.web.controller.command.general.LoginCommand;
import by.dvorkin.web.controller.command.general.LogoutCommand;
import by.dvorkin.web.controller.command.general.MainPageCommand;
import by.dvorkin.web.controller.command.general.RegistrationCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberSummaryCommand;
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
        commands.put("/admin/subscribers_new",  AdminSubscribersNewListCommand.class);
        commands.put("/admin/subscribers_all",  AdminSubscribersListCommand.class);
        commands.put("/admin/tariff_list",  AdminTariffsListCommand.class);
        commands.put("/admin/tariff_manage",  AdminTariffManageCommand.class);
        commands.put("/admin/tariff_delete",  AdminTariffDeleteCommand.class);
        commands.put("/admin/service_list",  AdminServicesListCommand.class);
        commands.put("/admin/service_delete",  AdminServiceDeleteCommand.class);
        commands.put("/admin/service_manage",  AdminServiceManageCommand.class);
        commands.put("/subscriber/subscriber",  SubscriberSummaryCommand.class);
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
