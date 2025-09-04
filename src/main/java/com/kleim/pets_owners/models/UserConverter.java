package com.kleim.pets_owners.models;

import com.kleim.pets_owners.models.user.User;
import com.kleim.pets_owners.models.user.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public final PetConverter petConverter;

    public UserConverter(PetConverter petConverter) {
        this.petConverter = petConverter;
    }


    public User toUser(UserDTO userDTO) {
        return new User(
                  userDTO.id(),
                  userDTO.name(),
                  userDTO.email(),
                  userDTO.age(),
                  userDTO.petsList().stream().map(petConverter::toPet).toList()
          );
    }


    public UserDTO toDtoUser(User user) {
        return new UserDTO(user.id(),
          user.name(),
          user.email(),
          user.age(),
          user.petsList().stream().map(petConverter::toPetDTO).toList()
        );
    }

}
