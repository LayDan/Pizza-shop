package application.controller;

import application.service.IUserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountPage {


    public AccountPage(IUserProfileService iUserProfileService) {

        this.iUserProfileService = iUserProfileService;
    }


    private IUserProfileService iUserProfileService;

    @GetMapping("/account")
    public String account(Model model) {
        if (iUserProfileService.getCurrentUser() != null) {
            model.addAttribute("infoUser", iUserProfileService.getCurrentUser());
            return "account";
        } else {
            return "login";
        }
    }
}
