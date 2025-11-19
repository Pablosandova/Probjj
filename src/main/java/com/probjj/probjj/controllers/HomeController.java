package com.probjj.probjj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // Verificar si hay un usuario en sesión
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        
        if (usuarioId != null) {
            // Usuario autenticado
            model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));
            model.addAttribute("usuarioEmail", session.getAttribute("usuarioEmail"));
            model.addAttribute("isAuthenticated", true);
        } else {
            // Usuario no autenticado
            model.addAttribute("isAuthenticated", false);
        }
        
        return "index";
    }
}
