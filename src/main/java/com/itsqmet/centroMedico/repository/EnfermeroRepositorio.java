package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Enfermero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnfermeroRepositorio extends MongoRepository<Enfermero, String> {
    List<Enfermero> findByNombreContainingIgnoreCase (String nombre);
}
