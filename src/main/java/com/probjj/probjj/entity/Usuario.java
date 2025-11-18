package com.probjj.probjj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity
public class Usuario {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String rut;
   private String nombre;
   private String apellido;
   private String password;
   private String email;
   private String direccion;
   private Integer edad;
   private String rol;
   private LocalDateTime creatAt;
   private LocalDateTime updateAt;

   public Usuario() {
   }

   public Usuario(String rut, String nombre, String apellido, String password, String email, String direccion, Integer edad) {
       this.rut = rut;
       this.nombre = nombre;
       this.apellido = apellido;
       this.password = password;
       this.email = email;
       this.direccion = direccion;
       this.edad = edad;
       this.rol = "USER";
   }

   // Getters and Setters
   public String getRut() {
       return rut;
   }
   public void setRut(String rut) {
       this.rut = rut;
   }
   public String getNombre() {
       return nombre;
   }
   public void setNombre(String nombre) {
       this.nombre = nombre;
   }
   public String getApellido() {
       return apellido;
   }
   public void setApellido(String apellido) {
       this.apellido = apellido;
   }
   public String getPassword() {
       return password;
   }
   public void setPassword(String password) {
       this.password = password;
   }
   public String getEmail() {
       return email;
   }
   public void setEmail(String email) {
       this.email = email;
   }
   public String getDireccion() {
       return direccion;
   }
   public void setDireccion(String direccion) {
       this.direccion = direccion;
   }
   public Integer getEdad() {
       return edad;
   }
   public void setEdad(Integer edad) {
       this.edad = edad;
   }
   public String getRol() {
       return rol;
   }
   public void setRol(String rol) {
       this.rol = rol;
   }
   public LocalDateTime getCreatAt() {
       return creatAt;
   }
   public void setCreatAt(LocalDateTime creatAt) {
       this.creatAt = creatAt;
   }
   public LocalDateTime getUpdateAt() {
       return updateAt;
   }
   public void setUpdateAt(LocalDateTime updateAt) {
       this.updateAt = updateAt;
   }
   public Long getId() {
       return id;
   }
   public void setId(Long id) {
       this.id = id;
   }
   @PrePersist
   protected void prePersist() {
       this.creatAt = LocalDateTime.now();
   }
   @PreUpdate
   protected void preUpdate() {
       this.updateAt = LocalDateTime.now();
   }
}
