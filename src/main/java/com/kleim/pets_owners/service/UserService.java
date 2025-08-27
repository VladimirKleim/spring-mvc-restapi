package com.kleim.pets_owners.service;



import com.kleim.pets_owners.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Long idCounter;
    private final Map<Long, User> userMap;

    public UserService() {
        this.userMap = new HashMap<>();
        this.idCounter = 0L;
    }

    public User createUser(User userToCreate) {
       var userId = ++idCounter;
        User user = new User(
                userId,
                userToCreate.name(),
                userToCreate.email(),
                userToCreate.age()
//                new ArrayList<>()
        );
        userMap.put(userId,user);
        return user;
    }
}
