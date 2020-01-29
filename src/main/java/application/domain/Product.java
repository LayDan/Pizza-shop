package application.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long code;
    private String name;
    private String description;
    private Double price;
    private String imagePath;
    private Double stock;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "type")
    private TypeProduct type;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Product_priceFromSize", joinColumns = @JoinColumn(name = "Product_id"))
    @NonNull
    private Map<String, Double> priceFromSize;
}
