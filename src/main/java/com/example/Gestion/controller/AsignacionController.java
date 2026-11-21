package com.example.Gestion.controller;

import com.example.Gestion.entity.Asignacion;
import com.example.Gestion.entity.Incidencia;
import com.example.Gestion.entity.Usuario;
import com.example.Gestion.repository.AsignacionRepository;
import com.example.Gestion.repository.IncidenciaRepository;
import com.example.Gestion.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/asignaciones")
public class AsignacionController {

    private final AsignacionRepository asignacionRepo;
    private final IncidenciaRepository incidenciaRepo;
    private final UsuarioRepository usuarioRepo;

    public AsignacionController(AsignacionRepository asignacionRepo,
                                IncidenciaRepository incidenciaRepo,
                                UsuarioRepository usuarioRepo) {
        this.asignacionRepo = asignacionRepo;
        this.incidenciaRepo = incidenciaRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // CU04 - Listar incidencias pendientes para asignar
    @GetMapping
    public String listarPendientes(Model model) {
        model.addAttribute("pendientes", incidenciaRepo.findByEstado(Incidencia.EstadoIncidencia.REGISTRADA));
        model.addAttribute("tecnicos", usuarioRepo.findByRolNombreAndEstado("TECNICO", Usuario.EstadoUsuario.ACTIVO));
        return "admin/asignar";
    }

    // CU04 - Asignar incidencia a técnico
    @PostMapping("/{idIncidencia}/asignar")
    public String asignar(@PathVariable Integer idIncidencia,
                          @RequestParam Integer idTecnico,
                          @RequestParam(required = false) String observacion) {
        Incidencia incidencia = incidenciaRepo.findById(idIncidencia).orElse(null);
        Usuario tecnico = usuarioRepo.findById(idTecnico).orElse(null);
        if (incidencia != null && tecnico != null) {
            incidencia.setEstado(Incidencia.EstadoIncidencia.ASIGNADA);
            incidenciaRepo.save(incidencia);

            Asignacion asignacion = new Asignacion();
            asignacion.setIncidencia(incidencia);
            asignacion.setTecnico(tecnico);
            asignacion.setObservacion(observacion);
            asignacionRepo.save(asignacion);
        }
        return "redirect:/admin/asignaciones";
    }
}
