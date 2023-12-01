package com.adoption.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "adoption_request")
public class AdoptionRequest {

    @Id
    private Integer adoptionRequestId;

    @Column(name = "adoption_date")
    private Date adoptionDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}