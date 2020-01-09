package application.controller;

import application.domain.Product;
import application.domain.TypeProduct;
import application.repository.ProductRepository;
import application.service.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProductFunction {

    private ProductRepository productRepository;
    private IProductService iproductService;

    public ProductFunction(ProductRepository productRepository, IProductService iproductService) {
        this.productRepository = productRepository;
        this.iproductService = iproductService;
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("types", TypeProduct.values());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String getAddProduct(Model model, Product product,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam String priceSize) throws IOException
    {
        if (product != null) {
            iproductService.addProduct(product, file, priceSize);
            model.addAttribute("catalog", TypeProduct.values());
            model.addAttribute("AllProduct", productRepository.findAll());
            return "katalog";
        } else {
            return "addProduct";
        }
    }
}
