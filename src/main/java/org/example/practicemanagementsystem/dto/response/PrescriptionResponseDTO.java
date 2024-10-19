package org.example.practicemanagementsystem.dto.response;

import lombok.Data;

@Data
public class PrescriptionResponseDTO {
    private Long id;
    private PatientResponseDTO patient;
    private DoctorResponseDTO doctor;
    private AppointmentResponseDTO appointment;
    private String content;
}
//criar os services e ver se será necessário implementar um mapper