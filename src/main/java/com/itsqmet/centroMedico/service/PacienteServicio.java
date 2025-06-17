package com.itsqmet.centroMedico.service;
import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.repository.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    //mostrar la lista de pacientes
    public List<Paciente> mostrarPacientes() {
        return pacienteRepositorio.findAll();
    }

    //buscar pacientes por id
    public Optional<Paciente> buscarPacientePorId(Long id) {
        return pacienteRepositorio.findById(id);
    }

    //buscar Pacientes por nombre
    public List<Paciente> buscarPacientePorNombre(String buscarPaciente) {
        if (buscarPaciente == null || buscarPaciente.isEmpty()) {
            return pacienteRepositorio.findAll();
        } else {
            return pacienteRepositorio.findByNombreContainingIgnoreCase(buscarPaciente);
        }
    }

    //guardar Paciente
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepositorio.save(paciente);
    }

    //eliminar Paciente
    public void eliminarPaciente(String id) {
        pacienteRepositorio.deleteById(id);
    }

    //metodo de autenticacion
    public Optional<Paciente> autenticarPaciente(String correo, String contrasenia) {
        return pacienteRepositorio.findByCorreoAndContrasenia(correo, contrasenia);
    }
}
