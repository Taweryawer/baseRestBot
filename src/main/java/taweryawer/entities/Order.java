package taweryawer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.entities.enums.PaymentMethod;

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

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderPiece> orderPieces;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated
    private OrderStatus orderStatus;

    @Enumerated
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    private LocalDateTime dateTime;
}
