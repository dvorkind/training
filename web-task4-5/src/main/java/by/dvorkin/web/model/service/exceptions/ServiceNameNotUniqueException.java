package by.dvorkin.web.model.service.exceptions;

public class ServiceNameNotUniqueException extends ServiceException {
    private String serviceName;

    public ServiceNameNotUniqueException(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
