package by.dvorkin.web.model.service.exceptions;

public class UserPhoneNotUniqueException extends ServiceException {
    private String phoneNumber;

    public UserPhoneNotUniqueException(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
