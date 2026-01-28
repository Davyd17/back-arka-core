package com.arka.party.employee;

import com.arka.information.contact.ContactEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(nullable = false)
    private int code;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_id", unique = true, nullable = false)
    private ContactEntity contact;
}
