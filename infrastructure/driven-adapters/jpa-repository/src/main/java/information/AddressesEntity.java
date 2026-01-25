package information;

import com.arka.entities.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class AddressesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = true)
    private short zipCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = false)
    private AddressType addressType;

    @Column(nullable = false)
    private boolean isActive;

    public AddressesEntity() {
        this.isActive = true;
    }
}
