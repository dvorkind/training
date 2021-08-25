package by.dvorkin.web.model.entity;

public class Tariff {
    private Long id;
    private String name;
    private String description;
    private int subscriptionFee;
    private int callCost;
    private int smsCost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSubscriptionFee() {
        return subscriptionFee;
    }

    public void setSubscriptionFee(int subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }

    public int getCallCost() {
        return callCost;
    }

    public void setCallCost(int callCost) {
        this.callCost = callCost;
    }

    public int getSmsCost() {
        return smsCost;
    }

    public void setSmsCost(int smsCost) {
        this.smsCost = smsCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
