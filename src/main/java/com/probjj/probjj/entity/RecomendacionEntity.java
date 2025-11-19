package com.probjj.probjj.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recomendaciones")
public class RecomendacionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "peso")
    private Double peso;
    
    @Column(name = "estatura")
    private Double estatura;
    
    @Column(name = "imc")
    private Double imc;
    
    @Column(name = "categoria_imc")
    private String categoriaImc;
    
    @Column(name = "calorias_diarias")
    private Integer caloriasDiarias;
    
    @Column(name = "proteinas_gramos")
    private Integer proteinasGramos;
    
    @Column(name = "carbohidratos_gramos")
    private Integer carbohidratosGramos;
    
    @Column(name = "grasas_gramos")
    private Integer grasasGramos;
    
    @Column(columnDefinition = "TEXT")
    private String recomendacionesAlimentarias;
    
    @Column(columnDefinition = "TEXT")
    private String recomendacionesEntrenamiento;
    
    @Column(columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters y Setters
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
    
    public Double getPeso() {
        return peso;
    }
    
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    
    public Double getEstatura() {
        return estatura;
    }
    
    public void setEstatura(Double estatura) {
        this.estatura = estatura;
    }
    
    public Double getImc() {
        return imc;
    }
    
    public void setImc(Double imc) {
        this.imc = imc;
    }
    
    public String getCategoriaImc() {
        return categoriaImc;
    }
    
    public void setCategoriaImc(String categoriaImc) {
        this.categoriaImc = categoriaImc;
    }
    
    public Integer getCaloriasDiarias() {
        return caloriasDiarias;
    }
    
    public void setCaloriasDiarias(Integer caloriasDiarias) {
        this.caloriasDiarias = caloriasDiarias;
    }
    
    public Integer getProteinasGramos() {
        return proteinasGramos;
    }
    
    public void setProteinasGramos(Integer proteinasGramos) {
        this.proteinasGramos = proteinasGramos;
    }
    
    public Integer getCarbohidratosGramos() {
        return carbohidratosGramos;
    }
    
    public void setCarbohidratosGramos(Integer carbohidratosGramos) {
        this.carbohidratosGramos = carbohidratosGramos;
    }
    
    public Integer getGrasasGramos() {
        return grasasGramos;
    }
    
    public void setGrasasGramos(Integer grasasGramos) {
        this.grasasGramos = grasasGramos;
    }
    
    public String getRecomendacionesAlimentarias() {
        return recomendacionesAlimentarias;
    }
    
    public void setRecomendacionesAlimentarias(String recomendacionesAlimentarias) {
        this.recomendacionesAlimentarias = recomendacionesAlimentarias;
    }
    
    public String getRecomendacionesEntrenamiento() {
        return recomendacionesEntrenamiento;
    }
    
    public void setRecomendacionesEntrenamiento(String recomendacionesEntrenamiento) {
        this.recomendacionesEntrenamiento = recomendacionesEntrenamiento;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
