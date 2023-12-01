package com.health.service;

import com.health.repository.PetVaccinationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetVaccinationsService {
    @Autowired
    PetVaccinationsRepository petVaccinationsRepository;
}
