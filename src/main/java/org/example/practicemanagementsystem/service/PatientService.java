package org.example.practicemanagementsystem.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
import org.example.practicemanagementsystem.dto.response.PatientResponseDTO;
import org.example.practicemanagementsystem.model.PatientModel;
import org.example.practicemanagementsystem.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private final ModelMapper modelMapper;

    private static final Logger LOGGER = LogManager.getLogger(PatientService.class);

    // this method is used to create and update patient
    public PatientResponseDTO createAndUpdatePatient(PatientRequestDTO patientRequestDTO) {
        PatientModel patient;

        if(patientRequestDTO.getId() != null && patientRepository.existsById(patientRequestDTO.getId())){
            patient = patientRepository.findById(patientRequestDTO.getId()).get();
        } else {
            patient = new PatientModel();
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setPhone(patientRequestDTO.getPhone());
        patient.setAddress(patientRequestDTO.getAddress());

        return modelMapper.map(patientRepository.save(patient), PatientResponseDTO.class);
    }

    public PatientResponseDTO savePatient(PatientRequestDTO patientRequestDTO) {
        return createAndUpdatePatient(patientRequestDTO);
    }

    public PatientResponseDTO updatePatient(PatientRequestDTO patientRequestDTO) {
        if (patientRequestDTO.getId() == null) {
            LOGGER.error("Patient id is required");
            throw new RuntimeException("Patient id is required");
        }
        return createAndUpdatePatient(patientRequestDTO);
    }

    // this method is used to get patient by id
    public PatientResponseDTO getPatientById(Long id) {
        return modelMapper.map(patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found")), PatientResponseDTO.class);
    }

    // this method is used to get all patients
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDTO.class))
                .toList();
    }

    // this method is used to delete patient by id
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
