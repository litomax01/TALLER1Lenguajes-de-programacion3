package com.itsqmet.centroMedico.service;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Farmaceutico;
import com.itsqmet.centroMedico.repository.FarmaceuticoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmaceuticoServicio {

    @Autowired
    private FarmaceuticoRepositorio farmaceuticoRepositorio;

    //mostrar la lista de farmaceutico
    public List<Farmaceutico> mostrarFarmaceuticos() {
        return farmaceuticoRepositorio.findAll();
    }

    //buscar farmaceuticos por id
    public Optional<Farmaceutico> buscarFarmaceuticoPorId(String id) {
        return farmaceuticoRepositorio.findById(id);
    }

    //buscar farmaceuticos por nombre
    public List<Farmaceutico> buscarFarmaceuticoPorNombre(String buscarFarmaceutico) {
        if (buscarFarmaceutico == null || buscarFarmaceutico.isEmpty()) {
            return farmaceuticoRepositorio.findAll();
        } else {
            return farmaceuticoRepositorio.findByNombreContainingIgnoreCase(buscarFarmaceutico);
        }
    }

    //guardar farmaceutico
    public Farmaceutico guardarFarmaceutico(Farmaceutico farmaceutico) {
        return farmaceuticoRepositorio.save(farmaceutico);
    }

    //eliminar farmaceutico
    public void eliminarFarmaceutico(String id) {
        farmaceuticoRepositorio.deleteById(id);
    }

    //metodo de autenticacion
    public Optional<Farmaceutico> autenticarFarmaceutico(String correo, String contrasenia) {
        return farmaceuticoRepositorio.findByCorreoAndContrasenia(correo, contrasenia);
    }
}
