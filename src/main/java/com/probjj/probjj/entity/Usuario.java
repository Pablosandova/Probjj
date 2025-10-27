package com.probjj.probjj.entity;
import java.util.Date;
public class Usuario {
   private String rut;
   private String nombre;
   private String apellido;
   private String password;
    private String email;
    private String rol; 
    private Date creatAt;
   private  Date updateAt;


   public Usuario(String rut, String nombre, String apellido, String password, String email, String rol, Date creatAt, Date updateAt) {
       this.rut = rut;
       this.nombre = nombre;
       this.apellido = apellido;
       this.password = password;
       this.email = email;
       this.rol = rol;
       this.creatAt = creatAt;
       this.updateAt = updateAt;
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
   public String getRol() {
       return rol;
   }
   public void setRol(String rol) {
       this.rol = rol;
   }
   public Date getCreatAt() {
       return creatAt;
   }
   public void setCreatAt(Date creatAt) {
       this.creatAt = creatAt;
   }
   public Date getUpdateAt() {
       return updateAt;
   }
   public void setUpdateAt(Date updateAt) {
       this.updateAt = updateAt;
   }
}
