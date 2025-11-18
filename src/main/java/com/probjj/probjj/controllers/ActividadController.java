package com.probjj.probjj.controllers;

import com.probjj.probjj.entity.ActividadEntity;
import com.probjj.probjj.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/actividad")
public class ActividadController {
    
    @Autowired
    private ActividadService actividadService;

    // Vista principal
    @GetMapping("/")
    public String index(Model model) {
        List<ActividadEntity> actividadList = actividadService.getAllActividades();
        model.addAttribute("actividadList", actividadList);
        if (!model.containsAttribute("actividad")) {
            model.addAttribute("actividad", new ActividadEntity());
        }
        return "actividad/index";
    }

    // API REST - Listar todos
    @GetMapping("/api/all")
    @ResponseBody
    public ResponseEntity<?> getAllActividades() {
        List<ActividadEntity> list = actividadService.getAllActividades();
        return ResponseEntity.ok(list);
    }

    // API REST - Obtener por ID
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> getActividad(@PathVariable Long id) {
        Optional<ActividadEntity> actividad = actividadService.getActividadById(id);
        return actividad.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear desde formulario
    @PostMapping("/create")
    public String createFromForm(@ModelAttribute ActividadEntity actividad, Model model) {
        try {
            actividadService.createActividad(actividad);
            return "redirect:/actividad/";
        } catch (Exception e) {
            List<ActividadEntity> actividadList = actividadService.getAllActividades();
            model.addAttribute("actividadList", actividadList);
            model.addAttribute("actividad", new ActividadEntity());
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "actividad/index";
        }
    }

    // Eliminar
    @GetMapping("/delete/{id}")
    public String deleteFromForm(@PathVariable Long id) {
        actividadService.deleteActividad(id);
        return "redirect:/actividad/";
    }
}
