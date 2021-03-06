package com.gretro.petclinic.pets.repositories;

import com.gretro.petclinic.pets.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findBySlug(String slug);
}
