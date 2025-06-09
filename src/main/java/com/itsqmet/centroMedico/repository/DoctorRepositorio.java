package com.itsqmet.centroMedico.repository;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Enfermero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepositorio extends MongoRepository<Doctor, String> {
    List<Doctor> findByNombreContainingIgnoreCase (String nombre);
}
