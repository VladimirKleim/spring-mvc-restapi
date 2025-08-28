package com.kleim.pets_owners.models;

import java.util.List;

public record UserDTO (
        Long id,
        String name,
        String email,
        Integer age,
        List<PetDTO> petsList
){
}
