package com.kleim.pets_owners.models.user;

import com.kleim.pets_owners.models.pet.Pet;

import java.util.List;

public record User(

        Long id,
        String name,
        String email,
        Integer age,
        List<Pet> petsList

) {
}
