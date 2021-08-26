package by.dvorkin.web.model.service.exceptions;

public class TariffLastException extends ServiceException {
    private Long tariffId;

    public TariffLastException(Long tariffId) {
        this.tariffId = tariffId;
    }

    public Long getTariffId() {
        return tariffId;
    }
}
