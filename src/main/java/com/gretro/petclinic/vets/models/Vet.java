package com.gretro.petclinic.vets.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Set;

@Entity
@Builder
@ToString()
@Table(name = "vets")
@EqualsAndHashCode
public class Vet {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vets__vet_specialties",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_specialty_id"))
    private Set<VetSpecialty> specialties;

    @Getter
    @Setter
    @Column(name = "created_at")
    @CreationTimestamp
    @NotEmpty
    private Instant createdAt;

    @Getter
    @Setter
    @Column(name = "created_by")
    @NotEmpty
    private String createdBy;

    @Getter
    @Setter
    @UpdateTimestamp
    @Column(name = "modified_at")
    private Instant lastModifiedAt;

    @Getter
    @Setter
    @Column(name = "modified_by")
    private String lastModifiedBy;
}
