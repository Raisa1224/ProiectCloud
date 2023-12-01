package com.health.service;

import com.health.repository.PetVeterinaryVisitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetVeterinaryVisitsService {
    @Autowired
    PetVeterinaryVisitsRepository petVeterinaryVisitsRepository;
}
