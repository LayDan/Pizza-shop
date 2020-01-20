package application.controller;

import application.service.IProductService;
import application.service.IUserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Api(value = "Account resources")
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
    public String BuyBasket(Model model, @RequestParam(required = false) String status, @RequestParam(required = false) Double delivery) {
        if (status != null) {
            model.addAttribute("success", iProductService.buyProduct(iUserProfileService.getCurrentUser(), delivery));
            model.addAttribute("basket", iUserProfileService.getCurrentUser().getBasket());
            model.addAttribute("money", iUserProfileService.money());
        } else {
            model.addAttribute("basket", iUserProfileService.getCurrentUser().getBasket());
            if (delivery != null) {
                model.addAttribute("success", iUserProfileService.money() + delivery);
            } else {
                model.addAttribute("money", iUserProfileService.money());
            }
        }
        return "basket";
    }

}
