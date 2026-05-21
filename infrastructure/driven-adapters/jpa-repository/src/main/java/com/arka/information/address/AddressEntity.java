package com.arka.information.address;

import com.arka.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    private String zipCode;

    @Column(nullable = false)
    private String address;

    private String notes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType type;

    @Column(name = "is_active", nullable = false)
    private boolean active;
}
