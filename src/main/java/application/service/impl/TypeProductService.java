package application.service.impl;

import application.domain.TypeProduct;
import application.repository.TypeProductRepository;
import application.service.ITypeProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
            return Optional.ofNullable(newTypeProduct);
        }
        return Optional.empty();
    }
}
