package com.probjj.probjj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity(name = "nutrition_data")
public class NutritionDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name")
    private String userName;
    
    private Integer edad;
    private Double estatura; // en cm
    private Double peso; // en kg
    private Integer tiempoEntrenamiento; // en minutos
    
    private Double calories;
    private Double proteins;
    private Double carbohydrates;
    private Double caloriesGoal;
    private Double proteinsGoal;
    private Double carbohydratesGoal;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public NutritionDataEntity() {
    }

    public NutritionDataEntity(String userName, Integer edad, Double estatura, Double peso, Integer tiempoEntrenamiento) {
        this.userName = userName;
        this.edad = edad;
        this.estatura = estatura;
        this.peso = peso;
        this.tiempoEntrenamiento = tiempoEntrenamiento;
    }

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getEstatura() {
        return estatura;
    }

    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getTiempoEntrenamiento() {
        return tiempoEntrenamiento;
    }

    public void setTiempoEntrenamiento(Integer tiempoEntrenamiento) {
        this.tiempoEntrenamiento = tiempoEntrenamiento;
    }

    public double getCalories() {
        return calories != null ? calories : 0.0;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins != null ? proteins : 0.0;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates != null ? carbohydrates : 0.0;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getCaloriesGoal() {
        return caloriesGoal != null ? caloriesGoal : 0.0;
    }

    public void setCaloriesGoal(Double caloriesGoal) {
        this.caloriesGoal = caloriesGoal;
    }

    public double getProteinsGoal() {
        return proteinsGoal != null ? proteinsGoal : 0.0;
    }

    public void setProteinsGoal(Double proteinsGoal) {
        this.proteinsGoal = proteinsGoal;
    }

    public double getCarbohydratesGoal() {
        return carbohydratesGoal != null ? carbohydratesGoal : 0.0;
    }

    public void setCarbohydratesGoal(Double carbohydratesGoal) {
        this.carbohydratesGoal = carbohydratesGoal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
