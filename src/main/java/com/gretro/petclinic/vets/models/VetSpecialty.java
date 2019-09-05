package com.gretro.petclinic.vets.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Table(
    name = "vet_specialties",
    indexes = {
        @Index(name = "ix_vet_specialities_slug", columnList = "slug", unique = true)
    })
@ToString
@EqualsAndHashCode
public class VetSpecialty {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "slug", nullable = false)
    private String slug;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vets__vet_specialties",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_specialty_id"))
    private Set<Vet> vets;

    @Getter
    @Setter
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Getter
    @Setter
    @Column(name = "created_by", updatable = false, nullable = false)
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
