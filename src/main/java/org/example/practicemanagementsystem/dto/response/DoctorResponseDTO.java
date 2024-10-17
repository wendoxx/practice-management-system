package org.example.practicemanagementsystem.dto.response;

import lombok.Data;

@Data
public class DoctorResponseDTO {
    private Long id;
    private String name;
    private String specialization;
    private String email;
}
