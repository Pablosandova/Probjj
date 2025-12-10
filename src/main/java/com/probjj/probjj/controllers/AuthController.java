package com.probjj.probjj.controllers;

import com.probjj.probjj.entity.Usuario;
import com.probjj.probjj.service.UsuarioService;
import com.probjj.probjj.service.ExportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ExportDataService exportDataService;
    
    // Página de login
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    
    // Login
    @PostMapping("/login")
    public String login(@RequestParam String identifier, @RequestParam String password, HttpSession session, Model model) {
        try {
            Optional<Usuario> usuario = usuarioService.autenticar(identifier, password);
            
            if (usuario.isPresent()) {
                session.setAttribute("usuarioId", usuario.get().getId());
                session.setAttribute("usuarioNombre", usuario.get().getNombre());
                session.setAttribute("usuarioEmail", usuario.get().getEmail());
                session.setAttribute("usuarioEdad", usuario.get().getEdad());
                session.setAttribute("usuarioEstatura", usuario.get().getEstatura());
                return "redirect:/perfil";
            } else {
                model.addAttribute("error", "Correo/RUT o contraseña incorrectos");
                return "auth/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }
    
    // Página de registro
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
    
    // Registrar nuevo usuario
    @PostMapping("/register")
    public String register(@RequestParam String rut, 
                          @RequestParam String nombre, 
                          @RequestParam String apellido,
                          @RequestParam String email,
                          @RequestParam String direccion,
                          @RequestParam Integer edad,
                          @RequestParam Double estatura,
                          @RequestParam String password,
                          @RequestParam String confirmPassword,
                          HttpSession session,
                          Model model) {
        try {
            // Validar que las contraseñas coincidan
            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Las contraseñas no coinciden");
                return "auth/register";
            }
            
            // Validar que los campos no estén vacíos
            if (rut.isEmpty() || nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                model.addAttribute("error", "Por favor completa todos los campos");
                return "auth/register";
            }
            
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario(rut, nombre, apellido, password, email, direccion, edad);
            nuevoUsuario.setEstatura(estatura);
            usuarioService.registrar(nuevoUsuario);
            
            // Exportar datos actualizados a import.sql
            exportDataService.exportarUsuariosAImportSql();
            
            // Crear sesión automáticamente
            session.setAttribute("usuarioId", nuevoUsuario.getId());
            session.setAttribute("usuarioNombre", nuevoUsuario.getNombre());
            session.setAttribute("usuarioEmail", nuevoUsuario.getEmail());
            session.setAttribute("usuarioEdad", nuevoUsuario.getEdad());
            session.setAttribute("usuarioEstatura", nuevoUsuario.getEstatura());
            
            return "redirect:/perfil";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "auth/register";
        }
    }
    
    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
