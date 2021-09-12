package by.dvorkin.web.model.entity;

import java.time.LocalDateTime;

public class SubscriberAction extends Entity {
    private Long subscriberId;
    private Action action;
    private LocalDateTime date;
    private int sum;

    public SubscriberAction() {
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
