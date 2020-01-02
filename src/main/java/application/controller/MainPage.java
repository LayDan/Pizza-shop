package application.controller;

import application.domain.UserProfile;
import application.service.IUserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class MainPage {

    public MainPage(IUserProfileService iUserProfileService) {
        this.iUserProfileService = iUserProfileService;
    }


    private IUserProfileService iUserProfileService;


    @GetMapping("/login")
    public String startLogin(Map<String, Object> model) {
        return "login";
    }


    @GetMapping("/registration")
    public String startRegistration(Map<String, Object> model) {
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
    public String mainWindow(Model model) {
        if (iUserProfileService.getCurrentUser() != null) {
            model.addAttribute("infoUser", iUserProfileService.getCurrentUser());
        }
        return "main";
    }
    @GetMapping("/main")
    public String mainWindow2(Model model) {
        if (iUserProfileService.getCurrentUser() != null) {
            model.addAttribute("infoUser", iUserProfileService.getCurrentUser());
        }
        return "main";
    }

}
