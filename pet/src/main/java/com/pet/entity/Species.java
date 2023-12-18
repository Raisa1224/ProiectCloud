package com.pet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "species")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer species_id;

    @Column(name = "species_name")
    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, message = "Name must have at least 3 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name must contain only letters")
    private String speciesName;

    @Column(name = "species_description")
    @NotEmpty(message = "Description is mandatory")
    @Size(min = 10, message = "Description must have at least 10 characters")
    private String speciesDescription;
}
