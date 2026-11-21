package com.example.Gestion.controller;

import com.example.Gestion.entity.Reporte;
import com.example.Gestion.entity.Usuario;
import com.example.Gestion.repository.ReporteRepository;
import com.example.Gestion.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/reportes")
public class ReporteController {

    private final ReporteRepository reporteRepo;
    private final UsuarioRepository usuarioRepo;

    public ReporteController(ReporteRepository reporteRepo, UsuarioRepository usuarioRepo) {
        this.reporteRepo = reporteRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // CU07 - Listar reportes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reportes", reporteRepo.findAll());
        return "admin/reportes";
    }

    // CU07 - Formulario para generar reporte
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("reporte", new Reporte());
        return "admin/reporte-form";
    }

    // CU07 - Generar reporte
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Reporte reporte,
                          @AuthenticationPrincipal UserDetails userDetails) {
        Usuario admin = usuarioRepo.findByCorreo(userDetails.getUsername());
        reporte.setAdministrador(admin);
        reporteRepo.save(reporte);
        return "redirect:/admin/reportes";
    }
}
