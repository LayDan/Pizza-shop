package application.service;

import application.domain.Basket;
import application.domain.Product;
import application.domain.UserProfile;

public interface IBasketService {
    Basket addProductToBasket(Product product, UserProfile userProfile, String key);
}
