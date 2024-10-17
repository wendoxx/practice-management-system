package org.example.practicemanagementsystem.dto.response;

import lombok.Data;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private String doctor;
    private String patient;
    private String dateAndHour;
    private String description;
}
