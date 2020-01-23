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
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Controller
public class ProductFunction {



    private ProductRepository productRepository;
    private IProductService iproductService;

    public ProductFunction(ProductRepository productRepository, IProductService iproductService) {
        this.productRepository = productRepository;
        this.iproductService = iproductService;
    }

    private ArrayList<Integer> arr = new ArrayList<>();

    @GetMapping("/addProduct")
    public String addProduct(Model model, @RequestParam(value = "size", defaultValue = "1") String size) {

        if (arr.size() != Integer.parseInt(size)) {
            arr.clear();
            for (int i = 1; i < Integer.parseInt(size) + 1; i++) {
                arr.add(i);
            }
        }
        model.addAttribute("array", arr);
        model.addAttribute("types", TypeProduct.values());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String getAddProduct(Model model, Product product,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam(name = "sizeProduct") String[] sizeProduct,
                                @RequestParam(name = "priceForProduct") Double[] price) throws IOException {
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        if (product != null && sizeProduct.length == price.length) {
            for (int i = 0; i < sizeProduct.length; i++) {
                if (sizeProduct[i] == null || price[i] == null) {
                    break;
                } else {
                    map.put(sizeProduct[i], price[i]);
                }
            }
            product.setPriceFromSize(map);
            iproductService.addProduct(product, file);
            model.addAttribute("AllProduct", productRepository.findAll());
            model.addAttribute("types", TypeProduct.values());
            arr.clear();
            return "katalog";
        } else {
            arr.clear();
            return "addProduct";
        }
    }

    @GetMapping("/editProduct")
    public String editProduct(Model model, Product product) {
        model.addAttribute(product);
        return "editProduct";
    }

    @PostMapping("/editProduct")
    public String postEditProduct(Model model, @RequestParam(name = "productId") Product product, Double stock, String name) {
        iproductService.editProduct(product, stock, name);
        model.addAttribute("AllProduct", productRepository.findAll());
        model.addAttribute("types", TypeProduct.values());
        return "katalog";
    }
    @GetMapping("/delete")
    public String deleteProduct(Model model, @RequestParam(name = "productId") Product product) {
        iproductService.deleteProduct(product.getId());
        model.addAttribute("AllProduct", productRepository.findAll());
        model.addAttribute("types", TypeProduct.values());
        return "katalog";
    }

}
