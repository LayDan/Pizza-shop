package application.repository;

import application.domain.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {
    Optional<TypeProduct> findByType(String type);

    default List<String> findAllType() {
        List<String> allTypes = new ArrayList<>();
        for (TypeProduct a : findAll()) {
            allTypes.add(allTypes.size(), a.getType());
        }
        return allTypes;
    }
}
