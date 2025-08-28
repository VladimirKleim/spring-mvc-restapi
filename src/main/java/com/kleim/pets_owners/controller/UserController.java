package com.kleim.pets_owners.controller;


import com.kleim.pets_owners.models.User;
import com.kleim.pets_owners.models.UserConverter;
import com.kleim.pets_owners.models.UserDTO;

import com.kleim.pets_owners.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/users")
@RestController
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();
    private final UserConverter userConverter;

    public UserController(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @PostMapping
    private ResponseEntity<UserDTO> createUser(
            @RequestBody UserDTO userToCreate
    ) {
      log.info("Got request to created user ");
      var createdUser = userService.createUser(userConverter.toUser(userToCreate));

      return ResponseEntity.status(HttpStatus.CREATED).body(userConverter.toDtoUser(createdUser));
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        log.info("Got users list ");
        var getAll = userService.getAllUsers();
        return getAll;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("id") Long id
    ) {
        log.info("Got request to find user by id ");
       var getUser = userService.findUserById(id);

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(userConverter.toDtoUser(getUser));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
         @PathVariable("id") Long id
    ) {
        log.info("Got request to delete user by id ");
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserDTO userToUpdate
    ) {
        log.info("Got request to update user");
        var updatedUser = userService.updateUser(id, userConverter.toUser(userToUpdate));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userConverter.toDtoUser(updatedUser));
    }
}






































