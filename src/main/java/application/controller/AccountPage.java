package application.controller;

import application.domain.LocaleMessage;
import application.domain.Role;
import application.domain.UserProfile;
import application.repository.UserProfileRepository;
import application.service.ITypeProductService;
import application.service.IUserProfileService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Controller
@Api(value = "Account resources")
public class AccountPage {

    public AccountPage(UserProfileRepository userProfileRepository, ITypeProductService iTypeProductService, IUserProfileService iUserProfileService) {
        this.userProfileRepository = userProfileRepository;
        this.iTypeProductService = iTypeProductService;
        this.iUserProfileService = iUserProfileService;
    }

    private UserProfileRepository userProfileRepository;
    private ITypeProductService iTypeProductService;
    private IUserProfileService iUserProfileService;


    private String money = "money";
    private String productFromBasket = "productFromBasket";

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
        model.addAttribute(productFromBasket, iUserProfileService.getBasketToPage());
        model.addAttribute(money, iUserProfileService.money());
        return "basket";
    }

    @PostMapping("/basket")
    public String buyBasket(Model model, @RequestParam(required = false) String status, @RequestParam(required = false) Double delivery, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        if (status != null) {
            model.addAttribute("success", iUserProfileService.buyProduct(iUserProfileService.getCurrentUser(), delivery));
            model.addAttribute(productFromBasket, iUserProfileService.getCurrentUser().getBasket());
            model.addAttribute(money, iUserProfileService.money());
        } else {
            model.addAttribute(productFromBasket, iUserProfileService.getCurrentUser().getBasket());
            if (delivery != null) {
                model.addAttribute("success", iUserProfileService.money() + delivery);
            } else {
                model.addAttribute(money, iUserProfileService.money());
            }
        }
        return "basket";
    }

    @GetMapping("/administrationPanel")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdministrationPanel(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        return "administrationPanel";
    }

    //////////////////////////////////////////////////////////////////////
    @GetMapping("/addTypeProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addTypeProduct(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        return "addTypeProduct";
    }

    @PostMapping("/addTypeProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String postAddTypeProduct(Model model, Locale locale, @RequestParam String type) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        iTypeProductService.addType(type);
        return "addTypeProduct";
    }

    ///////////////////////////////////////////////////////////////////////
    @GetMapping("/editUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editUsers(Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute("users", userProfileRepository.findAll());
        return "editUsers";
    }

    @GetMapping("/editUsers/{userProfile}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editUser(Model model, Locale locale, @PathVariable UserProfile userProfile) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", userProfile);
        return "editSomeUser";
    }

    @PostMapping("/editUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String posteditUsers(Model model, Locale locale,
                                @RequestParam String username,
                                @RequestParam Map<String, String> formRole,
                                @RequestParam(name = "userId") long id) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        Optional<UserProfile> byId = userProfileRepository.findById(id);
        byId.ifPresent(userProfile -> iUserProfileService.editUser(userProfile, username, formRole));
        return "redirect:/editUsers";
    }
}
