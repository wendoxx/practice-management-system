package org.example.practicemanagementsystem.controller;

import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
import org.example.practicemanagementsystem.dto.request.PrescriptionRequestDTO;
import org.example.practicemanagementsystem.dto.response.PrescriptionResponseDTO;
import org.example.practicemanagementsystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(prescriptionService.findPrescriptionById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<List<PrescriptionResponseDTO>> getByPatient(@RequestBody PatientRequestDTO patientRequestDTO){
        return ResponseEntity.ok(prescriptionService.findAllByPatient(patientRequestDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PrescriptionResponseDTO>> getAll(){
        return ResponseEntity.ok(prescriptionService.findAll());
    }

    @PostMapping
    public ResponseEntity<PrescriptionResponseDTO> savePrescription(@RequestBody PrescriptionRequestDTO patientRequestDTO) {
        return ResponseEntity.status(201).body(prescriptionService.savePrescription(patientRequestDTO));
    }

    @PutMapping
    public ResponseEntity<PrescriptionResponseDTO> updatePrescription(@RequestBody PrescriptionRequestDTO prescriptionRequestDTO) {
        return ResponseEntity.status(201).body(prescriptionService.updatePrescription(prescriptionRequestDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PrescriptionResponseDTO> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescriptionById(id);
        return ResponseEntity.noContent().build();
    }

}
