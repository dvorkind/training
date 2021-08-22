package by.dvorkin.web.model.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class MoneyFormatTag extends TagSupport {
    private int balance;

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int doStartTag() throws JspException {
        //TODO: правильно ли обрабатываю деньги
        String formattedPrice;
        if (balance < 0) {
            balance = Math.abs(balance);
            formattedPrice = "-" + String.format("%d,%02d", balance / 100, balance % 100);
        } else {
            formattedPrice = String.format("%d,%02d", balance / 100, balance % 100);
        }
        try {
            pageContext.getOut()
                    .write(formattedPrice);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
