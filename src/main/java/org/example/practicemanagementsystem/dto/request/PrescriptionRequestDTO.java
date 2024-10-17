package org.example.practicemanagementsystem.dto.request;

import lombok.Data;

@Data
public class PrescriptionRequestDTO {
    private Long id;
    private Long patient;
    private Long doctor;
    private Long appointment;
}
