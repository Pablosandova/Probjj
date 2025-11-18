package com.probjj.probjj.service;

import com.probjj.probjj.entity.NutritionDataEntity;
import com.probjj.probjj.dao.NutritionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NutritionDataService {
    
    @Autowired
    private NutritionDataRepository nutritionDataRepository;

    // Obtener todos los registros
    public List<NutritionDataEntity> getAllNutritionData() {
        return nutritionDataRepository.findAll();
    }

    // Obtener registro por ID
    public Optional<NutritionDataEntity> getNutritionDataById(Long id) {
        return nutritionDataRepository.findById(id);
    }

    // Obtener registros por nombre de usuario
    public List<NutritionDataEntity> getNutritionDataByUserName(String userName) {
        return nutritionDataRepository.findByUserName(userName);
    }

    // Crear nuevo registro
    public NutritionDataEntity createNutritionData(NutritionDataEntity nutritionData) {
        return nutritionDataRepository.save(nutritionData);
    }

    // Actualizar registro
    public NutritionDataEntity updateNutritionData(Long id, NutritionDataEntity nutritionDataDetails) {
        Optional<NutritionDataEntity> nutritionData = nutritionDataRepository.findById(id);
        
        if (nutritionData.isPresent()) {
            NutritionDataEntity entity = nutritionData.get();
            entity.setUserName(nutritionDataDetails.getUserName());
            entity.setEdad(nutritionDataDetails.getEdad());
            entity.setEstatura(nutritionDataDetails.getEstatura());
            entity.setPeso(nutritionDataDetails.getPeso());
            entity.setTiempoEntrenamiento(nutritionDataDetails.getTiempoEntrenamiento());
            entity.setCalories(nutritionDataDetails.getCalories());
            entity.setProteins(nutritionDataDetails.getProteins());
            entity.setCarbohydrates(nutritionDataDetails.getCarbohydrates());
            entity.setCaloriesGoal(nutritionDataDetails.getCaloriesGoal());
            entity.setProteinsGoal(nutritionDataDetails.getProteinsGoal());
            entity.setCarbohydratesGoal(nutritionDataDetails.getCarbohydratesGoal());
            return nutritionDataRepository.save(entity);
        }
        return null;
    }

    // Eliminar registro
    public void deleteNutritionData(Long id) {
        nutritionDataRepository.deleteById(id);
    }

    // Eliminar todos los registros de un usuario
    public void deleteByUserName(String userName) {
        List<NutritionDataEntity> list = nutritionDataRepository.findByUserName(userName);
        nutritionDataRepository.deleteAll(list);
    }
}
