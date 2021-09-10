package by.dvorkin.web.model.entity;

public enum Action {
    REGISTRATION("action.registration"),
    CHANGE_TARIFF("action.changeTariff"),
    ADD_SERVICE("action.addService"),
    DELETE_SERVICE("action.deleteService"),
    REFILL_BALANCE("action.refillBalance"),
    PAY_BILL("action.payBill"),
    MAKE_CALL("action.makeCall"),
    SEND_SMS("action.sendSms");

    private String name;

    Action(String name) {
        this.name = name;
    }

    public Long getId() {
        return (long) ordinal();
    }

    public String getName() {
        return name;
    }
}
