package com.kleim.pets_owners.models.user;

import com.kleim.pets_owners.models.pet.PetDTO;
import jakarta.validation.constraints.*;

import java.util.List;

public record UserDTO (

        Long id,

        @NotBlank
        @Size(min = 4, message = "Name shouldn't by empty")
        String name,

        @Email
        @Size(min = 0, max = 255)
        String email,

        @NotNull
        @Min(value = 18, message = "Access denied")
        Integer age,

        List<PetDTO> petsList

){
}
