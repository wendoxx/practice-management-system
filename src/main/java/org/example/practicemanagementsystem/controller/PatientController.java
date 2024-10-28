package org.example.practicemanagementsystem.controller;

import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
import org.example.practicemanagementsystem.dto.response.PatientResponseDTO;
import org.example.practicemanagementsystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponseDTO>> findAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> findPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/by-name")
    public ResponseEntity<PatientResponseDTO> findPatientByName(@RequestBody String name) {
        return ResponseEntity.ok(patientService.getPatientByName(name));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.status(201).body(patientService.savePatient(patientRequestDTO));
    }

    @PutMapping
    public ResponseEntity<PatientResponseDTO> updatePatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.ok(patientService.updatePatient(patientRequestDTO));
    }

    @DeleteMapping("/delete-patient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
