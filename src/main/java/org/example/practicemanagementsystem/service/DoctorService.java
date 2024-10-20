package org.example.practicemanagementsystem.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.practicemanagementsystem.dto.request.DoctorRequestDTO;
import org.example.practicemanagementsystem.dto.response.DoctorResponseDTO;
import org.example.practicemanagementsystem.model.DoctorModel;
import org.example.practicemanagementsystem.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    private final ModelMapper modelMapper;

    private static final Logger LOGGER = LogManager.getLogger(DoctorService.class);

    //This method creates and saves a doctor
    public DoctorResponseDTO createAndUpdateDoctor(DoctorRequestDTO doctorRequestDTO) {
        DoctorModel doctor;

        if(doctorRequestDTO.getId() != null && doctorRepository.existsById(doctorRequestDTO.getId())) {
            LOGGER.info("Updating Doctor...");
            doctor = doctorRepository.findById(doctorRequestDTO.getId()).get();
            LOGGER.info("Doctor updated!");
        } else {
            doctor = new DoctorModel();
        }

        //TODO: add method to validate email.
        
        doctor.setName(doctorRequestDTO.getName());
        doctor.setEmail(doctorRequestDTO.getEmail());
        doctor.setPhone(doctorRequestDTO.getPhone());
        doctor.setSpecialization(doctorRequestDTO.getSpecialization());

        return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDTO.class);
    }

    public DoctorResponseDTO saveDoctor(DoctorRequestDTO doctorRequestDTO) {
        return createAndUpdateDoctor(doctorRequestDTO);
    }

    public DoctorResponseDTO updateDoctor(DoctorRequestDTO doctorRequestDTO) {
        return createAndUpdateDoctor(doctorRequestDTO);
    }

    //This method lists all doctors
    public List<DoctorResponseDTO> getAllDoctors(){

        if(doctorRepository.findAll().isEmpty()){
            LOGGER.error("No doctors found.");
            throw new RuntimeException("No doctors found.");
        }

        return doctorRepository.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class)).toList();
    }

    //This method list find a doctor by id
    public DoctorResponseDTO getDoctorById(Long id){
        LOGGER.info("Getting doctor...");
        return modelMapper.map(doctorRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Doctor not found.");
                    return new RuntimeException("Doctor not found. Please, check the id.");
                }), DoctorResponseDTO.class);
    }

    public DoctorResponseDTO getDoctorByName(String name) {
        LOGGER.info("Getting doctor...");
        DoctorModel doctor = modelMapper.map(doctorRepository.findByName(name), DoctorModel.class);

        if (doctor == null) {
            throw new RuntimeException("Doctor not found.");
        }

        return modelMapper.map(doctor, DoctorResponseDTO.class);

    }

    public void deleteDoctorById(Long id){
        LOGGER.info("Deleting doctor...");
        if (doctorRepository.findById(id).isEmpty()){
            LOGGER.error("Doctor not found.");
            throw new RuntimeException("Doctor not found. Check the id.");
        }
        doctorRepository.deleteById(id);
    }
}
