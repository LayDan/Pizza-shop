package application.service.impl;

import application.domain.Product;
import application.domain.TypeProduct;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.repository.TypeProductRepository;
import application.repository.UserProfileRepository;
import application.service.IProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService implements IProductService {

    @Value("${upload.path}")
    private String uploadPath;

    private ProductRepository productRepository;

    private UserProfileRepository userProfileRepository;

    private TypeProductRepository typeProductRepository;

    public ProductService(ProductRepository productRepository, UserProfileRepository userProfileRepository, TypeProductRepository typeProductRepository) {
        this.productRepository = productRepository;
        this.userProfileRepository = userProfileRepository;
        this.typeProductRepository = typeProductRepository;
    }

    @Override
    public Product addProduct(Product product, MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + "/" + resultFilename));

        LinkedHashMap<String, Double> newMap = product.getPriceFromSize().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Product newProduct = Product.builder()
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .type(product.getType())
                .priceFromSize(newMap)
                .imagePath(resultFilename)
                .price(null)
                .stock(null)
                .build();
        productRepository.saveAndFlush(newProduct);
        return newProduct;

    }

    @Override
    public Product editProduct(Product product, Double stock, String name) {
        Optional<Product> cheekProduct = productRepository.findById(product.getId());
        if (name == null && cheekProduct.isPresent()) {
            name = cheekProduct.get().getName();
        }
        if (stock == null) {
            stock = 0.0;
        }
        if (cheekProduct.isPresent()) {
            cheekProduct.get().setName(name);
            cheekProduct.get().setStock(stock);
            for (String a : cheekProduct.get().getPriceFromSize().keySet()) {
                Double b = cheekProduct.get().getPriceFromSize().get(a);
                cheekProduct.get().getPriceFromSize().replace(a, b, b - (b * (stock / 100)));
            }
            productRepository.save(cheekProduct.get());
            return cheekProduct.get();
        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(value -> productRepository.delete(value));
    }

    @Override
    public void addToCart(Long id, Product product, Double price) {
        Optional<UserProfile> cheekUser = userProfileRepository.findById(id);
        Optional<Product> cheekProduct = productRepository.findById(product.getId());
        if (cheekUser.isPresent() && cheekProduct.isPresent()) {
            product.setPrice(price);
            cheekUser.get().getBasket().add(product);
            userProfileRepository.save(cheekUser.get());
        }
    }

    @Override
    public List<Product> sortType(String type) {
        if (type == null) {
            return productRepository.findAll();
        }
        for (TypeProduct a : typeProductRepository.findAll()) {
            if (a.getType().equals(type)) {
                return productRepository.findAllByType(typeProductRepository.findByType(type).get());
            }
        }

        return productRepository.findAll();
    }

    @Override
    public ArrayList<Product> search(String name) {
        char[] arrName = name.toLowerCase().toCharArray();
        ArrayList<Product> arr = new ArrayList<>();
        for (Product p : productRepository.findAll()) {
            char[] product = p.getName().toLowerCase().toCharArray();
            for (int i = 0; i < arrName.length; i++) {
                if (arrName[i] != product[i]) {
                    break;
                }
                if (i == arrName.length - 1 && arrName[i] == product[i]) {
                    arr.add(p);
                } else if (i == product.length - 1 && arrName[i] == product[i]) {
                    arr.add(p);
                    break;
                }
            }
        }
        return arr;
    }
}
