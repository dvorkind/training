package by.dvorkin.web.model.service.exceptions;

public class ServiceAlreadyUsedException extends ServiceException {
    public ServiceAlreadyUsedException(String message) {
        super(message);
    }
}
