package application.service.impl;

import application.domain.Basket;
import application.domain.Product;
import application.domain.UserProfile;
import application.repository.BasketRepository;
import application.repository.ProductRepository;
import application.repository.UserProfileRepository;
import application.service.IBasketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService implements IBasketService {
    private UserProfileRepository userProfileRepository;
    private ProductRepository productRepository;
    private BasketRepository basketRepository;

    public BasketService(UserProfileRepository userProfileRepository, ProductRepository productRepository, BasketRepository basketRepository) {
        this.userProfileRepository = userProfileRepository;
        this.productRepository = productRepository;
        this.basketRepository = basketRepository;
    }

    @Override
    public Basket addProductToBasket(Product product, UserProfile userProfile, String key) {
        Optional<Product> productById = productRepository.findById(product.getId());
        Basket basket = null;
        Optional<UserProfile> profile = userProfileRepository.findById(userProfile.getId());
        if (productById.isPresent() && profile.isPresent()) {
            List<Basket> allByUserProfile = basketRepository.findAllByUserProfile(profile.get());
            if (!allByUserProfile.isEmpty()) {
                for (Basket a : allByUserProfile) {
                    if (a.getProduct() == product && a.getKey().equals(key)) {
                        a.setQuantity(a.getQuantity() + 1);
                    } else {
                        basket = Basket.builder()
                                .quantity(1)
                                .product(product)
                                .userProfile(userProfile)
                                .key(key)
                                .build();
                        basketRepository.saveAndFlush(basket);
                    }
                }
            } else {
                basket = Basket.builder()
                        .quantity(1)
                        .product(product)
                        .userProfile(userProfile)
                        .key(key)
                        .build();
                basketRepository.saveAndFlush(basket);
            }
        }
        return basket;
    }
}
