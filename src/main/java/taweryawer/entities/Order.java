package taweryawer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import taweryawer.entities.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "order")
    private List<OrderPiece> orderPieces;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated
    private OrderStatus orderStatus;

    private LocalDateTime dateTime;
}
