package com.gretro.petclinic.vets.repositories;

import com.gretro.petclinic.vets.models.VetSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetSpecialtiesRepository extends JpaRepository<VetSpecialty, Long> {
}
