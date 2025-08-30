package com.kleim.pets_owners.service;



import com.kleim.pets_owners.models.user.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final AtomicLong atomicInteger;
    private final Map<Long, User> userMap;

    public UserService() {
        this.atomicInteger = new AtomicLong();
        this.userMap = new HashMap<>();
    }


    public User createUser(User userToCreate) {
        if (userToCreate.id() != null) {
            throw new IllegalArgumentException("User id not should be provided");
        }
        if (userToCreate.petsList() != null && !userToCreate.petsList().isEmpty()) {
            throw new IllegalArgumentException("User pets must be empty");
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
        if  (!userMap.containsKey(id)) {
            throw new NoSuchElementException("No such user with id=%s".formatted(id));
        }
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
        return new User(
                id,
                userToUpdate.name(),
                userToUpdate.email(),
                userToUpdate.age(),
                new ArrayList<>()
        );
    }

}
