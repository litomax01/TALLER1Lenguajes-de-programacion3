package com.itsqmet.centroMedico.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.centroMedico.entity.Cita;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.PacienteServicio;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String guardarPaciente(@Valid @ModelAttribute("paciente") Paciente paciente,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/registerPaciente";
        }
        pacienteServicio.guardarPaciente(paciente);
        return "redirect:/listaPaciente";
    }

    //actualizar paciente
    @GetMapping("/editarPaciente/{id}")
    public String actualizarPaciente(@PathVariable Long id, Model model) {
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

    //para la utenticacion
    @PostMapping("/loginPaciente")
    public String procesarLoginPaciente(@ModelAttribute("paciente") Paciente paciente, Model model) {
        Optional<Paciente> pacienteEncontrado = pacienteServicio.autenticarPaciente(paciente.getCorreo(), paciente.getContrasenia());
        if (pacienteEncontrado.isPresent()) {
            model.addAttribute("cita", new Cita());
            model.addAttribute("pacientes", pacienteServicio.mostrarPacientes());
            return "pages/registerCita";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "pages/loginPaciente";
        }
    }

    //metodo para generar un listado pdf
    @GetMapping("/pacientesPDF")
    public void exportPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pacientes.pdf");

        List<Paciente> pacientes = pacienteServicio.mostrarPacientes();

        Document documento = new Document();
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();
        documento.add(new Paragraph("LISTADO DE PACIENTES"));
        documento.add(new Paragraph(" "));

        for (Paciente paciente : pacientes) {
            documento.add(new Paragraph(paciente.getId() + "-" + paciente.getCedula() + "-"
                    + paciente.getNombre()+ "-" + paciente.getApellido()));
        }
        documento.close();
    }
}
