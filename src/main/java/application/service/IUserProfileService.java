package application.service;

import application.domain.UserProfile;

public interface IUserProfileService {
    boolean findByUsername(String username);

    UserProfile addUser(UserProfile userProfile);

    UserProfile getCurrentUser();

    Double money();

    Double buyProduct(UserProfile userProfile, Double delivery);

    boolean activateUser(String code);
}
