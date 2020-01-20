package application.service.impl;

import application.domain.Product;
import application.domain.Role;
import application.domain.UserProfile;
import application.repository.UserProfileRepository;
import application.service.IUserProfileService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserProfileService implements UserDetailsService, IUserProfileService {

    private UserProfileRepository userProfileRepository;

    private PasswordEncoder passwordEncoder;

    public UserProfileService(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userProfileRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public boolean findByUsername(String username) {
        return userProfileRepository.findByUsername(username) != null;
    }

    @Transactional
    @Override
    public UserProfile addUser(UserProfile userProfile) {
        UserProfile newUser = UserProfile.builder()
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .active(true)
                .bonus(0)
                .username(userProfile.getUsername())
                .password(passwordEncoder.encode(userProfile.getPassword()))
                .roles(Collections.singleton(Role.USER))
                .basket(new ArrayList<>())
                .build();
        userProfileRepository.saveAndFlush(newUser);
        return newUser;
    }

    @Override
    @Transactional
    public UserProfile getCurrentUser() {
        return (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    @Override
    public Double money() {
        Double money = 0.0;
        for (Product p : getCurrentUser().getBasket()) {
            money = money + p.getPrice();
        }
        return money;
    }
}
