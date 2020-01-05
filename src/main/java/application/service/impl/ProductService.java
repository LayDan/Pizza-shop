package application.service.impl;

import application.domain.Product;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.repository.UserProfileRepository;
import application.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    private ProductRepository productRepository;

    private UserProfileRepository userProfileRepository;

    public ProductService(ProductRepository productRepository, UserProfileRepository userProfileRepository) {
        this.productRepository = productRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Product addProduct(Product product) {
        if (product != null) {
            Product newProduct = Product.builder()
                    .code(product.getCode())
                    .name(product.getName())
                    .description(product.getDescription())
                    .type(product.getType())
                    .priceFromSize(product.getPriceFromSize())
                    .price(0.00)
                    .build();
            productRepository.saveAndFlush(newProduct);
            return newProduct;
        } else {
            return null;
        }
    }

    @Override
    public Product editProduct(Product product, Product newProduct) {
        Product cheekProduct = productRepository.findProductById(product.getId());
        if (cheekProduct != null && cheekProduct != newProduct) {
            cheekProduct.setCode(newProduct.getCode());
            cheekProduct.setName(newProduct.getName());
            cheekProduct.setDescription(newProduct.getDescription());
            cheekProduct.setType(newProduct.getType());
            cheekProduct.setPriceFromSize(newProduct.getPriceFromSize());
            productRepository.save(cheekProduct);
            return cheekProduct;
        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findProductById(id);
        if (product != null) {
            productRepository.delete(productRepository.findProductById(id));
        }
    }

    @Override
    public Product addToCart(UserProfile userProfile, Product product, String string) {
        UserProfile cheekUser = userProfileRepository.findByUsername(userProfile.getUsername());
        Product cheekProduct = productRepository.findProductById(product.getId());
        if (cheekUser != null && cheekProduct != null) {
            cheekProduct.setPrice(cheekProduct.getPriceFromSize().get(string));
            cheekUser.getBasket().add(cheekProduct.getId());
            userProfileRepository.save(cheekUser);
            return cheekProduct;
        }
        return null;
    }

    @Override
    public Double buyProduct(UserProfile userProfile) {
        UserProfile cheekUser = userProfileRepository.findByUsername(userProfile.getUsername());
        if (cheekUser != null && !cheekUser.getBasket().isEmpty()) {
            Double money = 0.00;
            for (Long a : cheekUser.getBasket()) {
                money = money + productRepository.findProductById(a).getPrice();
            }
            return money;
        }
        return null;
    }


}
