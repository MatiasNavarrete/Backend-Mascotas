package com.example.mascota.entity;

import com.example.mascota.enums.PetStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "pets", indexes = {
        @Index(name = "idx_pet_status", columnList = "status"),
        @Index(name = "idx_pet_owner", columnList = "owner_id")
})

@SQLDelete(sql = "UPDATE pets SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MascotaEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String species;

    private String breed;

    @Column(length = 600)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetStatus status; // LOST, FOUND, SIGHTED, RECOVERED

    private String lastKnownLocation;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

}