package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Farmaceutico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmaceuticoRepositorio extends MongoRepository<Farmaceutico, String> {
    List<Farmaceutico> findByNombreContainingIgnoreCase (String nombre);
}
