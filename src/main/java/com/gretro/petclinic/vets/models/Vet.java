package com.gretro.petclinic.vets.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(
    name = "vets",
    indexes = {
        @Index(name = "ix_vets_slug", columnList = "slug", unique = true)
    }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vet {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vets__vet_specialties",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_specialty_id"))
    @ToString.Exclude()
    private Set<VetSpecialty> specialties;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = true)
    private Instant lastModifiedAt;

    @Column(name = "modified_by", nullable = true)
    private String lastModifiedBy;
}
