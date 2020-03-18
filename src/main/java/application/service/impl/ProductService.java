package application.service.impl;

import application.domain.Basket;
import application.domain.Product;
import application.domain.TypeProduct;
import application.repository.BasketRepository;
import application.repository.ProductRepository;
import application.repository.TypeProductRepository;
import application.repository.UserProfileRepository;
import application.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ProductService implements IProductService {

    @Value("${upload.path}")
    private String uploadPath;

    private ProductRepository productRepository;

    private UserProfileRepository userProfileRepository;

    private TypeProductRepository typeProductRepository;

    private BasketRepository basketRepository;

    public ProductService(ProductRepository productRepository, UserProfileRepository userProfileRepository, TypeProductRepository typeProductRepository, BasketRepository basketRepository) {
        this.productRepository = productRepository;
        this.userProfileRepository = userProfileRepository;
        this.typeProductRepository = typeProductRepository;
        this.basketRepository = basketRepository;
    }

    @Override
    public Product addProduct(Product product, MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(String.format("%s/%s", uploadPath, resultFilename)));

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
                .promotionalItem(null)
                .build();
        productRepository.saveAndFlush(newProduct);
        log.info("Create product " + newProduct.getId() + " Name:" + newProduct.getName());
        return newProduct;
    }

    @CacheEvict(value = {"user", "productsPromotion", "productsByType", "productsAll"}, allEntries = true)
    @Override
    public Product editProduct(Product product, Double stock, String name) {
        Optional<Product> cheekProduct = productRepository.findById(product.getId());

        if (cheekProduct.isPresent() && (name == null || name.equals(""))) {
            name = cheekProduct.get().getName();
        }
        if (cheekProduct.isPresent()) {
            if (stock == null || stock == 0) {
                cheekProduct.get().setStock(null);
                cheekProduct.get().setPromotionalItem(false);
            } else {
                cheekProduct.get().setName(name);
                cheekProduct.get().setStock(stock);
                cheekProduct.get().setPromotionalItem(true);
                log.info("Update product " + cheekProduct.get().getId() + " Name:" + cheekProduct.get().getName());
                return productRepository.save(cheekProduct.get());
            }
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            log.info("Delete product " + product.get().getId() + " Name:" + product.get().getName());
            productRepository.delete(product.get());
        }
    }

    @CacheEvict(value = {"user"}, allEntries = true)
    @Override
    public void addToCart(Basket basket) {
        if (basket != null) {
            basket.getUserProfile().getBasket().add(basket);
        }
    }

    @Override
    public List<Product> sortType(String type) {
        if (type == null) {
            return productRepository.findAll();
        }
        for (TypeProduct a : typeProductRepository.findAll()) {
            Optional<TypeProduct> byType = typeProductRepository.findByType(type);
            if (a.getType().equals(type) && byType.isPresent()) {
                return productRepository.findAllByType(byType.get());
            }
        }
        return productRepository.findAll();
    }

    @Override
    public ArrayList<Product> search(String name) {
        if (name != null && !name.equals("")) {
            return findByName(name);
        } else {
            return (ArrayList<Product>) productRepository.findAll();
        }
    }

    @Async
    @Override
    public Object[] getProductsByNameFilter(String nameFilter) {
        return productRepository.findAll().stream()
                .filter(product -> product.getName().matches(nameFilter))
                .toArray();
    }

    private ArrayList<Product> findByName(String name) {
        char[] arrName = name.toLowerCase().toCharArray();
        ArrayList<Product> arr = new ArrayList<>();
        for (Product p : productRepository.findAll()) {
            char[] product = p.getName().toLowerCase().toCharArray();
            for (int i = 0; i < arrName.length; i++) {
                if (arrName[i] == product[i] && (i == product.length - 1 || i == arrName.length - 1)) {
                    arr.add(p);
                    break;
                }
            }
        }
        return arr;
    }
}
