package com.gretro.petclinic.vets.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(
    name = "customers",
    indexes = {
        @Index(name = "ix_customers_slug", columnList = "slug", unique = true)
    }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude()
    private Set<Pet> pets;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp()
    private Instant createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = true)
    private Instant lastModifiedAt;

    @Column(name = "modified_by", nullable = true)
    private String lastModifiedBy;
}
