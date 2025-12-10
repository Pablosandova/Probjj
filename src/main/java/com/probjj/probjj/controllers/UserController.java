package com.probjj.probjj.controllers;

import com.probjj.probjj.entity.Usuario;
import com.probjj.probjj.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfil")
public class UserController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String perfil(HttpSession session, Model model) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        
        if (usuarioId == null) {
            return "redirect:/login";
        }
        
        Usuario usuario = usuarioService.obtenerPorId(usuarioId)
                .orElse(null);
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuario", usuario);
        return "user/perfil";
    }
}
