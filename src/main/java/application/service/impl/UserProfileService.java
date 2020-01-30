package application.service.impl;

import application.domain.Product;
import application.domain.Role;
import application.domain.UserProfile;
import application.repository.UserProfileRepository;
import application.service.IUserProfileService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
public class UserProfileService implements UserDetailsService, IUserProfileService {

    private UserProfileRepository userProfileRepository;

    private PasswordEncoder passwordEncoder;

    private MailSender mailSender;

    public UserProfileService(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserProfile> userProfile = userProfileRepository.findByUsername(username);
        return userProfile.orElse(null);
    }

    @Override
    public boolean findByUsername(String username) {
        return userProfileRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserProfile addUser(UserProfile userProfile) {
        Optional<UserProfile> profile = userProfileRepository.findByUsername(userProfile.getUsername());
        Optional<UserProfile> byMail = userProfileRepository.findByMail(userProfile.getMail());
        if (!profile.isPresent() && !byMail.isPresent()) {
            UserProfile newUser = UserProfile.builder()
                    .firstName(userProfile.getFirstName())
                    .lastName(userProfile.getLastName())
                    .active(false)
                    .bonus(0)
                    .username(userProfile.getUsername())
                    .password(passwordEncoder.encode(userProfile.getPassword()))
                    .roles(Collections.singleton(Role.USER))
                    .activationCode(UUID.randomUUID().toString())
                    .basket(new LinkedHashMap<>())
                    .mail(userProfile.getMail())
                    .build();
            userProfileRepository.save(newUser);

            if (!StringUtils.isEmpty(newUser.getMail())) {
                String message = String.format(
                        "Hello %s\n" +
                                "Please, visit next link: http://localhost:8080/activate/%s",
                        newUser.getUsername(),
                        newUser.getActivationCode()
                );

                mailSender.send(newUser.getMail(), "Activation code", message);
                return newUser;
            }
        }
        return null;
    }

    @CacheEvict(value = {"user"}, allEntries = true)
    @Override
    public UserProfile getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile userProfile = (UserProfile) principal;
        Optional<UserProfile> byId = userProfileRepository.findById(userProfile.getId());
        return byId.orElse(null);
    }

    @Override
    public Double money() {
        Double money = 0.0;
        Map<String, Product> basket = getCurrentUser().getBasket();
        for (Map.Entry<String, Product> s : basket.entrySet()) {
            money = money + basket.get(s.getKey()).getPriceFromSize().get(s.getKey());
        }
        return money;
    }


    @Override
    public Double buyProduct(UserProfile userProfile, Double delivery) {
        Optional<UserProfile> cheekUser = userProfileRepository.findByUsername(userProfile.getUsername());
        if (cheekUser.isPresent() && !cheekUser.get().getBasket().isEmpty()) {
            userProfile.getBasket().clear();
            userProfileRepository.save(userProfile);
        }
        return 0.0;
    }

    @Override
    public boolean activateUser(String code) {
        Optional<UserProfile> user = userProfileRepository.findByActivationCode(code);

        if (user.isPresent()) {
            user.get().setActivationCode(null);
            user.get().setActive(true);
            userProfileRepository.save(user.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<String, Product> getBasketToPage() {
        Map<String, Product> array = new LinkedHashMap<>();
        Product product;
        String key;
        Double delivery;
        for (Map.Entry<String, Product> a : getCurrentUser().getBasket().entrySet()) {
            product = a.getValue();
            key = a.getKey();
            if (a.getValue().getStock() != null && a.getValue().getStock() != 0.0) {
                delivery = product.getPriceFromSize().get(key) * (product.getStock() / 100);
                array.put(String.valueOf(delivery), product);
            } else {
                array.put(String.valueOf(product.getPriceFromSize().get(key)), product);
            }
        }
        return array;
    }
}
