package com.itsqmet.centroMedico.service;
import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Enfermero;
import com.itsqmet.centroMedico.repository.EnfermeroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnfermeroServicio {
    @Autowired
    private EnfermeroRepositorio enfermeroRepositorio;

    //mostrar la lista de enfermeros
    public List<Enfermero> mostrarEnfermeros() {
        return enfermeroRepositorio.findAll();
    }

    //buscar enfermeros por id
    public Optional<Enfermero> buscarEnfermeroPorId(String id) {
        return enfermeroRepositorio.findById(id);
    }

    //buscar enfermero por nombre
    public List<Enfermero> buscarEnfermeroPorNombre(String buscarEnfermero) {
        if (buscarEnfermero == null || buscarEnfermero.isEmpty()) {
            return enfermeroRepositorio.findAll();
        } else {
            return enfermeroRepositorio.findByNombreContainingIgnoreCase(buscarEnfermero);
        }
    }

    //guardar enfermero
    public Enfermero guardarEnfermero(Enfermero enfermero) {
        return enfermeroRepositorio.save(enfermero);
    }

    //eliminar enfermero
    public void eliminarEnfermero(String id) {
        enfermeroRepositorio.deleteById(id);
    }

    //metodo de autenticacion
    public Optional<Enfermero> autenticarEnfermero(String correo, String contrasenia) {
        return enfermeroRepositorio.findByCorreoAndContrasenia(correo, contrasenia);
    }
}
