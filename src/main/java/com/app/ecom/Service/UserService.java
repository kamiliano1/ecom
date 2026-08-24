package com.app.ecom.Service;

import com.app.ecom.Entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private Long id = 1L;

    public List<User> fetchAllUsers() {
        return users;
    }

    public List<User> addUser(User user) {
        user.setId(id++);
        users.add(user);
        return users;
    }

    public boolean updateUser(User updatedUser, Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }

    public Optional<User> fetchUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}
