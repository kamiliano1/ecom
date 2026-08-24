package com.app.ecom.Controller;

import com.app.ecom.Entity.User;
import com.app.ecom.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/api/users")
    public ResponseEntity<List<User>> createUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<List<User>> updateUser(@PathVariable Long id,
                                                 @RequestBody User user) {
        boolean updated = userService.updateUser(user, id);
        if (updated) {
            return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.NOT_FOUND);
    }

}
