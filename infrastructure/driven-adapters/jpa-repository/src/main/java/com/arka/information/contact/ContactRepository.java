package com.arka.information.contact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    boolean existsByEmail(String email);
}
