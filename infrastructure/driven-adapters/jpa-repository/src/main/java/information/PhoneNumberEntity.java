package information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "phone_numbers")
public class PhoneNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = true)
    private short extension;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private Instant created_at;

    @Column(nullable = false)
    private Instant updated_at;

    public PhoneNumberEntity() {
        this.isActive = true;
        this.created_at = Instant.now();
    }
}


