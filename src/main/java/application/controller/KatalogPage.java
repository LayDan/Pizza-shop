package application.controller;

import application.domain.Product;
import application.domain.TypeProduct;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.service.IProductService;
import application.service.IUserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/katalog")
public class KatalogPage {


    public KatalogPage(IUserProfileService iUserProfileService, ProductRepository productRepository, IProductService iproductService) {
        this.iUserProfileService = iUserProfileService;
        this.productRepository = productRepository;
        this.iproductService = iproductService;
    }

    private IUserProfileService iUserProfileService;
    private ProductRepository productRepository;
    private IProductService iproductService;

    private String katalog = "katalog";

    @GetMapping()
    public String getKatalog(@RequestParam(name = "type", required = false, defaultValue = "") String type, Model model) {
        model.addAttribute("catalog", TypeProduct.values());
        model.addAttribute("AllProduct", iproductService.sortType(type));
        return katalog;
    }

    @PostMapping()
    public String addToCart(@RequestParam(name = "type", required = false, defaultValue = "") String type, Product product, String size, Model model) {
        model.addAttribute("catalog", TypeProduct.values());
        model.addAttribute("AllProduct", iproductService.sortType(type));
        return katalog;
    }

    @GetMapping(value = "{product}")
    public String getProduct(@PathVariable Product product,Model model) {
        model.addAttribute("product",product);
        model.addAttribute("priceFromSize",product.getPriceFromSize());
        return "productPage";
    }
}
