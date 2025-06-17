package com.itsqmet.centroMedico.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.centroMedico.entity.Cita;
import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.CitaServicio;
import com.itsqmet.centroMedico.service.PacienteServicio;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class CitaController {

    @Autowired
    private CitaServicio citaServicio;

    @Autowired
    private PacienteServicio pacienteServicio;

    //Leer datos
    @GetMapping("/citas")
    public String mostrarCitas(@RequestParam(name = "buscarCita",
            required = false, defaultValue = "") Date buscarCita, Model model) {
        List<Cita> citas = citaServicio.buscarCitaPorFecha(buscarCita);
        model.addAttribute("buscarCita");
        model.addAttribute("citas", citas);
        return "pages/listaCitas";
    }

    //crear
    @GetMapping("/registerCita")
    public String mostrarRegisterCita(Model model) {
        model.addAttribute("cita", new Cita());
        //enviar los pacientes de la db a la vista
        List<Paciente> pacientes = pacienteServicio.mostrarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "pages/registerCita";
    }

    @PostMapping("/guardarCita")
    public String crearCita(Cita cita) {
        citaServicio.guardarCita(cita);
        return "redirect:/listaCita";
    }

    //actualizar Cita
    @GetMapping("/editarCita/{id}")
    public String actualizarCita(@PathVariable Long id, Model model) {
        Optional<Cita> cita = citaServicio.buscarCitaPorId(id);
        model.addAttribute("cita", cita);
        //enviar pacientes de la db
        model.addAttribute("pacientes", pacienteServicio.mostrarPacientes());
        return "pages/registerCita";
    }

    //eliminar cita
    @GetMapping("/eliminarCita/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citaServicio.eliminarCita(id);
        return "redirect:/citas";
    }

    //metodo para generar un listado pdf
    @GetMapping("/citasPDF")
    public void exportPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas.pdf");

        List<Cita> citas = citaServicio.mostrarCitas();

        Document documento = new Document();
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();
        documento.add(new Paragraph("LISTADO DE CITAS"));
        documento.add(new Paragraph(" "));

        for (Cita cita : citas) {
            documento.add(new Paragraph(cita.getId() + "-" + cita.getFecha() + "-"
                    + cita.getMotivo()));
        }
        documento.close();
    }
}
