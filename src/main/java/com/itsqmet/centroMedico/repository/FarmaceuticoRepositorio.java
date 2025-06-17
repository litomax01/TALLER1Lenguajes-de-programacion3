package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Enfermero;
import com.itsqmet.centroMedico.entity.Farmaceutico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmaceuticoRepositorio extends JpaRepository<Farmaceutico, String> {
    List<Farmaceutico> findByNombreContainingIgnoreCase (String nombre);
    Optional<Farmaceutico> findByCorreoAndContrasenia(String correo, String contrasenia);
}
