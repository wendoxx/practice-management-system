package org.example.practicemanagementsystem.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.practicemanagementsystem.dto.request.DoctorRequestDTO;
import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
import org.example.practicemanagementsystem.dto.request.PrescriptionRequestDTO;
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

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    private static final Logger LOGGER = LogManager.getLogger(PrescriptionService.class);

    public PrescriptionResponseDTO saveAndUpdatePrescription(PrescriptionRequestDTO prescriptionRequestDTO) {
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

        return modelMapper.map(prescriptionRepository.save(prescription), PrescriptionResponseDTO.class);
    }
    
    public PrescriptionResponseDTO savePrescription(PrescriptionRequestDTO prescriptionRequestDTO) {
        return saveAndUpdatePrescription(prescriptionRequestDTO);
    }

    public PrescriptionResponseDTO updatePrescription(PrescriptionRequestDTO prescriptionRequestDTO) {
        return saveAndUpdatePrescription(prescriptionRequestDTO);
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

    public List<PrescriptionResponseDTO> findAllByPatient(PatientRequestDTO patientRequestDTO) {
        PatientModel patient = patientRepository.findById(patientRequestDTO.getId()).orElseThrow(() -> {
            LOGGER.error("Patient not found.");
            return new RuntimeException("Patient not found");
        });
        return modelMapper.map(prescriptionRepository.findByPatient(patient), List.class);
    }

    public List<PrescriptionResponseDTO> findAllByDoctor(DoctorRequestDTO doctorRequestDTO) {
        DoctorModel doctor = doctorRepository.findById(doctorRequestDTO.getId()).orElseThrow(() -> {
            LOGGER.error("Doctor not found.");
            return new RuntimeException("Doctor not found.");
        });

        return modelMapper.map(prescriptionRepository.findByDoctor(doctor), List.class);
    }
}
