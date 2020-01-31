package application.repository;

import application.domain.Product;
import application.domain.TypeProduct;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @CachePut("product")
    Optional<Product> findById(Long id);

    @Cacheable("productsByType")
    ArrayList<Product> findAllByType(TypeProduct type);

    @Override
    @Cacheable("productsAll")
    List<Product> findAll();

    @Override
    @CacheEvict(value = {"productsByType", "productsAll"}, allEntries = true)
    void delete(Product product);

    @Override
    @CacheEvict(value = {"productsByType", "productsAll"}, allEntries = true)
    <S extends Product> S saveAndFlush(S s);
}
