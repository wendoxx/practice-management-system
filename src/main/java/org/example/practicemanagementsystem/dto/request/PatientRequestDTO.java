package org.example.practicemanagementsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientRequestDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
