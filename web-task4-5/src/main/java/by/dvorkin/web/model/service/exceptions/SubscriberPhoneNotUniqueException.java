package by.dvorkin.web.model.service.exceptions;

public class SubscriberPhoneNotUniqueException extends ServiceException {
    private String phoneNumber;

    public SubscriberPhoneNotUniqueException(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
