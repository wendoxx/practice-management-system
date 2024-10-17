package org.example.practicemanagementsystem.dto.request;

import lombok.Data;

@Data
public class DoctorRequestDTO {
    private Long id;
    private String name;
    private String specialization;
    private String email;
}
