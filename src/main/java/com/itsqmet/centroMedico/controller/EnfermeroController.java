package com.itsqmet.centroMedico.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.centroMedico.entity.Cita;
import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Enfermero;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.EnfermeroServicio;
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
    public String guardarEnfermero(@Valid @ModelAttribute("enfermero") Enfermero enfermero,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/registerEnfermero";
        }
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

    //para la autenticacion
    @PostMapping("/loginEnfermero")
    public String procesarLoginEnfermero(@ModelAttribute("enfermero") Enfermero enfermero, Model model) {
        Optional<Enfermero> enfermeroEncontrado = enfermeroServicio.autenticarEnfermero(enfermero.getCorreo(), enfermero.getContrasenia());
        if (enfermeroEncontrado.isPresent()) {
            model.addAttribute("cita", new Cita());
            model.addAttribute("enfermeros", enfermeroServicio.mostrarEnfermeros());
            return "pages/registerCita";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "pages/loginEnfermero";
        }
    }

    //metodo para generar un listado pdf
    @GetMapping("/enfermerosPDF")
    public void exportPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=enfermeros.pdf");

        List<Enfermero> enfermeros = enfermeroServicio.mostrarEnfermeros();

        Document documento = new Document();
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();
        documento.add(new Paragraph("LISTADO DE ENFERMEROS"));
        documento.add(new Paragraph(" "));

        for (Enfermero enfermero : enfermeros) {
            documento.add(new Paragraph(enfermero.getId() + "-" + enfermero.getCedula() + "-"
                    + enfermero.getNombre()+ "-" + enfermero.getApellido()));
        }
        documento.close();
    }
}
