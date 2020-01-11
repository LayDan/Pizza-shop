package application.service;

import application.domain.Product;
import application.domain.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Product addProduct(Product product, MultipartFile file) throws IOException;

    Product editProduct(Product product, Product newProduct);

    void deleteProduct(Long id);

    Product addToCart(UserProfile userProfile, Product product, String string);

    Double buyProduct(UserProfile userProfile);

    List<Product> sortType(String type);

}
