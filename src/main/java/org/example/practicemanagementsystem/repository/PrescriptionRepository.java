package org.example.practicemanagementsystem.repository;

import org.example.practicemanagementsystem.model.DoctorModel;
import org.example.practicemanagementsystem.model.PatientModel;
import org.example.practicemanagementsystem.model.PrescriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionModel, Long> {
    Optional<PrescriptionModel> findByPatient(PatientModel patient);
    Optional<PrescriptionModel> findByDoctor(DoctorModel doctor);
}
