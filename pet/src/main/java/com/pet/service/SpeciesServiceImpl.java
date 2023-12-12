package com.pet.service;

import com.pet.entity.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpeciesServiceImpl {

    Page<Species> findPaginated(Pageable pageable);
}
