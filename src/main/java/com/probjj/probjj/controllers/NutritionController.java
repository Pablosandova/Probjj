package com.probjj.probjj.controllers;

import com.probjj.probjj.models.NutritionData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/nutrition")
public class NutritionController {
    
    private List<NutritionData> nutritionDataList = new ArrayList<>();
    private NutritionData goals;

    public NutritionController() {
        // Inicializar metas por defecto
        goals = new NutritionData();
        goals.setCaloriesGoal(2500);
        goals.setProteinsGoal(150);
        goals.setCarbohydratesGoal(300);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addNutritionData(@RequestBody NutritionData data) {
        data.setDateTime(LocalDateTime.now());
        // Establecer las metas actuales
        data.setCaloriesGoal(goals.getCaloriesGoal());
        data.setProteinsGoal(goals.getProteinsGoal());
        data.setCarbohydratesGoal(goals.getCarbohydratesGoal());
        nutritionDataList.add(data);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/latest")
    @ResponseBody
    public ResponseEntity<?> getLatestNutritionData() {
        if (nutritionDataList.isEmpty()) {
            return ResponseEntity.ok(goals);
        }
        return ResponseEntity.ok(nutritionDataList.get(nutritionDataList.size() - 1));
    }

    @GetMapping("/history")
    @ResponseBody
    public ResponseEntity<?> getNutritionHistory() {
        return ResponseEntity.ok(nutritionDataList);
    }

    @PostMapping("/goals")
    @ResponseBody
    public ResponseEntity<?> updateGoals(@RequestBody NutritionData newGoals) {
        goals.setCaloriesGoal(newGoals.getCaloriesGoal());
        goals.setProteinsGoal(newGoals.getProteinsGoal());
        goals.setCarbohydratesGoal(newGoals.getCarbohydratesGoal());
        return ResponseEntity.ok(goals);
    }

    @GetMapping("/goals")
    @ResponseBody
    public ResponseEntity<?> getGoals() {
        return ResponseEntity.ok(goals);
    }
}
