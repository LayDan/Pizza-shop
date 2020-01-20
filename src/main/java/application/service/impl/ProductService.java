package application.service.impl;

import application.domain.Product;
import application.domain.TypeProduct;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.repository.UserProfileRepository;
import application.service.IProductService;
import org.springframework.beans.factory.annotation.Value;
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

    public ProductService(ProductRepository productRepository, UserProfileRepository userProfileRepository) {
        this.productRepository = productRepository;
        this.userProfileRepository = userProfileRepository;
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
        Product cheekProduct = productRepository.findProductById(product.getId());
        if (name == null && cheekProduct != null) {
            name = cheekProduct.getName();
        }
        if (stock == null) {
            stock = 0.0;
        }
        if (cheekProduct != null) {
            cheekProduct.setName(name);
            cheekProduct.setStock(stock);
            for (String a : cheekProduct.getPriceFromSize().keySet()) {
                Double b = cheekProduct.getPriceFromSize().get(a);
                cheekProduct.getPriceFromSize().replace(a, b, b - (b * (stock / 100)));
            }
            productRepository.save(cheekProduct);
            return cheekProduct;
        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findProductById(id);
        if (product != null) {
            productRepository.delete(productRepository.findProductById(id));
        }
    }

    @Override
    public void addToCart(UserProfile userProfile, Product product, Double price) {
        UserProfile cheekUser = userProfileRepository.findByUsername(userProfile.getUsername());
        Product cheekProduct = productRepository.findProductById(product.getId());
        if (cheekUser != null && cheekProduct != null) {
            product.setPrice(price);
            userProfile.getBasket().add(product);
            userProfileRepository.save(userProfile);
        }
    }

    @Override
    public Double buyProduct(UserProfile userProfile, Double delivery) {
        UserProfile cheekUser = userProfileRepository.findByUsername(userProfile.getUsername());
        if (cheekUser != null && !cheekUser.getBasket().isEmpty()) {
            Double money = 0.00;
            for (Product a : cheekUser.getBasket()) {
                money = money + a.getPrice();
            }
            userProfile.getBasket().clear();
            if (delivery != null) {
                return money + delivery;
            }
        }
        return null;
    }

    @Override
    public List<Product> sortType(String type) {
        for (TypeProduct a : TypeProduct.values()) {
            if (a.name().equals(type)) {
                return Arrays.asList(productRepository.findAllByType(a));
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
