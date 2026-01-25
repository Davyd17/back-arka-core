package information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String position;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Instant created_at;

    @Column(nullable = false)
    private Instant updated_at;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private Long userId;

    @ManyToMany
    @JoinTable(
            name = "addresses_contacts",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<AddressesEntity> addresses;

    public ContactEntity() {
        this.isActive = true;
    }
}
