package com.pet.service;

import com.pet.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetServiceImpl {
    Page<Pet> findPaginated(Pageable pageable);
}
