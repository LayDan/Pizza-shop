package application.controller;

import application.domain.Product;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class KatalogPage {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;


    @GetMapping("/katalog")
    public String getKatalog(Model model, UserProfile userProfile) {
        model.addAttribute("AllProduct", productRepository.findAll());
        return "katalog";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        return "/addProduct";
    }

    @PostMapping("/addProduct")
    public String getAddProduct(Model model, Product product) {
        productService.addProduct(product);
        model.addAttribute("AllProduct", productRepository.findAll());
        return "katalog";
    }
}
