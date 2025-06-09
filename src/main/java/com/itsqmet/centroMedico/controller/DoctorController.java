package com.itsqmet.centroMedico.controller;

import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.DoctorServicio;
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
public class DoctorController {

    @Autowired
    private DoctorServicio doctorServicio;

    @GetMapping("/loginDoctor")
    public String mostrarLoginDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "pages/loginDoctor";
    }

    //Leer datos doctor
    @GetMapping("/listaDoctor")
    public String mostrarDoctores(@RequestParam(name = "buscarDoctor",
            required = false, defaultValue = "") String buscarDoctor, Model model) {
        List<Doctor> doctores = doctorServicio.buscarDoctorPorNombre(buscarDoctor);
        model.addAttribute("buscarDoctor", buscarDoctor);
        model.addAttribute("doctores", doctores);
        return "pages/listaDoctor";
    }

    //crear
    @GetMapping("/registerDoctor")
    public String registerDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "pages/registerDoctor";
    }

    @PostMapping("/guardarDoctor")
    public String guardarDoctor(Doctor doctor) {
        doctorServicio.guardarDoctor(doctor);
        return "redirect:/listaDoctor";
    }

    //actualizar dcotor
    @GetMapping("/editarDoctor/{id}")
    public String actualizarDoctor(@PathVariable String id, Model model) {
        Optional<Doctor> doctor = doctorServicio.buscarDoctorPorId(id);
        model.addAttribute("doctor", doctor);
        return "pages/registerDoctor";
    }

    //eliminar paciente
    @GetMapping("/eliminarDoctor/{id}")
    public String eliminarDoctor(@PathVariable String id) {
        doctorServicio.eliminarDoctor(id);
        return "redirect:/listaDoctor";
    }
}
