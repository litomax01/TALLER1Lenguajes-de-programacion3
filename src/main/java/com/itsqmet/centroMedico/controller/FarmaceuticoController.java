package com.itsqmet.centroMedico.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itsqmet.centroMedico.entity.Cita;
import com.itsqmet.centroMedico.entity.Doctor;
import com.itsqmet.centroMedico.entity.Farmaceutico;
import com.itsqmet.centroMedico.entity.Paciente;
import com.itsqmet.centroMedico.service.FarmaceuticoServicio;
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
    public String guardarFarmaceutico(@Valid @ModelAttribute("farmaceutico") Farmaceutico farmaceutico,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/registerFarmaceutico";
        }
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

    //para la autenticacion
    @PostMapping("/loginFarmaceutico")
    public String procesarLoginFarmaceutico(@ModelAttribute("farmaceutico") Farmaceutico farmaceutico, Model model) {
        Optional<Farmaceutico> farmaceuticoEncontrado = farmaceuticoServicio.autenticarFarmaceutico(farmaceutico.getCorreo(), farmaceutico.getContrasenia());
        if (farmaceuticoEncontrado.isPresent()) {
            model.addAttribute("cita", new Cita());
            model.addAttribute("farmaceuticos", farmaceuticoServicio.mostrarFarmaceuticos());
            return "pages/registerCita";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "pages/loginFarmaceutico";
        }
    }

    //metodo para generar un listado pdf
    @GetMapping("/farmaceuticosPDF")
    public void exportPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=farmaceuticos.pdf");

        List<Farmaceutico> farmaceuticos = farmaceuticoServicio.mostrarFarmaceuticos();

        Document documento = new Document();
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();
        documento.add(new Paragraph("LISTADO DE FARMACEUTICOS"));
        documento.add(new Paragraph(" "));

        for (Farmaceutico farmaceutico : farmaceuticos) {
            documento.add(new Paragraph(farmaceutico.getId() + "-" + farmaceutico.getCedula() + "-"
                    + farmaceutico.getNombre()+ "-" + farmaceutico.getApellido()));
        }
        documento.close();
    }
}
