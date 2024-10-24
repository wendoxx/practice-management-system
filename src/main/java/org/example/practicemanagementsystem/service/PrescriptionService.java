package org.example.practicemanagementsystem.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
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

    @Transactional
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

        DoctorModel doctor = (doctorRepository.findById(prescriptionRequestDTO.getDoctor())).orElseThrow(() -> {
            LOGGER.error("Doctor not found.");
            return new RuntimeException("Doctor not found.");

        });
        prescription.setDoctor(doctor);

        PatientModel patient = (patientRepository.findById(prescriptionRequestDTO.getPatient())).orElseThrow(() -> {
            LOGGER.error("Patient not found");
            return new RuntimeException("Patient not found.");
        });
        prescription.setPatient(patient);
        prescription.setContent(prescriptionRequestDTO.getContent());

        prescription = prescriptionRepository.save(prescription);
        return modelMapper.map(prescription, PrescriptionResponseDTO.class);
    }

    @Transactional
    public PrescriptionResponseDTO savePrescription(PrescriptionRequestDTO prescriptionRequestDTO) {
        return saveAndUpdatePrescription(prescriptionRequestDTO);
    }

    @Transactional
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
    //TODO: do this method return patient, appointment and prescription in JSON.
    public List<PrescriptionResponseDTO> findAllByPatient(PatientRequestDTO patientRequestDTO) {
        PatientModel patient = patientRepository.findById(patientRequestDTO.getId()).orElseThrow(() -> {
            LOGGER.error("Patient not found.");
            return new RuntimeException("Patient not found");
        });
        Optional<PrescriptionModel> prescriptions = prescriptionRepository.findByPatient(patient);

        return prescriptions.stream().map(prescription -> modelMapper.map(patient, PrescriptionResponseDTO.class)).toList();
    }
    //TODO: do this method return patient, appointment and prescription in JSON.
    public List<PrescriptionResponseDTO> findAllByDoctor(DoctorRequestDTO doctorRequestDTO) {
        DoctorModel doctor = doctorRepository.findById(doctorRequestDTO.getId()).orElseThrow(() -> {
            LOGGER.error("Doctor not found.");
            return new RuntimeException("Doctor not found.");
        });

        Optional<PrescriptionModel> prescriptions = prescriptionRepository.findByDoctor(doctor);

        return prescriptions.stream().map(prescription -> modelMapper.map(doctor, PrescriptionResponseDTO.class)).toList();
    }

    public void deletePrescriptionById(Long id) {
        LOGGER.info("Deleting prescription...");
        prescriptionRepository.deleteById(id);
    }
}
