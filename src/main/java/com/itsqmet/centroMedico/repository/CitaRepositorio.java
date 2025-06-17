package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Cita;
import com.itsqmet.centroMedico.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepositorio extends JpaRepository<Cita, Long> {

    List<Cita> findByFecha(Date fechaCita);
    Optional<Cita> findById(Long id);
}
