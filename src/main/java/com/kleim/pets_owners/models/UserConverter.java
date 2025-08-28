package com.kleim.pets_owners.models;

import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toUser(UserDTO userDTO) {
      User user =  new User(
                userDTO.id(),
                userDTO.name(),
                userDTO.email(),
                userDTO.age(),
                userDTO.petsList()
        );
      return user;
    }

    public UserDTO toDtoUser(User user) {
      UserDTO userDTO = new UserDTO(user.id(),
        user.name(),
        user.email(),
        user.age(),
        user.petsList()
      );
      return userDTO;
    }
}
