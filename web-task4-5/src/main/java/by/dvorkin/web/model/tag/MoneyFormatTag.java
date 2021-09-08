package by.dvorkin.web.model.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.Locale;

public class MoneyFormatTag extends TagSupport {
    private int balance;
    private String locale;

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        String formattedPrice;
        switch (locale) {
            case "ru":
                formattedPrice = String.format(Locale.FRANCE, "%.2f", balance / 100.0);
                break;
            case "en":
                formattedPrice = String.format(Locale.US, "%.2f", balance / 100.0);
                break;
            default:
                formattedPrice = String.format("%.2f", balance / 100.0);
                break;
        }
        try {
            pageContext.getOut().write(formattedPrice);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
