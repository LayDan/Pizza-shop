package application.repository;

import application.domain.Product;
import application.domain.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);

    ArrayList<Product> findAllByType(TypeProduct type);
}
