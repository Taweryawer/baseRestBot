package taweryawer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import taweryawer.entities.enums.PaymentMethod;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "machine_id")
    private String machineId;

    @Column(name = "phone_number")
    private String phoneNumber;
}
