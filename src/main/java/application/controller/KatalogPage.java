package application.controller;

import application.domain.Product;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.service.IProductService;
import application.service.IUserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
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

    @GetMapping("/katalog")
    public String getKatalog(Model model, UserProfile userProfile) {
        model.addAttribute("AllProduct", productRepository.findAll());
        return katalog;
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        return "/addProduct";
    }

    @PostMapping("/addProduct")
    public String getAddProduct(Model model, Product product) {
        iproductService.addProduct(product);
        model.addAttribute("AllProduct", productRepository.findAll());
        return katalog;
    }

    @PostMapping("/katalog")
    public String addToCart(Product product, String size, Model model) {
        model.addAttribute("infoAboutBuy",iproductService.addToCart(iUserProfileService.getCurrentUser(),product,size));
        return katalog;
    }
}
