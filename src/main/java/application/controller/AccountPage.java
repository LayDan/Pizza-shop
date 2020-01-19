package application.controller;

import application.service.IProductService;
import application.service.IUserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class AccountPage {

    public AccountPage(IUserProfileService iUserProfileService, IProductService iProductService) {
        this.iProductService = iProductService;
        this.iUserProfileService = iUserProfileService;
    }


    private IUserProfileService iUserProfileService;
    private IProductService iProductService;

    @GetMapping("/account")
    public String account(Model model) {
        if (iUserProfileService.getCurrentUser() != null) {
            model.addAttribute("infoUser", iUserProfileService.getCurrentUser());
            return "account";
        } else {
            return "login";
        }
    }

    @GetMapping("/basket")
    public String getBasket(Model model) {
        model.addAttribute("basket", iUserProfileService.getCurrentUser().getBasket());
        model.addAttribute("money", iUserProfileService.money());
        return "basket";
    }

    @PostMapping("/basket")
    public String BuyBasket(Model model) {
        model.addAttribute("basket", iUserProfileService.getCurrentUser().getBasket());
        model.addAttribute("success", iProductService.buyProduct(iUserProfileService.getCurrentUser()));
        model.addAttribute("money", iUserProfileService.money());
        return "basket";
    }

}
