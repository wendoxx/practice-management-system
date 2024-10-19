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
    public String findDoctorByName(){
        return "ByName";
    }

    @GetMapping("/{id}")
    public String findDoctorById() {
        return "Doctor with id ";
    }

    @PostMapping
    public String createDoctor() {
        return "create";
    }

    @PutMapping
    public String updateDoctor() {
        return "Doctor updated";
    }

    @DeleteMapping("/{id}")
    public String deleteDoctor() {
        return "Doctor deleted";
    }
}
