package com.example.Gestion.controller;

import com.example.Gestion.entity.Usuario;
import com.example.Gestion.repository.IncidenciaRepository;
import com.example.Gestion.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final IncidenciaRepository incidenciaRepo;
    private final UsuarioRepository usuarioRepo;

    public DashboardController(IncidenciaRepository incidenciaRepo, UsuarioRepository usuarioRepo) {
        this.incidenciaRepo = incidenciaRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepo.findByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario);

        if (usuario.isAdministrador()) {
            model.addAttribute("totalIncidencias", incidenciaRepo.count());
            model.addAttribute("pendientes", incidenciaRepo.findByEstado(com.example.Gestion.entity.Incidencia.EstadoIncidencia.REGISTRADA).size());
            model.addAttribute("enProceso", incidenciaRepo.findByEstado(com.example.Gestion.entity.Incidencia.EstadoIncidencia.EN_PROCESO).size());
            model.addAttribute("resueltas", incidenciaRepo.findByEstado(com.example.Gestion.entity.Incidencia.EstadoIncidencia.RESUELTA).size());
            return "admin/dashboard";
        } else if (usuario.isTecnico()) {
            model.addAttribute("asignadas", incidenciaRepo.findByEstado(com.example.Gestion.entity.Incidencia.EstadoIncidencia.ASIGNADA).size());
            model.addAttribute("enProceso", incidenciaRepo.findByEstado(com.example.Gestion.entity.Incidencia.EstadoIncidencia.EN_PROCESO).size());
            return "tecnico/dashboard";
        } else {
            model.addAttribute("misIncidencias", incidenciaRepo.findByReportanteId(usuario.getId()).size());
            return "estudiante/dashboard";
        }
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}
