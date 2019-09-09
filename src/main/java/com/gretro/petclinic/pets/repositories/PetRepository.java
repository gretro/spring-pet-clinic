package com.gretro.petclinic.pets.repositories;

import com.gretro.petclinic.pets.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findBySlug(String slug);
}
