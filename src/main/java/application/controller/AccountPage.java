package application.controller;

import application.domain.LocaleMessage;
import application.service.IProductService;
import application.service.IUserProfileService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@Api(value = "Account resources")
public class AccountPage {


    public AccountPage(IUserProfileService iUserProfileService, IProductService iProductService) {
        this.iUserProfileService = iUserProfileService;
        this.iProductService = iProductService;
    }

    private IUserProfileService iUserProfileService;
    private IProductService iProductService;

    @GetMapping("/account")
    public String account(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        if (iUserProfileService.getCurrentUser() != null) {
            model.addAttribute("infoUser", iUserProfileService.getCurrentUser());
            return "account";
        } else {
            return "login";
        }
    }

    @GetMapping("/basket")
    public String getBasket(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute("productFromBasket", iUserProfileService.getCurrentUser().getBasket());
        model.addAttribute("money", iUserProfileService.money());
        return "basket";
    }

    @PostMapping("/basket")
    public String BuyBasket(Model model, @RequestParam(required = false) String status, @RequestParam(required = false) Double delivery, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        if (status != null) {
            model.addAttribute("success", iUserProfileService.buyProduct(iUserProfileService.getCurrentUser(), delivery));
            model.addAttribute("productFromBasket", iUserProfileService.getCurrentUser().getBasket());
            model.addAttribute("money", iUserProfileService.money());
        } else {
            model.addAttribute("productFromBasket", iUserProfileService.getCurrentUser().getBasket());
            if (delivery != null) {
                model.addAttribute("success", iUserProfileService.money() + delivery);
            } else {
                model.addAttribute("money", iUserProfileService.money());
            }
        }
        return "basket";
    }

}
