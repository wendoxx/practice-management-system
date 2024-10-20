package org.example.practicemanagementsystem.repository;

import org.example.practicemanagementsystem.model.PrescriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionModel, Long> {
}
