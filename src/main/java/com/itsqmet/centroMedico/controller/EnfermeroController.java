package com.itsqmet.centroMedico.controller;

import com.itsqmet.centroMedico.entity.Enfermero;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.EnfermeroServicio;
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
public class EnfermeroController {

    @Autowired
    private EnfermeroServicio enfermeroServicio;

    @GetMapping("/loginEnfermero")
    public String mostrarLoginEnfermero(Model model) {
        model.addAttribute("enfermero", new Enfermero());
        return "pages/loginEnfermero";
    }

    //Leer datos enfermero
    @GetMapping("/listaEnfermero")
    public String mostrarEnfermero(@RequestParam(name = "buscarEnfermero",
            required = false, defaultValue = "") String buscarEnfermero, Model model) {
        List<Enfermero> enfermeros = enfermeroServicio.buscarEnfermeroPorNombre(buscarEnfermero);
        model.addAttribute("buscarEnfermero", buscarEnfermero);
        model.addAttribute("enfermeros", enfermeros);
        return "pages/listaEnfermero";
    }

    //crear
    @GetMapping("/registerEnfermero")
    public String registerEnfermero(Model model) {
        model.addAttribute("enfermero", new Enfermero());
        return "pages/registerEnfermero";
    }

    @PostMapping("/guardarEnfermero")
    public String guardarEnfermero(Enfermero enfermero) {
        enfermeroServicio.guardarEnfermero(enfermero);
        return "redirect:/listaEnfermero";
    }

    //actualizar enfermero
    @GetMapping("/editarEnfermero/{id}")
    public String actualizarEnfermero(@PathVariable String id, Model model) {
        Optional<Enfermero> enfermero = enfermeroServicio.buscarEnfermeroPorId(id);
        model.addAttribute("enfermero", enfermero);
        return "pages/registerEnfermero";
    }

    //eliminar Enfermero
    @GetMapping("/eliminarEnfermero/{id}")
    public String eliminarEnfermero(@PathVariable String id) {
        enfermeroServicio.eliminarEnfermero(id);
        return "redirect:/listaEnfermero";
    }
}
