package application.repository;

import application.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUsername(String username);

    Optional<UserProfile> findByMail(String mail);

    Optional<UserProfile> findByActivationCode(String code);
}
