package com.pet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "breed")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer breed_id;

    @Column(name = "breed_name")
    private String breedName;

    @Column(name = "breed_description")
    private String breedDescription;

    @Column(name = "lifespan")
    private Integer lifespan;
}
