package taweryawer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceLabelDTO {

    private PriceCategoryDTO priceCategoryDTO;

    private Double value;
}
