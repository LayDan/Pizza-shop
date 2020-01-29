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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileService implements UserDetailsService, IUserProfileService {

    private UserProfileRepository userProfileRepository;

    private PasswordEncoder passwordEncoder;

    private MailSender mailSender;

    public UserProfileService(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserProfile> userProfile = userProfileRepository.findByUsername(username);
        return userProfile.orElse(null);
    }

    @Transactional
    @Override
    public boolean findByUsername(String username) {
        return userProfileRepository.findByUsername(username).isPresent();
    }

    @Transactional
    @Override
    public UserProfile addUser(UserProfile userProfile) {
        Optional<UserProfile> profile = userProfileRepository.findByUsername(userProfile.getUsername());
        Optional<UserProfile> byMail = userProfileRepository.findByMail(userProfile.getMail());
        if (!profile.isPresent() && !byMail.isPresent()) {
            UserProfile newUser = UserProfile.builder()
                    .firstName(userProfile.getFirstName())
                    .lastName(userProfile.getLastName())
                    .active(true)
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

    @Override
    public boolean activateUser(String code) {
        Optional<UserProfile> user = userProfileRepository.findByActivationCode(code);

        if (user.isPresent()) {
            user.get().setActivationCode(null);
            userProfileRepository.save(user.get());
            return true;
        } else {
            return false;
        }
    }
}
