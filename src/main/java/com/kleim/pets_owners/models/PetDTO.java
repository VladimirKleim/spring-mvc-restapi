package com.kleim.pets_owners.models;

public record PetDTO(
        Long id,
        String name,
        Long userId
) {
}
