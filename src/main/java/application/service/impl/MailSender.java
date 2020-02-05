package application.service.impl;

import application.domain.UserProfile;
import application.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailSender {


    public MailSender(JavaMailSender mailSender, UserProfileRepository userProfileRepository) {
        this.mail = mailSender;
        this.userProfileRepository = userProfileRepository;
    }

    private JavaMailSender mail;
    private UserProfileRepository userProfileRepository;


    @Value("${spring.mail.username}")
    private String username;

    public void send(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mail.send(mailMessage);
    }

    @Scheduled(fixedRate = 86400000)
    public void reportCurrentTime() {
        for (UserProfile a : userProfileRepository.findAll()) {
            if (a.getMail() != null) {
                String message = String.format(
                        "Hello %s,\n" +
                                " Today is our discount day, rather visit our site (http://localhost:8080/catalog)",
                        a.getUsername()
                );
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(username);
                mailMessage.setTo(a.getMail());
                mailMessage.setSubject("Discount");
                mailMessage.setText(message);

                mail.send(mailMessage);
            }
        }
    }
}
