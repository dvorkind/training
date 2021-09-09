package by.dvorkin.web.model.entity;

public class Subscriber extends Entity {
    private Long accountId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Long tariff;
    private int balance;
    private boolean blocked;
    private boolean registered;

    public Subscriber() {
    }

    public Long getTariff() {
        return tariff;
    }

    public void setTariff(Long tariff) {
        this.tariff = tariff;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscriber)) return false;

        Subscriber that = (Subscriber) o;

        if (getBalance() != that.getBalance()) return false;
        if (isBlocked() != that.isBlocked()) return false;
        if (isRegistered() != that.isRegistered()) return false;
        if (getAccountId() != null ? !getAccountId().equals(that.getAccountId()) : that.getAccountId() != null)
            return false;
        if (getFirstname() != null ? !getFirstname().equals(that.getFirstname()) : that.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(that.getLastname()) : that.getLastname() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(that.getPhoneNumber()) : that.getPhoneNumber() != null)
            return false;
        return getTariff() != null ? getTariff().equals(that.getTariff()) : that.getTariff() == null;
    }

    @Override
    public int hashCode() {
        int result = getAccountId() != null ? getAccountId().hashCode() : 0;
        result = 31 * result + (getFirstname() != null ? getFirstname().hashCode() : 0);
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getTariff() != null ? getTariff().hashCode() : 0);
        result = 31 * result + getBalance();
        result = 31 * result + (isBlocked() ? 1 : 0);
        result = 31 * result + (isRegistered() ? 1 : 0);
        return result;
    }
}
