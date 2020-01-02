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
    @Enumerated(EnumType.STRING)
    private TypeProduct type;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Product_priceFromSize", joinColumns = @JoinColumn(name = "Product_id"))
    @NonNull
    private Map<String, Double> priceFromSize;
}
