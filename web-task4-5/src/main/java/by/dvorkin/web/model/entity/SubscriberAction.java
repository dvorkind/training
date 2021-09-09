package by.dvorkin.web.model.entity;

import java.util.Date;

public class SubscriberAction extends Entity {
    private Long subscriberId;
    private Action action;
    private Date date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
