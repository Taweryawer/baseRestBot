package taweryawer.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import taweryawer.dto.OrderDTO;
import taweryawer.entities.Order;

@Component
public class OrderMapper {

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO toDto(Order entity) {
        OrderDTO dto = modelMapper.map(entity, OrderDTO.class);
        dto.setSubmissionDate(entity.getDateTime());
        dto.setOrdererName(entity.getUser().getName());
        return dto;
    }
}
