package com.pet.service;

import com.pet.entity.Breed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BreedServiceImpl {

    Page<Breed> findPaginated(Pageable pageable);
}
