package org.example.practicemanagementsystem.dto.response;

import lombok.Data;

@Data
public class PatientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
