package application.service;

import application.domain.Product;
import application.domain.UserProfile;

import java.util.Map;

public interface IUserProfileService {
    boolean findByUsername(String username);

    UserProfile addUser(UserProfile userProfile);

    UserProfile getCurrentUser();

    Double money();

    Double buyProduct(UserProfile userProfile, Double delivery);

    boolean activateUser(String code);

    Map<String, Product> getBasketToPage();
}
