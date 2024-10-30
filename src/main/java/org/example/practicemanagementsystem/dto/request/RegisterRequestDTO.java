package org.example.practicemanagementsystem.dto.request;

import lombok.Data;
import org.example.practicemanagementsystem.role.UserRole;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private UserRole role;
}
