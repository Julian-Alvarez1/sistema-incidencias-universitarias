package com.example.Gestion.controller;

import com.example.Gestion.entity.HistorialEstado;
import com.example.Gestion.entity.Incidencia;
import com.example.Gestion.entity.Usuario;
import com.example.Gestion.repository.HistorialEstadoRepository;
import com.example.Gestion.repository.IncidenciaRepository;
import com.example.Gestion.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tecnico")
public class EstadoController {

    private final IncidenciaRepository incidenciaRepo;
    private final HistorialEstadoRepository historialRepo;
    private final UsuarioRepository usuarioRepo;

    public EstadoController(IncidenciaRepository incidenciaRepo,
                            HistorialEstadoRepository historialRepo,
                            UsuarioRepository usuarioRepo) {
        this.incidenciaRepo = incidenciaRepo;
        this.historialRepo = historialRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // CU05 - Listar incidencias asignadas al técnico
    @GetMapping("/mis-incidencias")
    public String misIncidencias(Model model) {
        model.addAttribute("incidencias", incidenciaRepo.findByEstadoIn(
            java.util.List.of(Incidencia.EstadoIncidencia.ASIGNADA, Incidencia.EstadoIncidencia.EN_PROCESO)));
        return "tecnico/mis-incidencias";
    }

    // CU05 - Cambiar estado de incidencia
    @PostMapping("/incidencia/{id}/estado")
    public String cambiarEstado(@PathVariable Integer id,
                                @RequestParam Incidencia.EstadoIncidencia nuevoEstado,
                                @RequestParam(required = false) String observacion,
                                @AuthenticationPrincipal UserDetails userDetails) {
        Incidencia incidencia = incidenciaRepo.findById(id).orElse(null);
        Usuario usuario = usuarioRepo.findByCorreo(userDetails.getUsername());
        if (incidencia != null && usuario != null) {
            HistorialEstado historial = new HistorialEstado();
            historial.setEstadoAnterior(incidencia.getEstado());
            historial.setEstadoNuevo(nuevoEstado);
            historial.setIncidencia(incidencia);
            historial.setUsuario(usuario);
            historialRepo.save(historial);

            incidencia.setEstado(nuevoEstado);
            incidenciaRepo.save(incidencia);
        }
        return "redirect:/tecnico/mis-incidencias";
    }
}
