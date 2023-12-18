package com.pet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, message = "Name must have at least 3 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name must contain only letters")
    private String breedName;

    @Column(name = "breed_description")
    @NotEmpty(message = "Description is mandatory")
    @Size(min = 10, message = "Description must have at least 10 characters")
    private String breedDescription;

    @Column(name = "lifespan")
    @NotNull(message = "Lifespan is mandatory")
    @Min(value = 10, message = "Lifespan must be at least equal to 10")
    private Integer lifespan;
}
