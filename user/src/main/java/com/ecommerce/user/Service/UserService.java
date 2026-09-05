package com.app.ecom.Service;

import com.app.ecom.Entity.Address;
import com.app.ecom.Entity.User;
import com.app.ecom.Repository.UserRepository;
import com.app.ecom.dto.AddressDto;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
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


    public boolean updateUser(UserRequest updatedUserRequest, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    public Optional<UserResponse> fetchUser(Long id) {
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
        response.setId(user.getId().toString());
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
