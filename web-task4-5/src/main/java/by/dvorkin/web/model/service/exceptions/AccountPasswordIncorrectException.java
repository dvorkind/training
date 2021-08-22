package by.dvorkin.web.model.service.exceptions;

public class AccountPasswordIncorrectException extends ServiceException {
    private Long id;

    public AccountPasswordIncorrectException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
