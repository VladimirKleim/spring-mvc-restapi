package com.kleim.pets_owners.service;

import com.kleim.pets_owners.models.Pet;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

public class PetService {

    public AtomicLong increment = new AtomicLong();
    private final UserService userService = new UserService();


    public Pet createPet(Pet petToCreate) {
        if (petToCreate.id() == null) {
            throw new NoSuchElementException("Pet with id: %s not found".formatted(petToCreate.id()));
        }
        var petId = increment.incrementAndGet();
        Pet newPet = new Pet(
                petId,
                petToCreate.name(),
                petToCreate.userId()
        );
        userService.findUserById(petId).petsList().add(newPet);

    }
}
