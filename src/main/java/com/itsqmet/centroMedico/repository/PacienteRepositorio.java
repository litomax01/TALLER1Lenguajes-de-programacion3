package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Enfermero;
import com.itsqmet.centroMedico.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {
    List<Paciente> findByNombreContainingIgnoreCase (String nombre);
    Optional<Paciente> findByCorreoAndContrasenia(String correo, String contrasenia);
    Optional<Paciente> findById(Long id);
}
