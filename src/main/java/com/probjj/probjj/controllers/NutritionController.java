package com.probjj.probjj.controllers;

import com.probjj.probjj.entity.NutritionDataEntity;
import com.probjj.probjj.service.NutritionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/nutrition")
public class NutritionController {
    
    @Autowired
    private NutritionDataService nutritionDataService;

    // Vista principal
    @GetMapping("/")
    public String index(Model model) {
        List<NutritionDataEntity> nutritionList = nutritionDataService.getAllNutritionData();
        model.addAttribute("nutritionList", nutritionList);
        if (!model.containsAttribute("nutritionData")) {
            model.addAttribute("nutritionData", new NutritionDataEntity());
        }
        return "nutrition/index";
    }

    // API REST - Listar todos
    @GetMapping("/api/all")
    @ResponseBody
    public ResponseEntity<?> getAllNutritionData() {
        List<NutritionDataEntity> list = nutritionDataService.getAllNutritionData();
        return ResponseEntity.ok(list);
    }

    // API REST - Obtener por ID
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> getNutritionData(@PathVariable Long id) {
        Optional<NutritionDataEntity> nutritionData = nutritionDataService.getNutritionDataById(id);
        return nutritionData.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // API REST - Obtener por nombre de usuario
    @GetMapping("/api/user/{userName}")
    @ResponseBody
    public ResponseEntity<?> getNutritionDataByUserName(@PathVariable String userName) {
        List<NutritionDataEntity> list = nutritionDataService.getNutritionDataByUserName(userName);
        return ResponseEntity.ok(list);
    }

    // API REST - Crear
    @PostMapping("/api/create")
    @ResponseBody
    public ResponseEntity<?> createNutritionData(@RequestBody NutritionDataEntity nutritionData) {
        try {
            NutritionDataEntity created = nutritionDataService.createNutritionData(nutritionData);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Crear desde formulario
    @PostMapping("/create")
    public String createFromForm(@ModelAttribute NutritionDataEntity nutritionData, Model model) {
        try {
            nutritionDataService.createNutritionData(nutritionData);
            return "redirect:/nutrition/";
        } catch (Exception e) {
            List<NutritionDataEntity> nutritionList = nutritionDataService.getAllNutritionData();
            model.addAttribute("nutritionList", nutritionList);
            model.addAttribute("nutritionData", new NutritionDataEntity());
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "nutrition/index";
        }
    }

    // API REST - Actualizar
    @PutMapping("/api/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateNutritionData(@PathVariable Long id, @RequestBody NutritionDataEntity nutritionDataDetails) {
        try {
            NutritionDataEntity updated = nutritionDataService.updateNutritionData(id, nutritionDataDetails);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // API REST - Eliminar
    @DeleteMapping("/api/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteNutritionData(@PathVariable Long id) {
        try {
            Optional<NutritionDataEntity> nutritionData = nutritionDataService.getNutritionDataById(id);
            if (nutritionData.isPresent()) {
                nutritionDataService.deleteNutritionData(id);
                return ResponseEntity.ok("Eliminado correctamente");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Eliminar desde formulario
    @GetMapping("/delete/{id}")
    public String deleteFromForm(@PathVariable Long id) {
        nutritionDataService.deleteNutritionData(id);
        return "redirect:/nutrition/";
    }
}
