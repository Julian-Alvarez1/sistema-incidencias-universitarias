package com.example.Gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        org.springframework.ui.Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Intente nuevamente.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Sesión cerrada correctamente.");
        }
        return "login";
    }
}
