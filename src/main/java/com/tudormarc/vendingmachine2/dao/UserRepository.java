package com.tudormarc.vendingmachine2.dao;

import com.tudormarc.vendingmachine2.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    public User addUser(User user) {
        userMap.put(user.getUsername(), user);
        return user;
    }

    public Optional<User> getUser(String userName) {
        return Optional.ofNullable(userMap.get(userName));
    }

    public User updateUser(User user) {
        userMap.put(user.getUsername(), user);
        return user;
    }

    public void deleteUser(String id) {
        userMap.remove(id);
    }
}
