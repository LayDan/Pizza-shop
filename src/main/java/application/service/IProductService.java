package application.service;

import application.domain.Basket;
import application.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IProductService {
    Product addProduct(Product product, MultipartFile file) throws IOException;

    Product editProduct(Product product, Double stock, String name);

    void deleteProduct(Long id);

    void addToCart(Basket basket);

    List<Product> sortType(String type);

    ArrayList<Product> search(String name);


}
