package application.service;

import application.domain.Basket;
import application.domain.UserProfile;

import java.util.List;
import java.util.Map;

public interface IUserProfileService {
    boolean findByUsername(String username);

    UserProfile addUser(UserProfile userProfile);

    UserProfile getCurrentUser();

    Double money();

    Double buyProduct(UserProfile userProfile, Double delivery);

    boolean activateUser(String code);

    List<Basket> getBasketToPage();

    UserProfile editUser(UserProfile userProfile, String username, Map<String, String> form);
}
