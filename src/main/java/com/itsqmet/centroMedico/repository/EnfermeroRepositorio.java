package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Enfermero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnfermeroRepositorio extends JpaRepository<Enfermero, String> {
    List<Enfermero> findByNombreContainingIgnoreCase (String nombre);
    Optional<Enfermero> findByCorreoAndContrasenia(String correo, String contrasenia);
}
