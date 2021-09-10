package by.dvorkin.web.model.service.exceptions;

public class AccountLoginNotUniqueException extends ServiceException {
    public AccountLoginNotUniqueException(String message) {
        super(message);
    }
}
