package org.example.practicemanagementsystem.controller;

import org.example.practicemanagementsystem.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public String findById(){
        return "find by id";
    }

    @GetMapping
    public String findByName(){
        return "find by name";
    }

    @GetMapping("/all")
    public String findAll(){
        return "find all";
    }

}
