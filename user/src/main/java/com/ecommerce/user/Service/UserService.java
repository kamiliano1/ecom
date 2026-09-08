package com.ecommerce.user.Service;

import com.ecommerce.user.Entity.Address;
import com.ecommerce.user.Entity.User;
import com.ecommerce.user.Repository.UserRepository;
import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public User addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
        return user;
    }


    public boolean updateUser(UserRequest updatedUserRequest, String id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUserRole(user.getUserRole());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setZipcode(user.getAddress().getZipcode());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setState(user.getAddress().getState());
            response.setAddress(addressDto);
        }
        return response;
    }
}
