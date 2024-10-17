package org.example.practicemanagementsystem.dto.request;

import lombok.Data;

@Data
public class PatientRequestDTO {
    private Long id;
    private String name;
    private String email;
}
