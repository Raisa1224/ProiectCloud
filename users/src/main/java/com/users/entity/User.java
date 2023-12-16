package com.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "firstname")
    @NotEmpty(message = "Firstname is required")
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty(message = "Lastname is required")
    private String lastname;

    @Column(name = "address")
    @NotEmpty(message = "Address is required")
    private String address;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dateOfBirth;

    @Column(name = "email")
    @NotEmpty(message = "Email is required")
    private String email;

    @Column(name = "user_password")
    @NotEmpty(message = "Password is required")
    private String userPassword;

    @Column(name = "phone")
    @NotEmpty(message = "Phone is required")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
