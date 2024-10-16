package org.example.practicemanagementsystem.repository;

import org.example.practicemanagementsystem.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {
    PatientModel findByName(String name);
}
