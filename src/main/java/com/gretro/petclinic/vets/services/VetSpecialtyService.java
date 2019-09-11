package com.gretro.petclinic.vets.services;

import com.gretro.petclinic.exceptions.EntityNotFoundException;
import com.gretro.petclinic.utils.SlugHelper;
import com.gretro.petclinic.vets.models.VetSpecialty;
import com.gretro.petclinic.vets.repositories.VetSpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VetSpecialtyService {
    private final VetSpecialtyRepository vetSpecialtyRepository;

    public VetSpecialtyService(VetSpecialtyRepository vetSpecialtyRepository) {
        this.vetSpecialtyRepository = vetSpecialtyRepository;
    }

    public List<VetSpecialty> getAllVetSpecialties() {
        return this.vetSpecialtyRepository.findAll();
    }

    public Optional<VetSpecialty> findBySlug(String slug) {
        return this.vetSpecialtyRepository.findBySlug(slug);
    }

    public VetSpecialty create(VetSpecialty model) {
        var tentativeSlug = SlugHelper.calculateSlug(model.getName());
        var similarSlugs = this.vetSpecialtyRepository.findSimilarSlugs(tentativeSlug);

        var finalSlug = SlugHelper.incrementSlug(tentativeSlug, similarSlugs);
        model.setSlug(finalSlug);

        // TODO: Replace with principal information
        model.setCreatedBy("unknown");

        var savedModel = this.vetSpecialtyRepository.save(model);
        return savedModel;
    }

    public VetSpecialty update(String slug, VetSpecialty model) {
        var toUpdate = this.vetSpecialtyRepository.findBySlug(slug)
            .orElseThrow(() -> new EntityNotFoundException(VetSpecialty.class, slug));

        toUpdate.setName(model.getName());

        // TODO: Replace with principal information
        toUpdate.setLastModifiedBy("unknown");

        var updatedModel = this.vetSpecialtyRepository.save(toUpdate);
        return updatedModel;
    }
}
