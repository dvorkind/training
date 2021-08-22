package by.dvorkin.web.model.service.exceptions;

public class AccountLoginNotUniqueException extends ServiceException {
    private String login;

    public AccountLoginNotUniqueException(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
