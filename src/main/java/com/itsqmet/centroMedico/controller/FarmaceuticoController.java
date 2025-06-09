package com.itsqmet.centroMedico.controller;

import com.itsqmet.centroMedico.entity.Farmaceutico;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.FarmaceuticoServicio;
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
public class FarmaceuticoController {

    @Autowired
    private FarmaceuticoServicio farmaceuticoServicio;

    @GetMapping("/loginFarmaceutico")
    public String mostrarLoginFarmaceutico(Model model) {
        model.addAttribute("farmaceutico", new Farmaceutico());
        return "pages/loginFarmaceutico";
    }

    //Leer datos paciente
    @GetMapping("/listaFarmaceutico")
    public String mostrarFarmaceuticos(@RequestParam(name = "buscarFarmaceutico",
            required = false, defaultValue = "") String buscarFarmaceutico, Model model) {
        List<Farmaceutico> farmaceuticos = farmaceuticoServicio.buscarFarmaceuticoPorNombre(buscarFarmaceutico);
        model.addAttribute("buscarFarmaceutico", buscarFarmaceutico);
        model.addAttribute("farmaceuticos", farmaceuticos);
        return "pages/listaFarmaceutico";
    }

    //crear
    @GetMapping("/registerFarmaceutico")
    public String registerFarmaceutico(Model model) {
        model.addAttribute("farmaceutico", new Farmaceutico());
        return "pages/registerFarmaceutico";
    }

    @PostMapping("/guardarFarmaceutico")
    public String guardarFarmaceutico(Farmaceutico farmaceutico) {
        farmaceuticoServicio.guardarFarmaceutico(farmaceutico);
        return "redirect:/listaFarmaceutico";
    }

    //actualizar Farmaceutico
    @GetMapping("/editarFarmaceutico/{id}")
    public String actualizarFarmaceutico(@PathVariable String id, Model model) {
        Optional<Farmaceutico> farmaceutico = farmaceuticoServicio.buscarFarmaceuticoPorId(id);
        model.addAttribute("farmaceutico", farmaceutico);
        return "pages/registerFarmaceutico";
    }

    //eliminar Farmaceutico
    @GetMapping("/eliminarFarmaceutico/{id}")
    public String eliminarFarmaceutico(@PathVariable String id) {
        farmaceuticoServicio.eliminarFarmaceutico(id);
        return "redirect:/listaFarmaceutico";
    }
}
