package by.dvorkin.web.model.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatTag extends TagSupport {
    private LocalDateTime dateTime;
    private String locale;

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        DateTimeFormatter formatter;
        if ("en".equals(locale)) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        } else {
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        }
        try {
            pageContext.getOut().write(dateTime.format(formatter));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
