package com.adoption.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    private Integer petId;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "price")
    private Double price;

    @Column(name = "available")
    private Boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;
}
