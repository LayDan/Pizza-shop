package application.repository;

import application.domain.UserProfile;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Cacheable("user")
    Optional<UserProfile> findByUsername(String username);

    @Cacheable("user")
    Optional<UserProfile> findByMail(String mail);

    @Cacheable("user")
    Optional<UserProfile> findByActivationCode(String code);

    @Cacheable("users")
    @Override
    List<UserProfile> findAll();

    @Override
    @CacheEvict(value = {"user"}, allEntries = true)
    <S extends UserProfile> S saveAndFlush(S s);

    @CacheEvict(value = {"user"}, allEntries = true)
    @Override
    <S extends UserProfile> S save(S s);

    @CachePut("user")
    @Override
    Optional<UserProfile> findById(Long aLong);


}
