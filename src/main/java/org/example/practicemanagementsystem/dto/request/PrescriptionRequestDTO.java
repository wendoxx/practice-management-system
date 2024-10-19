package org.example.practicemanagementsystem.dto.request;

import lombok.Data;
import org.example.practicemanagementsystem.dto.response.AppointmentResponseDTO;
import org.example.practicemanagementsystem.dto.response.DoctorResponseDTO;
import org.example.practicemanagementsystem.dto.response.PatientResponseDTO;

@Data
public class PrescriptionRequestDTO {
    private Long id;
    private PatientResponseDTO patient;
    private DoctorResponseDTO doctor;
    private AppointmentResponseDTO appointment;
    private String content;
}
