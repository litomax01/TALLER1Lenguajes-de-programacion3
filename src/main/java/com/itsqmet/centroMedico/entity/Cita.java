package com.itsqmet.centroMedico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de cita es obligatoria")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fecha;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 1000, message = "La dirección no puede exceder los 1000 caracteres")
    private String motivo;

    //relacion con paciente
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    //relacion con enfermero
    @ManyToOne
    @JoinColumn(name = "id_enfermero")
    private Enfermero enfermero;

    //relacion con farmaceutico
    @ManyToOne
    @JoinColumn(name = "id_farmaceutico")
    private Farmaceutico farmaceutico;

    //relacion con doctor
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    public Cita() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fechaCita) {
        this.fecha = fechaCita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Enfermero getEnfermero() {
        return enfermero;
    }

    public void setEnfermero(Enfermero enfermero) {
        this.enfermero = enfermero;
    }

    public Farmaceutico getFarmaceutico() {
        return farmaceutico;
    }

    public void setFarmaceutico(Farmaceutico farmaceutico) {
        this.farmaceutico = farmaceutico;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
