package com.kleim.pets_owners.service;

import com.kleim.pets_owners.models.pet.Pet;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PetService {

    public final AtomicLong increment;
    private final UserService userService;

    public PetService(UserService userService) {
        this.increment = new AtomicLong();
        this.userService = userService;
    }


    public Pet createPet(Pet petToCreate) {
        if (petToCreate.id() != null) {
            throw new IllegalArgumentException("Pet with id: %s not found".formatted(petToCreate.id()));
        }
        var petId = increment.incrementAndGet();
        Pet newPet = new Pet(
                petId,
                petToCreate.name(),
                petToCreate.userId()
        );
         userService.findUserById(petToCreate.userId()).petsList().add(newPet);
         return newPet;
    }


    public Pet getPet(Long id) {
        return getPetById(id).orElseThrow(() ->
                new IllegalArgumentException("No found pet by id: %s".formatted(id)));
    }


    public Optional<Pet> getPetById(Long id) {
        return userService.getAllUsers().stream()
                .flatMap(user -> user.petsList().stream())
                .filter(pet -> pet.id().equals(id))
                .findAny();
    }


    public void deletePetById(Long id) {
        var pet = getPetById(id).orElseThrow(()-> new IllegalArgumentException("Pet with id %s not found".formatted(id)));
        var user = userService.findUserById(pet.id());

        user.petsList().remove(pet);
    }


    public Pet updatePet(Long id, Pet petToUpdate) {
        var petId = getPetById(id).orElseThrow(()-> new IllegalArgumentException("Pet with id %s not found".formatted(id)));
        var pet = new Pet(
                petId.id(),
                petToUpdate.name(),
                petId.userId()
        );
        userService.findUserById(pet.userId()).petsList().remove(petId);
        userService.findUserById(pet.userId()).petsList().add(pet);
        return pet;
    }

}
