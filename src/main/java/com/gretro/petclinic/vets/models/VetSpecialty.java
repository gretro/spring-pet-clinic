package com.gretro.petclinic.vets.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(
    name = "vet_specialties",
    indexes = {
        @Index(name = "ix_vet_specialities_slug", columnList = "slug", unique = true)
    })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VetSpecialty {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vets__vet_specialties",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_specialty_id"))
    @ToString.Exclude
    private Set<Vet> vets;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "created_by", updatable = false, nullable = false)
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = true)
    private Instant lastModifiedAt;

    @Getter
    @Setter
    @Column(name = "modified_by", nullable = true)
    private String lastModifiedBy;
}
