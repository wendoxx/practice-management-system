package org.example.practicemanagementsystem.controller;

import org.example.practicemanagementsystem.dto.request.DoctorRequestDTO;
import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
import org.example.practicemanagementsystem.dto.request.PrescriptionRequestDTO;
import org.example.practicemanagementsystem.dto.response.PrescriptionResponseDTO;
import org.example.practicemanagementsystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(prescriptionService.findPrescriptionById(id));
    }

    @GetMapping("/by-patient")
    public ResponseEntity<List<PrescriptionResponseDTO>> getByPatient(@RequestBody PatientRequestDTO patientRequestDTO){
        return ResponseEntity.ok(prescriptionService.findAllByPatient(patientRequestDTO));
    }

    @GetMapping("/by-doctor")
    public ResponseEntity<List<PrescriptionResponseDTO>> getByDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        return ResponseEntity.ok(prescriptionService.findAllByDoctor(doctorRequestDTO));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PrescriptionResponseDTO>> getAll(){
        return ResponseEntity.ok(prescriptionService.findAll());
    }

    @PostMapping
    public ResponseEntity<PrescriptionResponseDTO> savePrescription(@RequestBody PrescriptionRequestDTO prescriptionRequestDTO) {
        return ResponseEntity.status(201).body(prescriptionService.savePrescription(prescriptionRequestDTO));
    }

    @PutMapping
    public ResponseEntity<PrescriptionResponseDTO> updatePrescription(@RequestBody PrescriptionRequestDTO prescriptionRequestDTO) {
        return ResponseEntity.status(201).body(prescriptionService.updatePrescription(prescriptionRequestDTO));
    }

    @DeleteMapping("/delete-prescription/{id}")
    public ResponseEntity<PrescriptionResponseDTO> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescriptionById(id);
        return ResponseEntity.noContent().build();
    }

}
