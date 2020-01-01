package application.service.impl;

import application.domain.Product;
import application.repository.ProductRepository;
import application.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        Product newProduct = Product.builder()
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .priceFromSize.
                .type(product.getType())
                .build();
        productRepository.saveAndFlush(newProduct);
        return newProduct;
    }
}
