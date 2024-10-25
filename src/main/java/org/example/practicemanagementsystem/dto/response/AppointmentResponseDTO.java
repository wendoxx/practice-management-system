package org.example.practicemanagementsystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private DoctorResponseDTO doctor;
    private PatientResponseDTO patient;
    private LocalDateTime date;
    private String description;
}
