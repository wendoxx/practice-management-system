package org.example.practicemanagementsystem.dto.request;

import lombok.Data;

@Data
public class PrescriptionRequestDTO {
    private Long id;
    private Long doctor;
    private Long patient;
    private Long appointment;
    private String content;
}
