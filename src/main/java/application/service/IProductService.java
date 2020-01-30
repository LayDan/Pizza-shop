package application.service;

import application.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface IProductService {
    Product addProduct(Product product, MultipartFile file) throws IOException;

    Product editProduct(Product product, Double stock, String name);

    void deleteProduct(Long id);

    void addToCart(Long id, Product product, String price);

    List<Product> sortType(String type);

    ArrayList<Product> search(String name);



}
