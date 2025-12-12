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
            System.out.println("=== INTENTO DE LOGIN ===");
            System.out.println("Identifier: " + identifier);
            System.out.println("Password length: " + password.length());
            
            Optional<Usuario> usuario = usuarioService.autenticar(identifier, password);
            
            if (usuario.isPresent()) {
                System.out.println("✓ Usuario encontrado: " + usuario.get().getNombre());
                session.setAttribute("usuarioId", usuario.get().getId());
                session.setAttribute("usuarioNombre", usuario.get().getNombre());
                session.setAttribute("usuarioEmail", usuario.get().getEmail());
                session.setAttribute("usuarioEdad", usuario.get().getEdad());
                session.setAttribute("usuarioEstatura", usuario.get().getEstatura());
                return "redirect:/perfil";
            } else {
                System.out.println("✗ Usuario no encontrado con credenciales proporcionadas");
                model.addAttribute("error", "Correo/RUT o contraseña incorrectos");
                return "auth/login";
            }
        } catch (Exception e) {
            System.out.println("✗ Error en login: " + e.getMessage());
            e.printStackTrace();
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
                          @RequestParam(required = false) String apellido,
                          @RequestParam String email,
                          @RequestParam(required = false) String direccion,
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
            
            // Validar que los campos obligatorios no estén vacíos
            if (rut == null || rut.trim().isEmpty() || 
                nombre == null || nombre.trim().isEmpty() || 
                email == null || email.trim().isEmpty() || 
                password == null || password.isEmpty()) {
                model.addAttribute("error", "Por favor completa todos los campos obligatorios");
                return "auth/register";
            }
            
            // Validar formato de email básico
            if (!email.contains("@") || !email.contains(".")) {
                model.addAttribute("error", "Por favor ingresa un email válido");
                return "auth/register";
            }
            
            // Validar edad
            if (edad == null || edad < 13 || edad > 120) {
                model.addAttribute("error", "Por favor ingresa una edad válida (13-120 años)");
                return "auth/register";
            }
            
            // Validar estatura
            if (estatura == null || estatura < 100 || estatura > 250) {
                model.addAttribute("error", "Por favor ingresa una estatura válida (100-250 cm)");
                return "auth/register";
            }
            
            // Validar longitud de contraseña
            if (password.length() < 6) {
                model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                return "auth/register";
            }
            
            // Crear nuevo usuario con valores seguros
            Usuario nuevoUsuario = new Usuario(
                rut.trim(), 
                nombre.trim(), 
                apellido != null ? apellido.trim() : "", 
                password, 
                email.trim(), 
                direccion != null ? direccion.trim() : "", 
                edad
            );
            nuevoUsuario.setEstatura(estatura);
            
            System.out.println("=== REGISTRO DE USUARIO ===");
            System.out.println("Email: " + email);
            System.out.println("RUT: " + rut);
            System.out.println("Password length: " + password.length());
            
            Usuario usuarioGuardado = usuarioService.registrar(nuevoUsuario);
            System.out.println("✓ Usuario guardado con ID: " + usuarioGuardado.getId());
            System.out.println("✓ Email: " + usuarioGuardado.getEmail());
            System.out.println("✓ RUT: " + usuarioGuardado.getRut());
            
            // Exportar datos actualizados a import.sql
            // exportDataService.exportarUsuariosAImportSql(); // Comentado temporalmente
            
            // Crear sesión automáticamente
            session.setAttribute("usuarioId", usuarioGuardado.getId());
            session.setAttribute("usuarioNombre", usuarioGuardado.getNombre());
            session.setAttribute("usuarioEmail", usuarioGuardado.getEmail());
            session.setAttribute("usuarioEdad", usuarioGuardado.getEdad());
            session.setAttribute("usuarioEstatura", usuarioGuardado.getEstatura());
            session.setAttribute("registroExitoso", true);
            
            model.addAttribute("success", "¡Usuario creado exitosamente! Redirigiendo a tu perfil...");
            return "redirect:/perfil";
        } catch (RuntimeException e) {
            System.err.println("✗ Error RuntimeException: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        } catch (Exception e) {
            System.err.println("✗ Error Exception: " + e.getMessage());
            e.printStackTrace();
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
