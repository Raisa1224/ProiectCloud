package com.adoption.repository;

import com.adoption.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {

    @Query("SELECT ar FROM AdoptionRequest ar WHERE ar.client.userId = :clientId")
    List<AdoptionRequest> findByClientId(Integer clientId);

    @Query("SELECT ar FROM AdoptionRequest ar WHERE ar.pet.petId = :petId")
    List<AdoptionRequest> findByPetId(Integer petId);

    @Query("SELECT ar FROM AdoptionRequest ar WHERE ar.pet.owner.userId = :ownerId")
    List<AdoptionRequest> findByOwnerId(Integer ownerId);
}
