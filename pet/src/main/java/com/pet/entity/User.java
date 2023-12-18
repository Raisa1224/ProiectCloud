package com.pet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "firstname")
    @NotEmpty(message = "Firstname is mandatory")
    @Size(min = 3, message = "Firstname must have at least 3 characters")
    @Pattern(regexp = "[a-zA-Z -]+", message = "Firstname must contain only letters, spaces or -")
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty(message = "Lastname is mandatory")
    @Size(min = 3, message = "Lastname must have at least 3 characters")
    @Pattern(regexp = "[a-zA-Z -]+", message = "Lastname must contain only letters, spaces or -")
    private String lastname;

}
