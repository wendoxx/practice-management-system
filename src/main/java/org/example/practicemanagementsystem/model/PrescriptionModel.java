package org.example.practicemanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "tb_prescriptions")
public class PrescriptionModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient")
    private PatientModel patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor")
    private DoctorModel doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment")
    private AppointmentModel appointment;

}
