package taweryawer.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class FoodForm {

    @NotEmpty(message = "Не может быть пустым")
    private String title;

    private String weight;

    @Size(max = 255, message = "Превышена максимальная длина(255)")
    private String description;

    @NotEmpty(message = "Не может быть пустым")
    private String photoURL;

    @NotNull(message = "Выберите хотя бы одну категорию")
    private String category;

    @Size(min = 1, message = "Введите хотя бы один ценник")
    private List<String> priceLabels;
}
