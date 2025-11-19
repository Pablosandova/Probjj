package com.probjj.probjj.service;

import com.probjj.probjj.entity.RecomendacionEntity;
import com.probjj.probjj.dao.RecomendacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecomendacionService {
    
    @Autowired
    private RecomendacionRepository recomendacionRepository;
    
    public RecomendacionEntity generateRecomendations(String userName, Double peso, Double estatura) {
        RecomendacionEntity recomendacion = new RecomendacionEntity();
        recomendacion.setUserName(userName);
        recomendacion.setPeso(peso);
        recomendacion.setEstatura(estatura);
        
        // Calcular IMC (peso en kg / (estatura en m)^2)
        Double estaturaMetros = estatura / 100;
        Double imc = peso / (estaturaMetros * estaturaMetros);
        recomendacion.setImc(Math.round(imc * 10.0) / 10.0);
        
        // Categorizar IMC
        String categoriaImc = categorizarImc(imc);
        recomendacion.setCategoriaImc(categoriaImc);
        
        // Calcular macronutrientes basados en peso y actividad
        Integer calorias = calcularCalorias(peso, categoriaImc);
        Integer proteinas = calcularProteinas(peso);
        Integer carbohidratos = calcularCarbohidratos(peso);
        Integer grasas = calcularGrasas(peso);
        
        recomendacion.setCaloriasDiarias(calorias);
        recomendacion.setProteinasGramos(proteinas);
        recomendacion.setCarbohidratosGramos(carbohidratos);
        recomendacion.setGrasasGramos(grasas);
        
        // Generar recomendaciones personalizadas
        String recomAlimentarias = generarRecomendacionesAlimentarias(peso, imc, proteinas, carbohidratos, grasas);
        String recomEntrenamiento = generarRecomendacionesEntrenamiento(categoriaImc, peso);
        
        recomendacion.setRecomendacionesAlimentarias(recomAlimentarias);
        recomendacion.setRecomendacionesEntrenamiento(recomEntrenamiento);
        
        return recomendacionRepository.save(recomendacion);
    }
    
    private String categorizarImc(Double imc) {
        if (imc < 18.5) return "Bajo peso";
        if (imc < 25) return "Peso normal";
        if (imc < 30) return "Sobrepeso";
        return "Obesidad";
    }
    
    private Integer calcularCalorias(Double peso, String categoriaImc) {
        // Fórmula: peso base + ajuste por actividad BJJ (1.3 factor)
        int calorasBase = (int) (peso * 25); // 25 cal/kg base
        int caloriasBJJ = (int) (calorasBase * 1.3); // +30% por actividad BJJ
        
        // Ajuste según categoría IMC
        if ("Bajo peso".equals(categoriaImc)) {
            return (int) (caloriasBJJ * 1.1); // +10% para ganancia
        } else if ("Sobrepeso".equals(categoriaImc) || "Obesidad".equals(categoriaImc)) {
            return (int) (caloriasBJJ * 0.9); // -10% para pérdida de peso
        }
        return caloriasBJJ;
    }
    
    private Integer calcularProteinas(Double peso) {
        // 1.6-2.0g por kg de peso corporal para atletas
        return (int) (peso * 1.8);
    }
    
    private Integer calcularCarbohidratos(Double peso) {
        // 4-7g por kg de peso corporal
        return (int) (peso * 5);
    }
    
    private Integer calcularGrasas(Double peso) {
        // 0.8-1.2g por kg de peso corporal
        return (int) (peso * 1.0);
    }
    
    private String generarRecomendacionesAlimentarias(Double peso, Double imc, Integer proteinas, Integer carbs, Integer grasas) {
        StringBuilder rec = new StringBuilder();
        rec.append("📋 RECOMENDACIONES NUTRICIONALES PERSONALIZADAS\n\n");
        
        rec.append("✅ DISTRIBUCION DIARIA:\n");
        rec.append("• Proteínas: ").append(proteinas).append("g (pollo, pescado, huevos)\n");
        rec.append("• Carbohidratos: ").append(carbs).append("g (arroz, pasta, papas)\n");
        rec.append("• Grasas: ").append(grasas).append("g (aceite de oliva, aguacate, frutos secos)\n\n");
        
        rec.append("🥗 COMIDAS RECOMENDADAS:\n");
        rec.append("• Desayuno: Avena con proteína, huevos, frutos rojos\n");
        rec.append("• Almuerzo: Pechuga de pollo, arroz integral, verduras\n");
        rec.append("• Merienda: Yogurt griego, frutos secos, plátano\n");
        rec.append("• Cena: Pescado, batata, brócoli\n\n");
        
        rec.append("💧 HIDRATACIÓN:\n");
        rec.append("• Mínimo 3-4 litros de agua diarios\n");
        rec.append("• Aumentar 500ml por hora de entrenamiento intenso\n\n");
        
        if (imc < 18.5) {
            rec.append("⚠️ NOTA: Bajo peso detectado. Aumentar ingesta calórica en 300-500 cal/día\n");
        } else if (imc >= 30) {
            rec.append("⚠️ NOTA: Déficit calórico recomendado (300-500 cal/día menos)\n");
        }
        
        return rec.toString();
    }
    
    private String generarRecomendacionesEntrenamiento(String categoriaImc, Double peso) {
        StringBuilder rec = new StringBuilder();
        rec.append("🎯 RECOMENDACIONES DE ENTRENAMIENTO\n\n");
        
        rec.append("📅 FRECUENCIA:\n");
        rec.append("• Principiante: 3-4 sesiones/semana\n");
        rec.append("• Intermedio: 4-5 sesiones/semana\n");
        rec.append("• Avanzado: 5-6 sesiones/semana\n\n");
        
        rec.append("⏱️ INTENSIDAD:\n");
        rec.append("• Calentamiento: 10-15 minutos\n");
        rec.append("• Técnica: 20-30 minutos\n");
        rec.append("• Sparring: 15-25 minutos\n");
        rec.append("• Enfriamiento: 5-10 minutos\n\n");
        
        rec.append("💪 COMPLEMENTARIO:\n");
        rec.append("• Fuerza: 2 días/semana (brazos, core, piernas)\n");
        rec.append("• Flexibilidad: Diario (5-10 min)\n");
        rec.append("• Cardio: 2-3 días/semana\n\n");
        
        if ("Sobrepeso".equals(categoriaImc) || "Obesidad".equals(categoriaImc)) {
            rec.append("⚠️ NOTA: Priorizar sparring ligero y técnica. Aumentar cardio gradualmente.\n");
        } else if ("Bajo peso".equals(categoriaImc)) {
            rec.append("⚠️ NOTA: Enfoque en ganancia muscular. Aumentar carga de trabajo.\n");
        }
        
        return rec.toString();
    }
    
    public List<RecomendacionEntity> getRecomendacionesByUserName(String userName) {
        return recomendacionRepository.findByUserName(userName);
    }
    
    public Optional<RecomendacionEntity> getRecomendacionById(Long id) {
        return recomendacionRepository.findById(id);
    }
    
    public List<RecomendacionEntity> getAllRecomendaciones() {
        return recomendacionRepository.findAll();
    }
    
    public RecomendacionEntity updateRecomendacion(Long id, RecomendacionEntity updated) {
        Optional<RecomendacionEntity> existing = recomendacionRepository.findById(id);
        if (existing.isPresent()) {
            RecomendacionEntity rec = existing.get();
            rec.setObservaciones(updated.getObservaciones());
            return recomendacionRepository.save(rec);
        }
        return null;
    }
    
    public void deleteRecomendacion(Long id) {
        recomendacionRepository.deleteById(id);
    }
}
