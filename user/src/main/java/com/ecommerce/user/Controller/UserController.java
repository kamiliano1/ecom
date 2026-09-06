package com.ecommerce.user.Controller;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<List<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<UserResponse>> updateUser(@PathVariable String id,
                                                         @RequestBody UserRequest updateUserRequest) {
        boolean updated = userService.updateUser(updateUserRequest, id);
        if (updated) {
            return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.NOT_FOUND);
    }

}
