package com.kleim.pets_owners.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;


public record PetDTO(

        Long id,

        @NotBlank
        @Size(min = 1, max = 20, message = "Name shouldn't by empty")
        String name,

        Long userId
) {
}
