package com.itsqmet.centroMedico.service;

import com.itsqmet.centroMedico.entity.Cita;
import com.itsqmet.centroMedico.repository.CitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServicio {

    @Autowired
    private CitaRepositorio citaRepositorio;

    //mostrar la lista de citas
    public List<Cita> mostrarCitas() {
        return citaRepositorio.findAll();
    }

    //buscar Citas por id
    public Optional<Cita> buscarCitaPorId(Long id) {
        return citaRepositorio.findById(id);
    }

    //buscar citas por fecha
    public List<Cita> buscarCitaPorFecha(Date buscarCita) {
        if (buscarCita == null) {
            return citaRepositorio.findAll();
        } else {
            return citaRepositorio.findByFecha(buscarCita);
        }
    }

    //guardar cita
    public Cita guardarCita(Cita cita) {
        return citaRepositorio.save(cita);
    }

    //eliminar Cita
    public void eliminarCita(Long id) {
        citaRepositorio.deleteById(id);
    }
}
