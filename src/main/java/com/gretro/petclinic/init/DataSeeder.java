package com.gretro.petclinic.init;

import com.gretro.petclinic.vets.models.VetSpecialty;
import com.gretro.petclinic.vets.repositories.VetRepository;
import com.gretro.petclinic.vets.repositories.VetSpecialtiesRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final VetSpecialtiesRepository vetSpecialtiesRepository;
    private final VetRepository vetRepository;

    private Map<String, VetSpecialty> vetSpecialities;

    private VetSpecialty radiology;
    private VetSpecialty surgery;
    private VetSpecialty dentistry;

    public DataSeeder(VetSpecialtiesRepository vetSpecialtiesRepository, VetRepository vetRepository) {
        this.vetSpecialtiesRepository = vetSpecialtiesRepository;
        this.vetRepository = vetRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Starting database seeding");

        this.createSpecialties();

        log.info("Database seeded");
    }

    private void createSpecialties() {
        log.info("Adding Vet Specialties");
        this.vetSpecialtiesRepository.deleteAll();

        this.radiology = VetSpecialty.builder()
            .name("Radiology")
            .slug("radiology")
            .createdBy("system")
            .build();

        this.surgery = VetSpecialty.builder()
            .name("Surgery")
            .slug("surgery")
            .createdBy("system")
            .build();

        this.dentistry = VetSpecialty.builder()
            .name("Dentistry")
            .slug("dentistry")
            .createdBy("system")
            .build();

        ArrayList<VetSpecialty> vetSpecialties = new ArrayList<>();
        vetSpecialties.add(this.radiology);
        vetSpecialties.add(this.surgery);
        vetSpecialties.add(this.dentistry);

        this.vetSpecialtiesRepository.saveAll(vetSpecialties);
    }
}
