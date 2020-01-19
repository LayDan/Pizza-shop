package application.service.impl;

import application.domain.Product;
import application.domain.TypeProduct;
import application.domain.UserProfile;
import application.repository.ProductRepository;
import application.repository.UserProfileRepository;
import application.service.IProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
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
                .build();
        productRepository.saveAndFlush(newProduct);
        return newProduct;

    }

    @Override
    public Product editProduct(Product product, Product newProduct) {
        Product cheekProduct = productRepository.findProductById(product.getId());
        if (cheekProduct != null && cheekProduct != newProduct) {
            cheekProduct.setCode(newProduct.getCode());
            cheekProduct.setName(newProduct.getName());
            cheekProduct.setDescription(newProduct.getDescription());
            cheekProduct.setType(newProduct.getType());
            cheekProduct.setPriceFromSize(newProduct.getPriceFromSize());
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
    public Double buyProduct(UserProfile userProfile) {
        UserProfile cheekUser = userProfileRepository.findByUsername(userProfile.getUsername());
        if (cheekUser != null && !cheekUser.getBasket().isEmpty()) {
            Double money = 0.00;
            for (Product a : cheekUser.getBasket()) {
                money = money + a.getPrice();
            }
            userProfile.getBasket().clear();
            return money;
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

    ////////////////////////////////////////////////////
//    private Map<String, Double> getMap(String string) {
//        String[] arr = string.split(" ");
//        String newString = "";
//        for (String a : arr) {
//            newString = newString.concat(a);
//        }
//        arr = newString.split(";");
//        for (String a : arr) {
//            a.split("-");
//        }
//        Map<String, Double> an = new HashMap<String, Double>();
//        return an;
//    }

    //////////////////////////////////////////////////////////////

}
