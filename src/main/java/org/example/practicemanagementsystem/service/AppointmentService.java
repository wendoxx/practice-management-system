package org.example.practicemanagementsystem.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.practicemanagementsystem.dto.request.AppointmentRequestDTO;
import org.example.practicemanagementsystem.dto.response.AppointmentResponseDTO;
import org.example.practicemanagementsystem.model.AppointmentModel;
import org.example.practicemanagementsystem.model.DoctorModel;
import org.example.practicemanagementsystem.model.PatientModel;
import org.example.practicemanagementsystem.repository.AppointmentRepository;
import org.example.practicemanagementsystem.repository.DoctorRepository;
import org.example.practicemanagementsystem.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    private static final Logger LOGGER = LogManager.getLogger(AppointmentService.class);

    public AppointmentResponseDTO saveAndUpdateAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        AppointmentModel appointment;

        if (appointmentRequestDTO.getId() != null && appointmentRepository.existsById(appointmentRequestDTO.getId())) {
            LOGGER.info("Updating appointment...");
            appointment = appointmentRepository.findById(appointmentRequestDTO.getId()).get();
        } else {
            LOGGER.info("Saving new appointment...");
            appointment = new AppointmentModel();
        }

        DoctorModel doctor = doctorRepository.findById(appointmentRequestDTO.getDoctor()).orElseThrow(() -> {
            LOGGER.error("Doctor not found.");
            return new RuntimeException("Doctor not found.");
        });
        appointment.setDoctor(doctor);

        PatientModel patient = patientRepository.findById(appointmentRequestDTO.getPatient()).orElseThrow(() -> {
            LOGGER.info("Patient not found.");
            return new RuntimeException("Patient not found");
        });
        appointment.setPatient(patient);

        appointment = appointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }

    public AppointmentResponseDTO saveAppointment(AppointmentRequestDTO appointmentRequestDTO) {
       return saveAndUpdateAppointment(appointmentRequestDTO);
    }

    public AppointmentResponseDTO updateAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        return saveAndUpdateAppointment(appointmentRequestDTO);
    }
}
