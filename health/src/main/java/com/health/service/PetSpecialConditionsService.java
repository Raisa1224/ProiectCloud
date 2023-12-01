package com.health.service;

import com.health.repository.PetSpecialConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetSpecialConditionsService {
    @Autowired
    PetSpecialConditionsRepository petSpecialConditionsRepository;
}
