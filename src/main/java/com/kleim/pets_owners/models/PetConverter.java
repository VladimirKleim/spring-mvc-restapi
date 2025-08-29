package com.kleim.pets_owners.models;

import org.springframework.stereotype.Component;

@Component

public class PetConverter {
    public Pet toPet(PetDTO petDTO) {
        return new Pet(
                petDTO.id(),
                petDTO.name(),
                petDTO.userId()
        );
    }
    public PetDTO toPetDTO(Pet pet) {
        return new PetDTO(
                pet.id(),
                pet.name(),
                pet.userId()
        );
    }
}
