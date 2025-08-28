package com.kleim.pets_owners.service;



import com.kleim.pets_owners.models.User;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private AtomicLong atomicInteger = new AtomicLong();
    private final Map<Long, User> userMap;

    public UserService() {
        this.userMap = new HashMap<>();
    }

    public User createUser(User userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("User id not should be provided");
        }
       var userId = atomicInteger.incrementAndGet();
        User user = new User(
                userId,
                userToCreate.name(),
                userToCreate.email(),
                userToCreate.age(),
                new ArrayList<>()
        );
        userMap.put(userId,user);
        return user;
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }

    public User findUserById(Long id) {
        return userMap.get(id);
    }

    public void deleteUser(Long id) {
        var userId = userMap.remove(id);
        if (userId == null) {
            throw new NoSuchElementException("Not found user");
        }
    }

    public User updateUser(Long id, User userToUpdate) {
        var userId = userMap.get(id);
        if (userId == null) {
            throw new NoSuchElementException("Not found user with id: %s".formatted(userId));
        }
        var newUser = new User(
                id,
                userToUpdate.name(),
                userToUpdate.email(),
                userToUpdate.age(),
                userToUpdate.petsList()
        );
        return newUser;
    }
}
