package org.example.practicemanagementsystem.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {
    private Long id;
    private Long doctor;
    private Long patient;
    private LocalDateTime dateAndHour;
}
