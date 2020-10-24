package taweryawer.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FoodForm {

    private String title;

    private String weight;

    private String description;

    private String photoURL;

    private String category;

    private List<String> priceLabels;
}
