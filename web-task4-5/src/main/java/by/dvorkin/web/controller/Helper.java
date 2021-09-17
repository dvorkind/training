package by.dvorkin.web.controller;

import by.dvorkin.web.model.entity.Bill;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.entity.Tariff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class Helper {
    private static final Logger logger = LogManager.getLogger("User");

    public static String extractPath(String url, String context) {
        int postfixIndex = url.lastIndexOf(".html");
        if (postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        return url;
    }

    public static void log(String logMessage) {
        logger.info(logMessage);
    }

    public static void sortServices(String sortBy, List<Service> services) {
        switch (sortBy) {
            case "nameDown":
                services.sort(Comparator.comparing(Service::getName).reversed());
                break;
            case "priceUp":
                services.sort(Comparator.comparing(Service::getPrice));
                break;
            case "priceDown":
                services.sort(Comparator.comparing(Service::getPrice).reversed());
                break;
            default:
                services.sort(Comparator.comparing(Service::getName));
        }
    }

    public static void sortBills(String sortBy, List<Bill> bills) {
        switch (sortBy) {
            case "dateDown":
                bills.sort(Comparator.comparing(Bill::getInvoiceDate).reversed());
                break;
            case "sumUp":
                bills.sort(Comparator.comparing(Bill::getSum));
                break;
            case "sumDown":
                bills.sort(Comparator.comparing(Bill::getSum).reversed());
                break;
            case "statusUp":
                bills.sort(Comparator.comparing(Bill::isPaid));
                break;
            case "statusDown":
                bills.sort(Comparator.comparing(Bill::isPaid).reversed());
                break;
            default:
                bills.sort(Comparator.comparing(Bill::getInvoiceDate));
        }
    }

    public static void sortActions(String sortBy, List<SubscriberAction> actionList) {
        switch (sortBy) {
            case "nameDown":
                actionList.sort(Comparator.comparing((SubscriberAction subscriberAction) -> subscriberAction.getAction()
                        .getName()).reversed());
                break;
            case "dateUp":
                actionList.sort(Comparator.comparing(SubscriberAction::getDate));
                break;
            case "dateDown":
                actionList.sort(Comparator.comparing(SubscriberAction::getDate).reversed());
                break;
            case "sumUp":
                actionList.sort(Comparator.comparing(SubscriberAction::getSum));
                break;
            case "sumDown":
                actionList.sort(Comparator.comparing(SubscriberAction::getSum).reversed());
                break;
            default:
                actionList.sort(Comparator.comparing(subscriberAction -> subscriberAction.getAction().getName()));
        }
    }

    public static void sortSubscribers(String sortBy, List<Subscriber> subscribers) {
        switch (sortBy) {
            case "firstNameDown":
                subscribers.sort(Comparator.comparing(Subscriber::getFirstname).reversed());
                break;
            case "lastNameUp":
                subscribers.sort(Comparator.comparing(Subscriber::getLastname));
                break;
            case "lastNameDown":
                subscribers.sort(Comparator.comparing(Subscriber::getLastname).reversed());
                break;
            case "balanceUp":
                subscribers.sort(Comparator.comparing(Subscriber::getBalance));
                break;
            case "balanceDown":
                subscribers.sort(Comparator.comparing(Subscriber::getBalance).reversed());
                break;
            case "stateUp":
                subscribers.sort(Comparator.comparing(Subscriber::isBlocked));
                break;
            case "stateDown":
                subscribers.sort(Comparator.comparing(Subscriber::isBlocked).reversed());
                break;
            default:
                subscribers.sort(Comparator.comparing(Subscriber::getFirstname));
        }
    }

    public static void sortTariffs(String sortBy, List<Tariff> tariffs) {
        switch (sortBy) {
            case "nameDown":
                tariffs.sort(Comparator.comparing(Tariff::getName).reversed());
                break;
            case "subscriptionFeeUp":
                tariffs.sort(Comparator.comparing(Tariff::getSubscriptionFee));
                break;
            case "subscriptionFeeDown":
                tariffs.sort(Comparator.comparing(Tariff::getSubscriptionFee).reversed());
                break;
            case "callCostUp":
                tariffs.sort(Comparator.comparing(Tariff::getCallCost));
                break;
            case "callCostDown":
                tariffs.sort(Comparator.comparing(Tariff::getCallCost).reversed());
                break;
            case "smsCostUp":
                tariffs.sort(Comparator.comparing(Tariff::getSmsCost));
                break;
            case "smsCostDown":
                tariffs.sort(Comparator.comparing(Tariff::getSmsCost).reversed());
                break;
            default:
                tariffs.sort(Comparator.comparing(Tariff::getName));
        }
    }
}
