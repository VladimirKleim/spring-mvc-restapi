package com.kleim.pets_owners.controller;

import com.kleim.pets_owners.models.PetConverter;
import com.kleim.pets_owners.models.PetDTO;
import com.kleim.pets_owners.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/pets")
@RestController
public class PetController {

    private final PetService petService;
    private final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetConverter petConverter;

    public PetController(PetConverter petConverter, PetService petService) {
        this.petConverter = petConverter;
        this.petService = petService;

    }


    @PostMapping
    public ResponseEntity<PetDTO> createPet(
           @RequestBody @Valid PetDTO petToCreate
    ) {
        log.info("...");
        var createdPet = petService.createPet(petConverter.toPet(petToCreate));

        return ResponseEntity.ok(petConverter.toPetDTO(createdPet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(
        @PathVariable("id") Long id
    ) {
        log.info("..."); // TODO
        var petById = petService.getPet(id);
        return ResponseEntity.ok(petConverter.toPetDTO(petById));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<PetDTO> deletePetById(
          @PathVariable("id")  Long id
    ) {
        log.info("...");
        petService.deletePetById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}")
    public ResponseEntity<PetDTO> updatePet(
          @PathVariable("id")  Long id,
          @RequestBody @Valid PetDTO petDTO
    ) {
        log.info("...");
        var pet = petService.updatePet(id, petConverter.toPet(petDTO));
        return ResponseEntity.ok(petConverter.toPetDTO(pet));
    }
}
