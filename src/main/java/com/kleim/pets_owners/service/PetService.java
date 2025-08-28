package com.kleim.pets_owners.service;

import com.kleim.pets_owners.models.Pet;
import com.kleim.pets_owners.models.PetConverter;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

public class PetService {

    public AtomicLong increment = new AtomicLong();
    private final UserService userService = new UserService();
    private final PetConverter petConverter = new PetConverter();


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
        userService.findUserById(petId).petsList().add(petConverter.toPetDTO(newPet));

    }
}
