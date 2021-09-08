package by.dvorkin.web.controller;

import by.dvorkin.web.model.entity.Action;
import by.dvorkin.web.model.entity.Service;
import by.dvorkin.web.model.entity.Subscriber;
import by.dvorkin.web.model.entity.SubscriberAction;
import by.dvorkin.web.model.entity.Tariff;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Helper {
    public static String extractPath(String url, String context) {
        int postfixIndex = url.lastIndexOf(".html");
        if (postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        return url;
    }

    public static SubscriberAction createSubscriberAction(Long subscriberId, Action action, int sum) {
        SubscriberAction subscriberAction = new SubscriberAction();
        subscriberAction.setAction(action);
        subscriberAction.setSubscriberId(subscriberId);
        subscriberAction.setDate(new Date());
        subscriberAction.setSum(sum);
        return subscriberAction;
    }

    public static void sortServices(String sortBy, List<Service> services) {
        switch (sortBy) {
            case "nameUp":
                services.sort(Comparator.comparing(Service::getName));
                break;
            case "nameDown":
                services.sort(Comparator.comparing(Service::getName).reversed());
                break;
            case "priceUp":
                services.sort(Comparator.comparing(Service::getPrice));
                break;
            case "priceDown":
                services.sort(Comparator.comparing(Service::getPrice).reversed());
                break;
        }
    }

    public static void sortSubscribers(String sortBy, List<Subscriber> subscribers) {
        switch (sortBy) {
            case "firstNameUp":
                subscribers.sort(Comparator.comparing(Subscriber::getFirstname));
                break;
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
        }
    }

    public static void sortTariffs(String sortBy, List<Tariff> tariffs) {
        switch (sortBy) {
            case "nameUp":
                tariffs.sort(Comparator.comparing(Tariff::getName));
                break;
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
        }
    }
}
