package application.service;

import application.domain.Product;
import application.domain.UserProfile;

public interface IProductService {
    Product addProduct(Product product);

    Product editProduct(Product product, Product newProduct);

    void deleteProduct(Long id);

    void addToCart(UserProfile userProfile, Product product, String string);

    Double buyProduct(UserProfile userProfile);

}
