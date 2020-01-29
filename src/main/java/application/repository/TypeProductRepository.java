package application.repository;

import application.domain.TypeProduct;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {

    @CachePut("type")
    Optional<TypeProduct> findByType(String type);

    @Cacheable("typesValue")
    default List<String> findAllType() {
        List<String> allTypes = new ArrayList<>();
        for (TypeProduct a : findAll()) {
            allTypes.add(allTypes.size(), a.getType());
        }
        return allTypes;
    }

    @Cacheable("types")
    @Override
    List<TypeProduct> findAll();

    @Override
    @CacheEvict(value = {"types","typesValue"}, allEntries = true)
    <S extends TypeProduct> S saveAndFlush(S s);
}
