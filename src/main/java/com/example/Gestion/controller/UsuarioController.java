package com.example.Gestion.controller;

import com.example.Gestion.entity.Usuario;
import com.example.Gestion.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // CU06 - Listar usuarios
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "admin/usuarios";
    }

    // CU06 - Crear usuario (formulario)
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario-form";
    }

    // CU06 - Guardar usuario
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        usuario.setClaveHash(passwordEncoder.encode(usuario.getClaveHash()));
        usuarioRepo.save(usuario);
        return "redirect:/admin/usuarios";
    }

    // CU06 - Desactivar usuario
    @PostMapping("/{id}/desactivar")
    public String desactivar(@PathVariable Integer id) {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setEstado(Usuario.EstadoUsuario.INACTIVO);
            usuarioRepo.save(usuario);
        }
        return "redirect:/admin/usuarios";
    }
}
