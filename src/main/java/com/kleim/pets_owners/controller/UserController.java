package com.kleim.pets_owners.controller;

import com.kleim.pets_owners.models.User;
import com.kleim.pets_owners.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService = new UserService();

    @PostMapping("/users")
    private ResponseEntity<User> createUser(
            @RequestBody User userToCreate
    ) {

      log.info("User created successfully.");
      var createdUser = userService.createUser(userToCreate);

      return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}






































