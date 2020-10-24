package taweryawer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FoodDTO {

    private Long id;

    private String title;

    private String weight;

    private String description;

    private String photoURL;

    private CategoryDTO category;

    private List<PriceLabelDTO> priceLabels;
}
