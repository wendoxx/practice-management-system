package org.example.practicemanagementsystem.service;

import lombok.AllArgsConstructor;
import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
import org.example.practicemanagementsystem.dto.response.PatientResponseDTO;
import org.example.practicemanagementsystem.mapper.Mapper;
import org.example.practicemanagementsystem.model.PatientModel;
import org.example.practicemanagementsystem.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private final ModelMapper modelMapper;

    // this method is used to create and update patient
    public PatientResponseDTO createAndUpdatePatient(PatientRequestDTO patientRequestDTO){
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

    // this method is used to get patient by id
    public PatientResponseDTO getPatientById(Long id) {
        return modelMapper.map(patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found")), PatientResponseDTO.class);
    }

}
