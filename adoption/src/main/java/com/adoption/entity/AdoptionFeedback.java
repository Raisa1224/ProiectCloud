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
@Table(name = "adoption_feedback")
public class AdoptionFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @Column(name = "feedback_date")
    private Date feedbackDate;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adoption_request_id")
    private AdoptionRequest adoptionRequest;
    
}