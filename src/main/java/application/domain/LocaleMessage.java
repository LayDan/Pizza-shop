package application.domain;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleMessage {

    public String getMessage(Locale locale, String key) {


        ResourceBundle rb = ResourceBundle.getBundle("messageResource", locale);
        for (String a : rb.keySet()) {
            if (a.equals(key)) {
                return rb.getString(key);
            }
        }
        return "error";
    }
}
