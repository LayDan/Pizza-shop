package application.service.impl;

import application.domain.Basket;
import application.domain.Product;
import application.domain.Role;
import application.domain.UserProfile;
import application.repository.BasketRepository;
import application.repository.UserProfileRepository;
import application.service.IUserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserProfileService implements UserDetailsService, IUserProfileService {

    private UserProfileRepository userProfileRepository;

    private PasswordEncoder passwordEncoder;

    private MailSender mailSender;

    private BasketRepository basketRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, MailSender mailSender, BasketRepository basketRepository) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.basketRepository = basketRepository;
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
                    .basket(new ArrayList<>())
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
        UserProfile userProfile;
        Optional<Object> principal = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (principal.isPresent()) {
            userProfile = (UserProfile) principal.get();
            Optional<UserProfile> byId = userProfileRepository.findById(userProfile.getId());
            return byId.orElse(null);
        }
        return null;
    }

    @CacheEvict(value = {"user", "basket"}, allEntries = true)
    @Override
    public Double money() {
        double money = 0.0;
        double productMoney = 0.0;
        List<Basket> basket = getCurrentUser().getBasket();

        for (Basket b : basket) {
            Product product = b.getProduct();
            if (product.getStock() != null && product.getStock() != 0.0) {
                Double delivery = product.getPriceFromSize().get(b.getKey()) * (product.getStock() / 100);
                productMoney = product.getPriceFromSize().get(b.getKey()) * b.getQuantity();
                delivery = delivery * b.getQuantity();
                money = money + (productMoney - delivery);
            } else {
                productMoney = product.getPriceFromSize().get(b.getKey()) * b.getQuantity();
                money = money + productMoney;
            }
        }
        return money;
    }


    @Override
    public Double buyProduct(UserProfile userProfile, Double delivery) {
        Optional<UserProfile> cheekUser = userProfileRepository.findByUsername(userProfile.getUsername());
        if (cheekUser.isPresent() && !cheekUser.get().getBasket().isEmpty()) {
            basketRepository.deleteAll(userProfile.getBasket());
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
            log.info("Create user " + user.get().getId());
            return true;
        } else {
            return false;
        }
    }

    @Cacheable("user")
    @Override
    public List<Basket> getBasketToPage() {
        List<Basket> array = new ArrayList<>();
        Product product;
        String key;
        Double delivery;
        double money;
        Basket basket;
        for (Basket a : getCurrentUser().getBasket()) {
            product = a.getProduct();
            key = a.getKey();
            if (product.getStock() != null && product.getStock() != 0.0) {
                delivery = product.getPriceFromSize().get(key) * (product.getStock() / 100);
                money = product.getPriceFromSize().get(key) - delivery;
                basket = Basket.builder()
                        .id(a.getId())
                        .userProfile(a.getUserProfile())
                        .key(String.valueOf(money))
                        .product(product)
                        .quantity(a.getQuantity())
                        .build();
                array.add(basket);
            } else {
                basket = Basket.builder()
                        .id(a.getId())
                        .userProfile(a.getUserProfile())
                        .key(String.valueOf(product.getPriceFromSize().get(key)))
                        .product(product)
                        .quantity(a.getQuantity())
                        .build();
                array.add(basket);
            }
        }
        return array;
    }


    @CacheEvict(value = {"user", "users"}, allEntries = true)
    @Override
    public UserProfile editUser(UserProfile userProfile, String username, Map<String, String> form) {
        Optional<UserProfile> userProfile1 = Optional.ofNullable(userProfile);
        if (userProfile1.isPresent() && username != null && !username.equals("")) {
            userProfile1.get().setUsername(username);
        }
        if (userProfile1.isPresent() && !form.isEmpty()) {

            Set<String> roles = Arrays.stream(Role.values())
                    .map(Role::name)
                    .collect(Collectors.toSet());

            userProfile1.get().getRoles().clear();

            for (String key : form.keySet()) {
                if (roles.contains(key)) {
                    userProfile1.get().getRoles().add(Role.valueOf(key));
                }
            }
            log.info("Update user " + userProfile1.get().getId());
            return userProfileRepository.save(userProfile1.get());
        }
        return userProfile;
    }
}
