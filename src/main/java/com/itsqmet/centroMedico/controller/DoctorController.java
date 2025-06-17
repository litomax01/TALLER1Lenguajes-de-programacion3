package com.itsqmet.centroMedico.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.centroMedico.entity.Cita;
import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.DoctorServicio;
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
    public String guardarDoctor(@Valid @ModelAttribute("doctor") Doctor doctor,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/registerDoctor";
        }
        doctorServicio.guardarDoctor(doctor);
        return "redirect:/listaDoctor";
    }

    //actualizar doctor
    @GetMapping("/editarDoctor/{id}")
    public String actualizarDoctor(@PathVariable Long id, Model model) {
        Optional<Doctor> doctor = doctorServicio.buscarDoctorPorId(id);
        model.addAttribute("doctor", doctor);
        return "pages/listaDoctor";
    }

    //eliminar doctor
    @GetMapping("/eliminarDoctor/{id}")
    public String eliminarDoctor(@PathVariable Long id) {
        doctorServicio.eliminarDoctor(id);
        return "redirect:/listaDoctor";
    }

    //para la autenticacion
    @PostMapping("/loginDoctor")
    public String procesarLoginDoctor(@ModelAttribute("doctor") Doctor doctor, Model model) {
        Optional<Doctor> doctorEncontrado = doctorServicio.autenticarDoctor(doctor.getCorreo(), doctor.getContrasenia());
        if (doctorEncontrado.isPresent()) {
            model.addAttribute("cita", new Cita());
            model.addAttribute("doctores", doctorServicio.mostrarDoctores());
            return "pages/registerCita";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "pages/loginDoctor";
        }
    }

    //metodo para generar un listado pdf
    @GetMapping("/doctoresPDF")
    public void exportPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=doctores.pdf");

        List<Doctor> doctores = doctorServicio.mostrarDoctores();

        Document documento = new Document();
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();
        documento.add(new Paragraph("LISTADO DE DOCTORES"));
        documento.add(new Paragraph(" "));

        for (Doctor doctor : doctores) {
            documento.add(new Paragraph(doctor.getId() + "-" + doctor.getCedula() + "-"
                    + doctor.getNombre()+ "-" + doctor.getApellido()));
        }
        documento.close();
    }
}