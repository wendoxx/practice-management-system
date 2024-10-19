package org.example.practicemanagementsystem.repository;

import org.example.practicemanagementsystem.model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel, Long> {
    Optional<DoctorModel> findByName(String name);
}
