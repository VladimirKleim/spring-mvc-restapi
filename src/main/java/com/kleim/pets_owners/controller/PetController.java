package com.kleim.pets_owners.controller;

import com.kleim.pets_owners.models.Pet;
import com.kleim.pets_owners.models.PetConverter;
import com.kleim.pets_owners.models.PetDTO;
import com.kleim.pets_owners.service.PetService;
import com.kleim.pets_owners.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

    private final PetService petService = new PetService();
    private final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetConverter petConverter;

    public PetController(PetConverter petConverter) {
        this.petConverter = petConverter;
    }


    @GetMapping("/user/pets")
    public ResponseEntity<PetDTO> createPet(
           @RequestBody Pet petToCreate
    ) {
        log.info("..");
        var createdPet = petService.createPet(petToCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(petConverter.toPetDTO(createdPet));
    }
}
