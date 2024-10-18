package org.example.practicemanagementsystem.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @GetMapping("/all")
    public String findAllDoctors() {
        return "All doctors";
    }

    @GetMapping("/{id}")
    public String findDoctorById() {
        return "Doctor with id ";
    }

    @PostMapping
    public String createDoctor() {
        return "Doctor created";
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
