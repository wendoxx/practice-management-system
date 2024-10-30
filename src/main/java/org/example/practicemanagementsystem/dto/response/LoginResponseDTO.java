package org.example.practicemanagementsystem.dto.response;

import lombok.Data;

@Data

public class LoginResponseDTO{
    private String token;

    public LoginResponseDTO(String token){
        this.token = token;
    }
}
