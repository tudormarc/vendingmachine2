package com.tudormarc.vendingmachine2.service;

import com.tudormarc.vendingmachine2.dao.UserRepository;
import com.tudormarc.vendingmachine2.domain.User;
import com.tudormarc.vendingmachine2.errors.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User createUser) {
        User user = userRepository.addUser(createUser);
        return user;
    }

    public User getUser(String id) {
        Optional<User> user = userRepository.getUser(id);

        return user.orElseThrow(() -> new BadRequestException("User not found: " + id));
    }

    public User updateUser(User updateUser) {
        User dbUser = userRepository.getUser(updateUser.getUsername()).get();
        updateUser.setDeposit(dbUser.getDeposit());
        updateUser = userRepository.updateUser(updateUser);

        updateUser.setRoleChanged(dbUser.getRole() != updateUser.getRole());

        return updateUser;
    }

    public void deleteUser(String id) {
        userRepository.deleteUser(id);
    }

    public User deposit(BigDecimal amount, String userId) {
        User dbUser = userRepository.getUser(userId).get();
        dbUser.setDeposit(dbUser.getDeposit().add(amount));
        return userRepository.updateUser(dbUser);
    }

    public User reset(String id, BigDecimal amount) {
        User dbUser = userRepository.getUser(id).get();
        dbUser.setDeposit(amount);
        return userRepository.updateUser(dbUser);

    }
}
