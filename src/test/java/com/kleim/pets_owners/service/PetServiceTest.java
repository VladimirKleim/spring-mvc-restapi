package com.kleim.pets_owners.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kleim.pets_owners.models.pet.Pet;
import com.kleim.pets_owners.models.pet.PetDTO;
import com.kleim.pets_owners.models.user.User;
import com.kleim.pets_owners.models.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldCreatePet() throws Exception {
        var user = userService.createUser(new User(
                null,
                "some-body",
                "some-body@mail.ru",
                30,
                new ArrayList<>()
        ));
        PetDTO pet = new PetDTO(
                null,
                "Puppy-Muppy",
                user.id()
        );

        String petJson = objectMapper.writeValueAsString(pet);

        String petToJson = mockMvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON).content(petJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        PetDTO petsResponse = objectMapper.readValue(petToJson, PetDTO.class);
        Assertions.assertEquals(pet.name(), petsResponse.name());
        Assertions.assertEquals(pet.userId(), user.id());
        Assertions.assertNotNull(petsResponse.userId());
    }


    @Test
    void shouldFoundPetById() throws Exception {
        var user = userService.createUser(new User(
                null,
                "some-body",
                "some-body@mail.ru",
                30,
                new ArrayList<>()
        ));
        Pet pet = new Pet(
                null,
                "some-body",
                user.id()
        );
        pet = petService.createPet(pet);
        String petToJson = mockMvc.perform(get("/pets/{id}", pet.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var petDtoResponse = objectMapper.readValue(petToJson, Pet.class);
        org.assertj.core.api.Assertions.assertThat(pet).usingRecursiveComparison().isEqualTo(petDtoResponse);
    }

}