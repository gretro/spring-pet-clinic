package com.gretro.petclinic.init;

import com.gretro.petclinic.vets.models.Customer;
import com.gretro.petclinic.vets.models.Pet;
import com.gretro.petclinic.vets.models.Vet;
import com.gretro.petclinic.vets.models.VetSpecialty;
import com.gretro.petclinic.vets.repositories.CustomerRepository;
import com.gretro.petclinic.vets.repositories.PetRepository;
import com.gretro.petclinic.vets.repositories.VetRepository;
import com.gretro.petclinic.vets.repositories.VetSpecialtiesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@ConditionalOnProperty("petclinic.dataseeder.enabled")
@Slf4j
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final VetSpecialtiesRepository vetSpecialtiesRepository;
    private final VetRepository vetRepository;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    private VetSpecialty generalist;
    private VetSpecialty radiology;
    private VetSpecialty surgery;
    private VetSpecialty dentistry;

    public DataSeeder(
            VetSpecialtiesRepository vetSpecialtiesRepository, VetRepository vetRepository,
            CustomerRepository customerRepository, PetRepository petRepository) {
        this.vetSpecialtiesRepository = vetSpecialtiesRepository;
        this.vetRepository = vetRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Starting database seeding");

        this.clearValues();

        this.populateSpecialties();
        this.populateVets();
        this.populatePetsAndOwners();

        log.info("Database seeded");

        Optional<Customer> maybeCustomer = this.customerRepository.findBySlug("theresa-may");
        maybeCustomer.ifPresent(customer -> {
            log.info("Retrieved Theresa May customer: %s", customer.toString());
        });
    }

    private void clearValues() {
        log.info("Clearing existing values");
        this.vetRepository.deleteAll();
        this.vetSpecialtiesRepository.deleteAll();
        this.petRepository.deleteAll();
        this.customerRepository.deleteAll();
    }

    private void populateSpecialties() {
        log.info("Adding Vet Specialties");

        this.generalist = VetSpecialty.builder()
                .name("Generalist")
                .slug("generalist")
                .createdBy("system")
                .build();

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

        List<VetSpecialty> vetSpecialties = List.of(this.generalist, this.radiology, this.surgery, this.dentistry);
        this.vetSpecialtiesRepository.saveAll(vetSpecialties);
    }

    private void populateVets() {
        log.info("Adding Vets");

        Vet vet1 = Vet.builder()
                .firstName("John")
                .lastName("Doe")
                .slug("john_doe")
                .specialties(Set.of(this.generalist))
                .createdBy("system")
                .build();

        Vet vet2 = Vet.builder()
                .firstName("Geneviève")
                .lastName("Perras")
                .slug("genevieve_perras")
                .specialties(Set.of(this.generalist, this.surgery))
                .createdBy("system")
                .build();

        Vet vet3 = Vet.builder()
                .firstName("Robert")
                .lastName("De Niro")
                .slug("robert_de_niro")
                .specialties(Set.of(this.dentistry))
                .createdBy("system")
                .build();

        Vet vet4 = Vet.builder()
                .firstName("Lyra")
                .lastName("Bella")
                .slug("lyra_bella")
                .specialties(Set.of(this.radiology))
                .createdBy("system")
                .build();

        Vet vet5 = Vet.builder()
                .firstName("Donald")
                .lastName("Trumpf")
                .slug("donald_trumpf")
                .specialties(Set.of(this.generalist))
                .createdBy("system")
                .build();

        List<Vet> vets = List.of(vet1, vet2, vet3, vet4, vet5);
        this.vetRepository.saveAll(vets);
    }

    private void populatePetsAndOwners() {
        Customer c1 = Customer.builder()
                .firstName("Stephanie")
                .lastName("Rovo")
                .slug("stephanie-rovo")
                .createdBy("system")
                .build();

        Customer c2 = Customer.builder()
                .firstName("Damien")
                .lastName("Smith")
                .slug("damien-smith")
                .createdBy("system")
                .build();

        Customer c3 = Customer.builder()
                .firstName("Theresa")
                .lastName("May")
                .slug("theresa-may")
                .createdBy("system")
                .build();

        this.customerRepository.saveAll(List.of(c1, c2, c3));

        Pet c1p1 = Pet.builder()
                .name("fido")
                .slug("fido")
                .createdBy("system")
                .ownerId(c1.getId())
                .build();

        Pet c1p2 = Pet.builder()
                .name("cato")
                .slug("cato")
                .createdBy("system")
                .ownerId(c1.getId())
                .build();

        Pet c2p1 = Pet.builder()
                .name("Snow")
                .slug("snow")
                .createdBy("system")
                .ownerId(c2.getId())
                .build();

        this.petRepository.saveAll(List.of(c1p1, c1p2, c2p1));
    }
}
