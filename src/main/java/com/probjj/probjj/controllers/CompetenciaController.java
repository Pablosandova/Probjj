package com.probjj.probjj.controllers;

import com.probjj.probjj.entity.CompetenciaEntity;
import com.probjj.probjj.service.CompetenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/competencia")
public class CompetenciaController {
    
    @Autowired
    private CompetenciaService competenciaService;

    // Vista principal
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        List<CompetenciaEntity> competenciaList = competenciaService.getAllCompetencias();
        model.addAttribute("competenciaList", competenciaList);
        
        // Cargar datos de sesión
        String usuarioNombre = (String) session.getAttribute("usuarioNombre");
        Integer usuarioEdad = (Integer) session.getAttribute("usuarioEdad");
        Double usuarioEstatura = (Double) session.getAttribute("usuarioEstatura");
        
        model.addAttribute("usuarioNombre", usuarioNombre);
        model.addAttribute("usuarioEdad", usuarioEdad);
        model.addAttribute("usuarioEstatura", usuarioEstatura);
        model.addAttribute("sesionActiva", usuarioNombre != null);
        
        if (!model.containsAttribute("competencia")) {
            CompetenciaEntity nuevaCompetencia = new CompetenciaEntity();
            model.addAttribute("competencia", nuevaCompetencia);
        }
        return "competencia/index";
    }

    // API REST - Listar todos
    @GetMapping("/api/all")
    @ResponseBody
    public ResponseEntity<?> getAllCompetencias() {
        List<CompetenciaEntity> list = competenciaService.getAllCompetencias();
        return ResponseEntity.ok(list);
    }

    // API REST - Obtener por ID
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> getCompetencia(@PathVariable Long id) {
        Optional<CompetenciaEntity> competencia = competenciaService.getCompetenciaById(id);
        return competencia.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear desde formulario
    @PostMapping("/create")
    public String createFromForm(@ModelAttribute CompetenciaEntity competencia, Model model) {
        try {
            competenciaService.createCompetencia(competencia);
            return "redirect:/competencia/";
        } catch (Exception e) {
            List<CompetenciaEntity> competenciaList = competenciaService.getAllCompetencias();
            model.addAttribute("competenciaList", competenciaList);
            model.addAttribute("competencia", new CompetenciaEntity());
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "competencia/index";
        }
    }

    // Eliminar
    @GetMapping("/delete/{id}")
    public String deleteFromForm(@PathVariable Long id) {
        competenciaService.deleteCompetencia(id);
        return "redirect:/competencia/";
    }
}
