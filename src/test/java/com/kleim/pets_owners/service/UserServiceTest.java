package com.kleim.pets_owners.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kleim.pets_owners.models.user.User;
import com.kleim.pets_owners.models.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService userService = new UserService();

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldCreateUser() throws Exception {
        var userDTO = new UserDTO(
                null,
                "some-booka",
                "kitkatorgnew@gmail.com",
                18,
                new ArrayList<>()
        );
        String userJson = objectMapper.writeValueAsString(userDTO);

        String createdUserJson = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO userDTOResponse = objectMapper.readValue(createdUserJson, UserDTO.class);
        Assertions.assertEquals(userDTO.name(), userDTOResponse.name());
        Assertions.assertEquals(userDTOResponse.email(), userDTOResponse.email());
        Assertions.assertEquals(userDTOResponse.age(), userDTOResponse.age());
    }


    @Test
    void shouldFoundUserById() throws Exception {
        var user = new User(null,
                "some-booka",
                "kitkatorgnew@gmail.com",
                18,
                List.of());
        user = userService.createUser(user);
        String findUserJson = mockMvc.perform(get("/users/{id}", user.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var userJson = objectMapper.readValue(findUserJson, User.class);
        org.assertj.core.api.Assertions.assertThat(user).usingRecursiveComparison().isEqualTo(userJson);
    }


    @Test
    void deleteUser() throws Exception {
        var user = new User(
                null,
                "somy-body",
                "kitkatorgnew@gmail.com",
                21,
                List.of()
        );
        String userJson = objectMapper.writeValueAsString(user);
        String deleteUserJsonMax = mockMvc.perform(delete("/users/{id}", 0)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertTrue(deleteUserJsonMax.toLowerCase().contains("not found") || deleteUserJsonMax.toLowerCase().contains("error"));

    }


    @Test
    void updateUserValidSuccess() throws Exception {
        var user = new User(
                1L,
                "somy-body",
                "kitkatorgnew@gmail.com",
                21,
                List.of()
        );
        String userJson = objectMapper.writeValueAsString(user);
        String deleteUserJson = mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var updatedUser = objectMapper.readValue(deleteUserJson, User.class);
        Assertions.assertEquals(user, updatedUser);
    }

    @Test
    void updateUserValidDataSuccess() throws Exception {
        long userId = 1L;
        var user = new User(
                userId,
                "name",
                "newemail@example.com",
                30,
                List.of());
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.email").value("newemail@example.com"))
                .andExpect(jsonPath("$.age").value(30));
    }
}