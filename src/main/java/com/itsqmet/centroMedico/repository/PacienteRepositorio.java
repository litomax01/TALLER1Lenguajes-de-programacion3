package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Enfermero;
import com.itsqmet.centroMedico.entity.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepositorio extends MongoRepository<Paciente, String> {
    List<Paciente> findByNombreContainingIgnoreCase (String nombre);
}
