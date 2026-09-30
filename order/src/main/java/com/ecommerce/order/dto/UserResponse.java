package com.ecommerce.order.dto;

import com.ecommerce.order.Entity.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole userRole;
    private AddressDto address;

}
