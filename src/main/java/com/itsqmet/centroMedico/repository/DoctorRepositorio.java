package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Enfermero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepositorio extends JpaRepository<Doctor, Long> {
    List<Doctor> findByNombreContainingIgnoreCase (String nombre);
    Optional<Doctor> findByCorreoAndContrasenia(String correo, String contrasenia);
}
