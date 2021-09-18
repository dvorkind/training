package by.dvorkin.web.controller.command;

import by.dvorkin.web.controller.command.admin.AdminDebtorsListCommand;
import by.dvorkin.web.controller.command.admin.AdminServiceDeleteCommand;
import by.dvorkin.web.controller.command.admin.AdminServiceManageCommand;
import by.dvorkin.web.controller.command.admin.AdminServicesListCommand;
import by.dvorkin.web.controller.command.admin.AdminSubscriberBillsCommand;
import by.dvorkin.web.controller.command.admin.AdminSubscriberEditCommand;
import by.dvorkin.web.controller.command.admin.AdminSubscribersListCommand;
import by.dvorkin.web.controller.command.admin.AdminSubscribersNewListCommand;
import by.dvorkin.web.controller.command.admin.AdminSummaryCommand;
import by.dvorkin.web.controller.command.admin.AdminTariffDeleteCommand;
import by.dvorkin.web.controller.command.admin.AdminTariffManageCommand;
import by.dvorkin.web.controller.command.admin.AdminTariffsListCommand;
import by.dvorkin.web.controller.command.general.ChangePasswordCommand;
import by.dvorkin.web.controller.command.general.FailCommand;
import by.dvorkin.web.controller.command.general.IndexPageCommand;
import by.dvorkin.web.controller.command.general.LanguageCommand;
import by.dvorkin.web.controller.command.general.LoginCommand;
import by.dvorkin.web.controller.command.general.LogoutCommand;
import by.dvorkin.web.controller.command.general.MainPageCommand;
import by.dvorkin.web.controller.command.general.RegistrationCommand;
import by.dvorkin.web.controller.command.general.ResetPasswordCommand;
import by.dvorkin.web.controller.command.general.SuccessCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberBillsCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberCallCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberRefillBalanceCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberServicesCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberSmsCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberStatementCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberSummaryCommand;
import by.dvorkin.web.controller.command.subscriber.SubscriberTariffChangeCommand;
import jakarta.servlet.ServletException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Class<? extends Command>> commands = new HashMap<>();

    static {
        commands.put("/", MainPageCommand.class);
        commands.put("/index", IndexPageCommand.class);
        commands.put("/login", LoginCommand.class);
        commands.put("/registration", RegistrationCommand.class);
        commands.put("/reset_password", ResetPasswordCommand.class);
        commands.put("/language", LanguageCommand.class);
        commands.put("/logout", LogoutCommand.class);
        commands.put("/change_password", ChangePasswordCommand.class);
        commands.put("/success", SuccessCommand.class);
        commands.put("/fail", FailCommand.class);

        commands.put("/admin/admin", AdminSummaryCommand.class);
        commands.put("/admin/subscribers_new", AdminSubscribersNewListCommand.class);
        commands.put("/admin/subscribers_all", AdminSubscribersListCommand.class);
        commands.put("/admin/subscriber_edit", AdminSubscriberEditCommand.class);
        commands.put("/admin/subscriber_bills", AdminSubscriberBillsCommand.class);
        commands.put("/admin/debtors", AdminDebtorsListCommand.class);
        commands.put("/admin/tariff_list", AdminTariffsListCommand.class);
        commands.put("/admin/tariff_manage", AdminTariffManageCommand.class);
        commands.put("/admin/tariff_delete", AdminTariffDeleteCommand.class);
        commands.put("/admin/service_list", AdminServicesListCommand.class);
        commands.put("/admin/service_delete", AdminServiceDeleteCommand.class);
        commands.put("/admin/service_manage", AdminServiceManageCommand.class);

        commands.put("/subscriber/subscriber", SubscriberSummaryCommand.class);
        commands.put("/subscriber/tariff", SubscriberTariffChangeCommand.class);
        commands.put("/subscriber/services", SubscriberServicesCommand.class);
        commands.put("/subscriber/bills", SubscriberBillsCommand.class);
        commands.put("/subscriber/refill_balance", SubscriberRefillBalanceCommand.class);
        commands.put("/subscriber/call", SubscriberCallCommand.class);
        commands.put("/subscriber/sms", SubscriberSmsCommand.class);
        commands.put("/subscriber/statement", SubscriberStatementCommand.class);
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
