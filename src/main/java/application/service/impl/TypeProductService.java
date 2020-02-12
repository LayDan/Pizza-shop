package application.service.impl;

import application.domain.TypeProduct;
import application.repository.TypeProductRepository;
import application.service.ITypeProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TypeProductService implements ITypeProductService {


    public TypeProductService(TypeProductRepository typeProductRepository) {
        this.typeProductRepository = typeProductRepository;
    }

    private TypeProductRepository typeProductRepository;


    @Override
    public Optional<TypeProduct> addType(String type) {
        if (type != null && !typeProductRepository.findByType(type).isPresent()) {
            TypeProduct newTypeProduct = TypeProduct.builder()
                    .type(type)
                    .build();
            typeProductRepository.saveAndFlush(newTypeProduct);
            log.info("Create TypeProduct " + newTypeProduct.getId());
            return Optional.of(newTypeProduct);
        }
        return Optional.empty();
    }
}
