package application.controller;

import application.domain.LocaleMessage;
import application.domain.UserProfile;
import application.service.IUserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;
import java.util.Map;

@Controller
public class MainPage {

    public MainPage(IUserProfileService iUserProfileService) {
        this.iUserProfileService = iUserProfileService;
    }

    private IUserProfileService iUserProfileService;


    @GetMapping("/login")
    public String startLogin(Map<String, Object> model) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.put("catalog",localeMessage.getMessage(Locale.getDefault(), "label.katalog"));
        model.put("account",localeMessage.getMessage(Locale.getDefault(), "label.account"));
        model.put("basket",localeMessage.getMessage(Locale.getDefault(), "label.basket"));
        model.put("registration",localeMessage.getMessage(Locale.getDefault(), "label.registration"));

        model.put("login", localeMessage.getMessage(Locale.getDefault(), "label.login"));
        model.put("password", localeMessage.getMessage(Locale.getDefault(), "label.password"));
        return "login";
    }


    @GetMapping("/registration")
    public String startRegistration(Map<String, Object> model) {
        LocaleMessage localeMessage = new LocaleMessage();

        model.put("catalog",localeMessage.getMessage(Locale.getDefault(), "label.katalog"));
        model.put("account",localeMessage.getMessage(Locale.getDefault(), "label.account"));
        model.put("basket",localeMessage.getMessage(Locale.getDefault(), "label.basket"));
        model.put("registration",localeMessage.getMessage(Locale.getDefault(), "label.registration"));

        model.put("login", localeMessage.getMessage(Locale.getDefault(), "label.login"));
        model.put("password", localeMessage.getMessage(Locale.getDefault(), "label.password"));
        model.put("lastName", localeMessage.getMessage(Locale.getDefault(), "label.lastName"));
        model.put("firstName", localeMessage.getMessage(Locale.getDefault(), "label.firstName"));

        return "registration";
    }

    @PostMapping("/registration")
    public String endRegistration(Model model, UserProfile user) {
        if (iUserProfileService.findByUsername(user.getUsername())) {
            model.addAttribute("info", "User exist");
        } else {
            iUserProfileService.addUser(user);
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String gretting(Model model) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute("catalog",localeMessage.getMessage(Locale.getDefault(), "label.katalog"));
        model.addAttribute("account",localeMessage.getMessage(Locale.getDefault(), "label.account"));
        model.addAttribute("basket",localeMessage.getMessage(Locale.getDefault(), "label.basket"));
        model.addAttribute("registration",localeMessage.getMessage(Locale.getDefault(), "label.registration"));

        model.addAttribute("login", localeMessage.getMessage(Locale.getDefault(), "label.login"));
        model.addAttribute("password", localeMessage.getMessage(Locale.getDefault(), "label.password"));
        if (iUserProfileService.getCurrentUser() != null) {
            model.addAttribute("infoUser", iUserProfileService.getCurrentUser());
        }
        return "main";
    }

    @GetMapping("/main")
    public String mainWindow2(Model model) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute("catalog",localeMessage.getMessage(Locale.getDefault(), "label.katalog"));
        model.addAttribute("account",localeMessage.getMessage(Locale.getDefault(), "label.account"));
        model.addAttribute("basket",localeMessage.getMessage(Locale.getDefault(), "label.basket"));
        model.addAttribute("registration",localeMessage.getMessage(Locale.getDefault(), "label.registration"));

        model.addAttribute("login", localeMessage.getMessage(Locale.getDefault(), "label.login"));
        model.addAttribute("password", localeMessage.getMessage(Locale.getDefault(), "label.password"));
        if (iUserProfileService.getCurrentUser() != null) {
            model.addAttribute("infoUser", iUserProfileService.getCurrentUser());
        }
        return "main";
    }

}
