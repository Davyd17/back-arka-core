package com.arka.information.contact;

import com.arka.entities.information.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    boolean existsByEmail(String email);

    Optional<Contact> findByEmail(String email);
}
