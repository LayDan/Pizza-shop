package application.service.impl;

import application.domain.UserProfile;
import application.repository.BasketRepository;
import application.repository.UserProfileRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProfileServiceTest {


    @MockBean
    private BasketRepository basketRepository;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserProfileService userProfileService;

    @Test
    public void addUser() {
        UserProfile user = new UserProfile();
        user.setUsername("1");
        user.setPassword("1");
        user.setLastName("1");
        user.setFirstName("1");
        user.setMail("1");
        UserProfile createUser = userProfileService.addUser(user);
        Assert.assertNotNull(createUser);
        Assert.assertNotNull(createUser.getActivationCode());
    }

    @Test
    public void buyProduct() {
        UserProfile userProfile = new UserProfile();
        Double aDouble = userProfileService.buyProduct(userProfile, 0.0);
        Assert.assertNotNull(aDouble);
    }

}