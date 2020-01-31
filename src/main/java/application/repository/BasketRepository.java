package application.repository;

import application.domain.Basket;
import application.domain.UserProfile;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Cacheable("basket")
    List<Basket> findAllByUserProfile(UserProfile userProfile);

    @CacheEvict(value = {"basket"}, allEntries = true)
    @Override
    <S extends Basket> S saveAndFlush(S s);

    @CacheEvict(value = {"basket"}, allEntries = true)
    @Override
    void deleteAll(Iterable<? extends Basket> iterable);
}
