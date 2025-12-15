package com.probjj.probjj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SaludoController {
    
    private String ultimoSaludo = "Esperando saludo...";
    
    // Endpoint GET para recibir saludo desde Ionic
    @GetMapping("/saludo/{nombre}")
    @ResponseBody
    public Map<String, String> saludarGet(@PathVariable String nombre) {
        ultimoSaludo = "¡Hola " + nombre + "! Bienvenido a PROBJJ";
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ultimoSaludo);
        response.put("fecha", java.time.LocalDateTime.now().toString());
        response.put("status", "success");
        return response;
    }
    
    // Endpoint POST para recibir saludo desde Ionic (alternativo)
    @PostMapping("/saludo")
    @ResponseBody
    public Map<String, String> saludarPost(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        ultimoSaludo = "¡Hola " + nombre + "! Bienvenido a PROBJJ";
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ultimoSaludo);
        response.put("fecha", java.time.LocalDateTime.now().toString());
        response.put("status", "success");
        return response;
    }
    
    @GetMapping("/ver-saludo")
    public String verSaludo(Model model) {
        model.addAttribute("saludo", ultimoSaludo);
        return "saludo";
    }
}
