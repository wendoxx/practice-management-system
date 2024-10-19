package org.example.practicemanagementsystem.controller;

import org.example.practicemanagementsystem.dto.request.DoctorRequestDTO;
import org.example.practicemanagementsystem.dto.response.DoctorResponseDTO;
import org.example.practicemanagementsystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDTO>> findAllDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors().stream().toList());
    }
    @GetMapping("/name")
    public ResponseEntity<DoctorResponseDTO> findDoctorByName(@RequestParam String name){
        return ResponseEntity.ok(doctorService.getDoctorByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> findDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        return ResponseEntity.status(201).body(doctorService.saveDoctor(doctorRequestDTO));
    }

    @PutMapping
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        return ResponseEntity.status(201).body(doctorService.updateDoctor(doctorRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }
}
