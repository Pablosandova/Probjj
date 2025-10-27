package com.probjj.probjj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String identifier, @RequestParam String password) {
        // Aquí irá la lógica de autenticación
        // Por ahora solo redirigimos al dashboard
        return "redirect:/";
    }
}
