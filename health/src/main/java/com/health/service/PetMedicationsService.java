package com.health.service;

import com.health.repository.PetMedicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetMedicationsService {
    @Autowired
    PetMedicationsRepository petMedicationsRepository;
}
