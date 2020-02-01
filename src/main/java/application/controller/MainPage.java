package application.controller;

import application.domain.LocaleMessage;
import application.domain.Product;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.repository.TypeProductRepository;
import application.service.IUserProfileService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Locale;

@Controller
public class MainPage {

    public MainPage(TypeProductRepository typeProductRepository, IUserProfileService iUserProfileService, ProductRepository productRepository) {
        this.typeProductRepository = typeProductRepository;
        this.iUserProfileService = iUserProfileService;
        this.productRepository = productRepository;
    }

    private TypeProductRepository typeProductRepository;
    private IUserProfileService iUserProfileService;
    private ProductRepository productRepository;


    @GetMapping("/login")
    public String startLogin(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, LocaleContextHolder.getLocale()));
        return "login";
    }


    @GetMapping("/registration")
    public String startRegistration(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute(localeMessage.registration(model, locale));
        return "registration";
    }

    @PostMapping("/registration")
    public String endRegistration(Model model, UserProfile user, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute(localeMessage.registration(model, locale));
        if (iUserProfileService.findByUsername(user.getUsername())) {
            model.addAttribute("info", "User exist");
            return "registration";
        } else {
            iUserProfileService.addUser(user);
        }
        return "redirect:/login";
    }

    @GetMapping(value = {"/", "/main"})
    public String gretting(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        ArrayList<Product> allByPromotionalItem = productRepository.findAllByPromotionalItem();
        if (!allByPromotionalItem.isEmpty()) {
            model.addAttribute("PromotionProducts", allByPromotionalItem);
        }
        model.addAttribute("types", typeProductRepository.findAllType());
        return "main";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        boolean isActivated = iUserProfileService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }

}
