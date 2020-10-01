package taweryawer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import taweryawer.entities.Category;

import javax.persistence.*;

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
    private Integer priceKyiv;
    private Integer priceKharkiv;

}
