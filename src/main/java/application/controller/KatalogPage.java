package application.controller;

import application.domain.LocaleMessage;
import application.domain.Product;
import application.repository.ProductRepository;
import application.repository.TypeProductRepository;
import application.service.IProductService;
import application.service.IUserProfileService;
import application.service.impl.BasketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/catalog")
public class KatalogPage {

    public KatalogPage(BasketService basketService, TypeProductRepository typeProductRepository, IUserProfileService iUserProfileService, ProductRepository productRepository, IProductService iproductService) {
        this.basketService = basketService;
        this.typeProductRepository = typeProductRepository;
        this.iUserProfileService = iUserProfileService;
        this.productRepository = productRepository;
        this.iproductService = iproductService;
    }

    private BasketService basketService;
    private TypeProductRepository typeProductRepository;
    private IUserProfileService iUserProfileService;
    private ProductRepository productRepository;
    private IProductService iproductService;

    private String size;
    private String catalog = "catalog";
    private String types = "types";
    private String productPage = "productPage";
    private String allProduct = "AllProduct";

    @GetMapping()
    public String getKatalog(@RequestParam(name = "type", required = false, defaultValue = "") String type, Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute(types, typeProductRepository.findAllType());
        model.addAttribute(allProduct, iproductService.sortType(type));
        return catalog;
    }

    @PostMapping()
    public String catalogPage(@RequestParam(name = "search", required = false) String search,
                              @RequestParam(name = "type", required = false, defaultValue = "") String type, Model model, Locale locale) {

        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        model.addAttribute(types, typeProductRepository.findAllType());
        if (search != null) {
            model.addAttribute(allProduct, iproductService.search(search));
        } else {
            model.addAttribute(allProduct, iproductService.sortType(type));
        }
        return catalog;
    }

    @GetMapping(value = "{product}")
    public String getProduct(@Valid @PathVariable Product product, Model model, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));

        model.addAttribute("product", product);
        model.addAttribute("priceFromSize", product.getPriceFromSize());
        return productPage;
    }

    @PostMapping(value = "{product}")
    public String getProductSize(Model model, String size, Product product, String priceToBasket, Locale locale) {
        LocaleMessage localeMessage = new LocaleMessage();
        model.addAttribute(localeMessage.navBar(model, locale));
        if (size != null) {
            this.size = size;
            Double selectPrice = product.getPriceFromSize().get(size);
            if (product.getStock() == null || product.getStock() == 0.0) {
                model.addAttribute("price", selectPrice);
            } else {
                double newMoney = selectPrice * product.getStock() / 100;
                model.addAttribute("price", selectPrice);
                model.addAttribute("newPrice", selectPrice - newMoney);
            }
            model.addAttribute("product", product);
            model.addAttribute("priceFromSize", product.getPriceFromSize());
            return productPage;
        }
        if (priceToBasket != null) {
            size = this.size;
            iproductService.addToCart(basketService.addProductToBasket(product, iUserProfileService.getCurrentUser(), size));
            model.addAttribute("productFromBasket", iUserProfileService.getCurrentUser().getBasket());
            model.addAttribute(types, typeProductRepository.findAllType());
            model.addAttribute(allProduct, productRepository.findAll());
            return catalog;
        }
        return productPage;
    }
}
