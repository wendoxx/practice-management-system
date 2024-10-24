package org.example.practicemanagementsystem.repository;

import org.example.practicemanagementsystem.model.AppointmentModel;
import org.example.practicemanagementsystem.model.DoctorModel;
import org.example.practicemanagementsystem.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {
    Optional<AppointmentModel> findAllByDoctor(DoctorModel doctorModel);
    Optional<AppointmentModel> findAllByPatient(PatientModel patientModel);
}
