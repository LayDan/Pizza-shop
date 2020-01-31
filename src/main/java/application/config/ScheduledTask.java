package application.config;

import application.domain.UserProfile;
import application.repository.UserProfileRepository;
import application.service.impl.MailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    public ScheduledTask(MailSender mailSender, UserProfileRepository userProfileRepository) {
        this.mailSender = mailSender;
        this.userProfileRepository = userProfileRepository;
    }

    private MailSender mailSender;
    private UserProfileRepository userProfileRepository;

    @Scheduled(fixedRate = 86400000)
    public void reportCurrentTime() {
        for (UserProfile a : userProfileRepository.findAll()) {
            if (a.getMail() != null) {
                String message = String.format(
                        "Hello %s,\n" +
                                " Today is our discount day, rather visit our site (http://localhost:8080/catalog)",
                        a.getUsername()
                );
                mailSender.send(a.getMail(), "Hello", message);
            }
        }
    }
}
