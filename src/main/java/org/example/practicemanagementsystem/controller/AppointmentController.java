package org.example.practicemanagementsystem.controller;

import org.example.practicemanagementsystem.dto.request.AppointmentRequestDTO;
import org.example.practicemanagementsystem.dto.request.DoctorRequestDTO;
import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
import org.example.practicemanagementsystem.dto.response.AppointmentResponseDTO;
import org.example.practicemanagementsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.findAppointmentById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAllAppointments());
    }

    @GetMapping("/by-doctor")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        return ResponseEntity.ok(appointmentService.findAppointmentsByDoctor(doctorRequestDTO));
    }

    @GetMapping("/by-patient")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentByPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.ok(appointmentService.findAppointmentsByPatient(patientRequestDTO));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return ResponseEntity.status(201).body(appointmentService.saveAppointment(appointmentRequestDTO));
    }

    @PutMapping
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return ResponseEntity.status(201).body(appointmentService.updateAppointment(appointmentRequestDTO));
    }

    @DeleteMapping("/delete-appointment/{id}")
    public ResponseEntity<AppointmentResponseDTO> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
