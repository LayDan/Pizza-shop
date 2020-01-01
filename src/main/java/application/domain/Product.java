package application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Double price;
    private Integer code;
    private String name;
    private String description;
    private String type;
    private Map<String, price> priceFromSize;
}
