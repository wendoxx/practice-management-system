package org.example.practicemanagementsystem.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.practicemanagementsystem.dto.request.PrescriptionRequestDTO;
import org.example.practicemanagementsystem.dto.response.PatientResponseDTO;
import org.example.practicemanagementsystem.dto.response.PrescriptionResponseDTO;
import org.example.practicemanagementsystem.model.DoctorModel;
import org.example.practicemanagementsystem.model.PatientModel;
import org.example.practicemanagementsystem.model.PrescriptionModel;
import org.example.practicemanagementsystem.repository.DoctorRepository;
import org.example.practicemanagementsystem.repository.PatientRepository;
import org.example.practicemanagementsystem.repository.PrescriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LogManager.getLogger(PrescriptionService.class);

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;


    public PrescriptionModel createPrescription(PrescriptionRequestDTO prescriptionRequestDTO) {
        PrescriptionModel prescription;

        if (prescriptionRequestDTO.getId() != null && prescriptionRepository.existsById(prescriptionRequestDTO.getId())) {
            LOGGER.info("Updating prescription");
            prescription = prescriptionRepository.findById(prescriptionRequestDTO.getId()).get();
            LOGGER.info("Prescription updated!");
        } else {
            LOGGER.info("Saving new prescription...");
            prescription = new PrescriptionModel();
            LOGGER.info("Prescription saved!");
        }

        DoctorModel doctor = (doctorRepository.findById(prescriptionRequestDTO.getId())).orElseThrow(() -> {
            LOGGER.error("Doctor not found.");
            return new RuntimeException("Doctor not found.");
        });

        PatientModel patient = (patientRepository.findById(prescriptionRequestDTO.getId())).orElseThrow(() -> {
            LOGGER.error("Patient not found");
            return new RuntimeException("Patient not found.");
        });

        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setContent(prescriptionRequestDTO.getContent());

        return prescriptionRepository.save(prescription);
    }

    public List<PrescriptionResponseDTO> findAll() {
        LOGGER.info("Finding prescriptions...");
        return prescriptionRepository.findAll()
                .stream()
                .map(prescription -> modelMapper.map(prescription, PrescriptionResponseDTO.class)).toList();
    }

    public PrescriptionResponseDTO findPrescriptionById(Long id) {
        LOGGER.info("Finding prescription...");
        return modelMapper.map(prescriptionRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Prescription not found.");
            return new RuntimeException("Prescription not found.");
        }), PrescriptionResponseDTO.class);
    }

}
