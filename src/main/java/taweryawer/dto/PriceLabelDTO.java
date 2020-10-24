package taweryawer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceLabelDTO {

    private PriceCategoryDTO priceCategory;

    private Double value;
}
