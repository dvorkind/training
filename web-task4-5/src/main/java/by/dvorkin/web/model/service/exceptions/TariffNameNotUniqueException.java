package by.dvorkin.web.model.service.exceptions;

public class TariffNameNotUniqueException extends ServiceException {
    private String tariffName;

    public TariffNameNotUniqueException(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getTariffName() {
        return tariffName;
    }
}
