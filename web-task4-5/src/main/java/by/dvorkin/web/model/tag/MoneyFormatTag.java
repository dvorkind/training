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
        String formattedPrice;
        int absBalance = Math.abs(balance);
        formattedPrice = String.format("%d,%02d", absBalance / 100, absBalance % 100);
        if (balance < 0) {
            formattedPrice = "-" + formattedPrice;
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
