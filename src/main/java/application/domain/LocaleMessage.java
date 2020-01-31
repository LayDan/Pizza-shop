package application.domain;

import org.springframework.ui.Model;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleMessage {

    private String getMessage(Locale locale, String key) {
        ResourceBundle rb = ResourceBundle.getBundle("messageResource", locale);
        for (String a : rb.keySet()) {
            if (a.equals(key)) {
                return rb.getString(key);
            }
        }
        return "error";
    }

    public Model navBar(Model model, Locale locale) {
        model.addAttribute("catalog", getMessage(locale, "label.catalog"));
        model.addAttribute("account", getMessage(locale, "label.account"));
        model.addAttribute("basket", getMessage(locale, "label.basket"));
        model.addAttribute("registration", getMessage(locale, "label.registration"));
        model.addAttribute("login", getMessage(locale, "label.login"));
        model.addAttribute("password", getMessage(locale, "label.password"));
        return model;
    }

    public Model registration(Model model, Locale locale) {
        model.addAttribute("login", getMessage(locale, "label.login"));
        model.addAttribute("email", getMessage(locale, "label.email"));
        model.addAttribute("lastName", getMessage(locale, "label.lastName"));
        model.addAttribute("firstName", getMessage(locale, "label.firstName"));
        return model;
    }
}
