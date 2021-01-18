package com.udacity.jdnd.course3.critter.repository;


import com.udacity.jdnd.course3.critter.entities.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> getPetsByCustomerId(long ownerId);
}
