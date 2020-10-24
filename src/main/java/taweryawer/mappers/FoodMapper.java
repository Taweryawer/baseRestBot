package taweryawer.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import taweryawer.dto.FoodDTO;
import taweryawer.entities.Food;

@Component
public class FoodMapper {

    @Autowired
    private ModelMapper modelMapper;

    public FoodDTO toDto(Food food) {
        FoodDTO dto = modelMapper.map(food, FoodDTO.class);
        return dto;
    }

    public Food toEntity(FoodDTO dto) {
        return modelMapper.map(dto, Food.class);
    }
}
