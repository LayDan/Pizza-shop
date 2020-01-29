package application.service;

import application.domain.TypeProduct;

import java.util.Optional;

public interface ITypeProductService {
    Optional<TypeProduct> addType(String type);
}
