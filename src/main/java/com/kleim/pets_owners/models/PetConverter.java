package com.kleim.pets_owners.models;

import com.kleim.pets_owners.models.pet.Pet;
import com.kleim.pets_owners.models.pet.PetDTO;
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
