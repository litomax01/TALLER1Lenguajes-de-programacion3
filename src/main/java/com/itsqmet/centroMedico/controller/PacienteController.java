package com.itsqmet.centroMedico.controller;

import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PacienteController {

    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/loginPaciente")
    public String mostrarLoginPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pages/loginPaciente";
    }

    //Leer datos paciente
    @GetMapping("/listaPaciente")
    public String mostrarPacientes(@RequestParam(name = "buscarPaciente",
            required = false, defaultValue = "") String buscarPaciente, Model model) {
        List<Paciente> pacientes = pacienteServicio.buscarPacientePorNombre(buscarPaciente);
        model.addAttribute("buscarPaciente", buscarPaciente);
        model.addAttribute("pacientes", pacientes);
        return "pages/listaPaciente";
    }

    //crear
    @GetMapping("/registerPaciente")
    public String registerPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pages/registerPaciente";
    }

    @PostMapping("/guardarPaciente")
    public String guardarPaciente(Paciente paciente) {
        pacienteServicio.guardarPaciente(paciente);
        return "redirect:/listaPaciente";
    }

    //actualizar paciente
    @GetMapping("/editarPaciente/{id}")
    public String actualizarPaciente(@PathVariable String id, Model model) {
        Optional<Paciente> paciente = pacienteServicio.buscarPacientePorId(id);
        model.addAttribute("paciente", paciente);
        return "pages/registerPaciente";
    }

    //eliminar paciente
    @GetMapping("/eliminarPaciente/{id}")
    public String eliminarPaciente(@PathVariable String id) {
        pacienteServicio.eliminarPaciente(id);
        return "redirect:/listaPaciente";
    }
}
