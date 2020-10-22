package taweryawer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "price_label")
public class PriceLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "price_category_id", referencedColumnName = "id", nullable = false)
    private PriceCategory priceCategory;

    private Double value;
}
