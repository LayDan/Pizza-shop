package application.controller;

import application.domain.LocaleMessage;
import application.domain.Product;
import application.repository.ProductRepository;
import application.repository.TypeProductRepository;
import application.service.IProductService;
import application.service.IUserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequestMapping("/catalog")
public class KatalogPage {

    public KatalogPage(TypeProductRepository typeProductRepository, IUserProfileService iUserProfileService, ProductRepository productRepository, IProductService iproductService) {
        this.typeProductRepository = typeProductRepository;
        this.iUserProfileService = iUserProfileService;
        this.productRepository = productRepository;
        this.iproductService = iproductService;
    }

    private TypeProductRepository typeProductRepository;
    private IUserProfileService iUserProfileService;
    private ProductRepository productRepository;
    private IProductService iproductService;

    private String size;
    private String catalog = "catalog";

    @GetMapping()
    public String getKatalog(@RequestParam(name = "type", required = false, defaultValue = "") String type, Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));

        model.addAttribute("types", typeProductRepository.findAllType());
        model.addAttribute("AllProduct", iproductService.sortType(type));
        return catalog;
    }

    @PostMapping()
    public String catalogPage(@RequestParam(name = "search", required = false) String search,
                              @RequestParam(name = "type", required = false, defaultValue = "") String type, Product product, Model model, Locale locale) {

        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute("types", typeProductRepository.findAllType());
        if (search != null) {
            model.addAttribute("AllProduct", iproductService.search(search));
        } else {
            model.addAttribute("AllProduct", iproductService.sortType(type));
        }
        return catalog;
    }

    @GetMapping(value = "{product}")
    public String getProduct(@PathVariable Product product, Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));

        model.addAttribute("product", product);
        model.addAttribute("priceFromSize", product.getPriceFromSize());
        return "productPage";
    }

    @PostMapping(value = "{product}")
    public String getProductSize(Model model, String size, Product product, Double priceToBasket, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        if (size != null) {
            this.size = size;
            Double selectPrice = product.getPriceFromSize().get(size);
            model.addAttribute("price", selectPrice);
            model.addAttribute("product", product);
            model.addAttribute("priceFromSize", product.getPriceFromSize());
            return "productPage";
        }
        if (priceToBasket != null) {
            size = this.size;
            iproductService.addToCart(iUserProfileService.getCurrentUser().getId(), product, size);
            model.addAttribute("productFromBasket", iUserProfileService.getCurrentUser().getBasket());
            model.addAttribute("types", typeProductRepository.findAllType());
            model.addAttribute("AllProduct", productRepository.findAll());
            return catalog;
        }
        return "productPage";
    }
}
