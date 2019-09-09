package com.gretro.petclinic.vets.repositories;

import com.gretro.petclinic.vets.models.VetSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VetSpecialtyRepository extends JpaRepository<VetSpecialty, Long> {
    Optional<VetSpecialty> findBySlug(String slug);
}
