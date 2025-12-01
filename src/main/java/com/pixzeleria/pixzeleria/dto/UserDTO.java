package com.pixzeleria.pixzeleria.dto;

import com.pixzeleria.pixzeleria.model.user.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}