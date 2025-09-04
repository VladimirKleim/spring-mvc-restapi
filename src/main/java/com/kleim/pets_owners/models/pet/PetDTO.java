package com.kleim.pets_owners.models.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record PetDTO(

        Long id,

        @NotBlank
        @Size(min = 1, max = 20, message = "Name shouldn't by empty")
        String name,

        Long userId
) {
}
