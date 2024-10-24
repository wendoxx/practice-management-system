package org.example.practicemanagementsystem.service;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.practicemanagementsystem.dto.request.AppointmentRequestDTO;
import org.example.practicemanagementsystem.dto.request.DoctorRequestDTO;
import org.example.practicemanagementsystem.dto.request.PatientRequestDTO;
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

import java.util.List;
import java.util.Optional;

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
            LOGGER.error("Patient not found.");
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

    public List<AppointmentResponseDTO> findAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class)).toList();
    }

    public List<AppointmentResponseDTO> findAppointmentsByDoctor(DoctorRequestDTO doctorRequestDTO) {
        LOGGER.info("Finding appointment...");
        DoctorModel doctor = doctorRepository.findById(doctorRequestDTO.getId()).orElseThrow(() -> {
           LOGGER.error("Doctor not found.");
           return new RuntimeException("Doctor not found.");
        });

        Optional<AppointmentModel> appointments = appointmentRepository.findAllByDoctor(doctor);
        return appointments.stream().map(appointment -> modelMapper.map(doctor, AppointmentResponseDTO.class)).toList();
    }

    public List<AppointmentResponseDTO> findAppointmentsByPatient(PatientRequestDTO patientRequestDTO) {
        LOGGER.info("Finding appointment...");
        PatientModel patient = patientRepository.findById(patientRequestDTO.getId()).orElseThrow(() -> {
            LOGGER.error("Patient not found.");
            return new RuntimeException("Patient not found");
        });

        Optional<AppointmentModel> appointments = appointmentRepository.findAllByPatient(patient);
        return appointments.stream().map(appointment -> modelMapper.map(patient, AppointmentResponseDTO.class)).toList();
    }

    public AppointmentResponseDTO findAppointmentById(Long id) {
        return modelMapper.map(appointmentRepository.findById(id).orElseThrow(() -> {
         LOGGER.error("Appointment not found.");
         return new RuntimeException("Appointment not found");
        }), AppointmentResponseDTO.class);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

}
