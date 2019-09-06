package com.gretro.petclinic.vets.repositories;

import com.gretro.petclinic.vets.models.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    Optional<Vet> findBySlug(String slug);
}
