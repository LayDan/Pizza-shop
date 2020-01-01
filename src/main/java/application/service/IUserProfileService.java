package application.service;

import application.domain.UserProfile;

public interface IUserProfileService {
    boolean findByUsername(String username);
    UserProfile addUser(UserProfile userProfile);
    UserProfile getCurrentUser();
}
