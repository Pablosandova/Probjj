package com.probjj.probjj.models;

import java.time.LocalDateTime;

public class NutritionData {
    private Long id;
    private double calories;
    private double proteins;
    private double carbohydrates;
    private LocalDateTime dateTime;
    private Long userId;
    private double caloriesGoal;
    private double proteinsGoal;
    private double carbohydratesGoal;

    public NutritionData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getCaloriesGoal() {
        return caloriesGoal;
    }

    public void setCaloriesGoal(double caloriesGoal) {
        this.caloriesGoal = caloriesGoal;
    }

    public double getProteinsGoal() {
        return proteinsGoal;
    }

    public void setProteinsGoal(double proteinsGoal) {
        this.proteinsGoal = proteinsGoal;
    }

    public double getCarbohydratesGoal() {
        return carbohydratesGoal;
    }

    public void setCarbohydratesGoal(double carbohydratesGoal) {
        this.carbohydratesGoal = carbohydratesGoal;
    }
}
