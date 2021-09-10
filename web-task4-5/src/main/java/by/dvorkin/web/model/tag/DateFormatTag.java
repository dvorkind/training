package by.dvorkin.web.model.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTag extends TagSupport {
    private Date dateTime;
    private String locale;

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        SimpleDateFormat formattedDate = new SimpleDateFormat();
        if ("en".equals(locale)) {
            formattedDate.applyPattern("dd/MM/yyyy");
        } else {
            formattedDate.applyPattern("dd.MM.yyyy");
        }
        try {
            pageContext.getOut().write(formattedDate.format(dateTime));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
