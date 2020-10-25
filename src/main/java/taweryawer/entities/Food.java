package taweryawer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "food")
public class Food {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String weight;
    private String description;
    private String photoURL;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "food_price_label",
            joinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "price_label_id", referencedColumnName = "id", nullable = false))
    private List<PriceLabel> priceLabels;

}
