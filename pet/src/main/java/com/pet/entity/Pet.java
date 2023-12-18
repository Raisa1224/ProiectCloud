package com.pet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer petId;

    @Column(name = "pet_name")
    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, message = "Name must have at least 3 characters")
    @Pattern(regexp = "[a-zA-Z -]+", message = "Name must contain only letters, spaces or -")
    private String petName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "species_id")
    private Species species;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @Column(name = "year_of_birth")
    @NotNull(message = "Year of birth is mandatory and must be 4 digits long")
    @DateTimeFormat(pattern = "yyyy")
    private Integer yearOfBirth;

    @Column(name = "gender")
    @NotEmpty(message = "Gender is mandatory")
    @Pattern(regexp = "^(M|F)$", message = "Gender must be 'M' (Male) or 'F' (Female)")
    private String gender;

    @Column(name = "color")
    @NotEmpty(message = "Color is mandatory")
    @Pattern(regexp = "[a-zA-Z ]+", message = "Color must contain only letters")
    @Size(min = 3, message = "Color must have at least 3 characters")
    private String color;

    @Column(name = "price")
    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @Column(name = "available")
    private Boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;
}
