package com.arka.information.contact;

import com.arka.company.CompanyEntity;
import com.arka.information.address.AddressEntity;
import com.arka.information.phonenumber.PhoneNumberEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String companyPosition;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(nullable = false, insertable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private CompanyEntity company;

    @OneToMany
    @JoinColumn(name = "contact_id", nullable = false)
    private List<AddressEntity> addresses;

    @OneToMany
    @JoinColumn(name = "contact_id", nullable = false)
    private List<PhoneNumberEntity> phoneNumbers;

}
