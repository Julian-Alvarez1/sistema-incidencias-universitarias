package com.example.Gestion.controller;

import com.example.Gestion.entity.Incidencia;
import com.example.Gestion.entity.Ubicacion;
import com.example.Gestion.entity.Usuario;
import com.example.Gestion.repository.IncidenciaRepository;
import com.example.Gestion.repository.TipoIncidenciaRepository;
import com.example.Gestion.repository.UbicacionRepository;
import com.example.Gestion.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/incidencias")
public class IncidenciaController {

    private final IncidenciaRepository incidenciaRepo;
    private final TipoIncidenciaRepository tipoRepo;
    private final UbicacionRepository ubicacionRepo;
    private final UsuarioRepository usuarioRepo;

    public IncidenciaController(IncidenciaRepository incidenciaRepo,
                                TipoIncidenciaRepository tipoRepo,
                                UbicacionRepository ubicacionRepo,
                                UsuarioRepository usuarioRepo) {
        this.incidenciaRepo = incidenciaRepo;
        this.tipoRepo = tipoRepo;
        this.ubicacionRepo = ubicacionRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // CU02 - Registrar incidencia (formulario)
    @GetMapping("/nueva")
    public String nueva(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("tipos", tipoRepo.findAll());
        model.addAttribute("ubicaciones", ubicacionRepo.findAll());
        model.addAttribute("incidencia", new Incidencia());
        return "incidencias/formulario";
    }

    // CU02 - Guardar incidencia
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Incidencia incidencia,
                          @AuthenticationPrincipal UserDetails userDetails) {
        Usuario reportante = usuarioRepo.findByCorreo(userDetails.getUsername());
        incidencia.setReportante(reportante);
        incidenciaRepo.save(incidencia);
        return "redirect:/incidencias/mis?creada=true";
    }

    // CU03 - Consultar incidencia (listado)
    @GetMapping
    public String listar(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioRepo.findByCorreo(userDetails.getUsername());
        if (usuario.isAdministrador() || usuario.isTecnico()) {
            model.addAttribute("incidencias", incidenciaRepo.findAll());
        } else {
            model.addAttribute("incidencias", incidenciaRepo.findByReportanteId(usuario.getId()));
        }
        return "incidencias/lista";
    }

    // CU03 - Ver detalle de una incidencia
    @GetMapping("/{id}")
    public String detalle(@PathVariable Integer id, Model model,
                          @AuthenticationPrincipal UserDetails userDetails) {
        Incidencia incidencia = incidenciaRepo.findById(id).orElse(null);
        if (incidencia == null) {
            return "redirect:/incidencias";
        }
        Usuario usuario = usuarioRepo.findByCorreo(userDetails.getUsername());
        // Solo el reportante, admin o técnico puede ver
        boolean puedeVer = usuario.isAdministrador() ||
                usuario.isTecnico() ||
                incidencia.getReportante().getId().equals(usuario.getId());
        if (!puedeVer) {
            return "redirect:/incidencias";
        }
        model.addAttribute("incidencia", incidencia);
        return "incidencias/detalle";
    }

    // CU08 / RF08 - Historial de reportes del usuario
    @GetMapping("/mis")
    public String misReportes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioRepo.findByCorreo(userDetails.getUsername());
        model.addAttribute("incidencias", incidenciaRepo.findByReportanteId(usuario.getId()));
        model.addAttribute("creada", model.containsAttribute("creada"));
        return "incidencias/mis";
    }
}
