package com.itsqmet.centroMedico.service;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.repository.DoctorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServicio {

    @Autowired
    private DoctorRepositorio doctorRepositorio;

    //mostrar la lista de doctores
    public List<Doctor> mostrarDoctores() {
        return doctorRepositorio.findAll();
    }

    //buscar doctores por id
    public Optional<Doctor> buscarDoctorPorId(String id) {
        return doctorRepositorio.findById(id);
    }

    //buscar doctores por nombre
    public List<Doctor> buscarDoctorPorNombre(String buscarDoctor) {
        if (buscarDoctor == null || buscarDoctor.isEmpty()) {
            return doctorRepositorio.findAll();
        } else {
            return doctorRepositorio.findByNombreContainingIgnoreCase(buscarDoctor);
        }
    }

    //guardar doctor
    public Doctor guardarDoctor(Doctor doctor) {
        return doctorRepositorio.save(doctor);
    }

    //eliminar doctor
    public void eliminarDoctor(String id) {
        doctorRepositorio.deleteById(id);
    }
}
