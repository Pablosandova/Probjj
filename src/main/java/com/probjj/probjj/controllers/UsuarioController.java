package com.probjj.probjj.controllers;

import com.probjj.probjj.entity.Usuario;
import com.probjj.probjj.service.UsuarioService;
import com.probjj.probjj.service.ExportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ExportDataService exportDataService;
    
    // Listar todos los usuarios
    @GetMapping("/")
    public String listar(Model model, HttpSession session) {
        String usuarioNombre = (String) session.getAttribute("usuarioNombre");
        
        if (usuarioNombre == null) {
            return "redirect:/login";
        }
        
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioNombre", usuarioNombre);
        
        return "usuario/lista";
    }
    
    // Página para editar usuario
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, HttpSession session) {
        String usuarioNombre = (String) session.getAttribute("usuarioNombre");
        
        if (usuarioNombre == null) {
            return "redirect:/login";
        }
        
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("usuarioNombre", usuarioNombre);
            return "usuario/editar";
        }
        return "redirect:/usuario/";
    }
    
    // Guardar cambios del usuario
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                            @RequestParam String nombre,
                            @RequestParam String apellido,
                            @RequestParam String email,
                            @RequestParam String direccion,
                            @RequestParam Integer edad,
                            @RequestParam Double estatura,
                            Model model,
                            HttpSession session) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(id);
            
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setEmail(email);
                usuario.setDireccion(direccion);
                usuario.setEdad(edad);
                usuario.setEstatura(estatura);
                
                usuarioService.actualizar(usuario);
                
                // Exportar datos actualizados a import.sql
                exportDataService.exportarUsuariosAImportSql();
                
                // Actualizar sesión con nuevos datos
                session.setAttribute("usuarioNombre", nombre);
                session.setAttribute("usuarioEmail", email);
                session.setAttribute("usuarioEdad", edad);
                session.setAttribute("usuarioEstatura", estatura);
                
                model.addAttribute("mensaje", "✅ Usuario actualizado correctamente");
                model.addAttribute("usuario", usuario);
                
                return "usuario/editar";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        
        return "redirect:/usuario/";
    }
    
    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        try {
            usuarioService.eliminar(id);
            
            // Exportar datos actualizados a import.sql
            exportDataService.exportarUsuariosAImportSql();
            
            // Si el usuario eliminado es el mismo de la sesión, cerrar sesión
            Long usuarioIdSession = (Long) session.getAttribute("usuarioId");
            if (usuarioIdSession != null && usuarioIdSession.equals(id)) {
                session.invalidate();
                return "redirect:/login";
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
        
        return "redirect:/usuario/";
    }
    
    // Ver perfil de usuario
    @GetMapping("/perfil/{id}")
    public String verPerfil(@PathVariable Long id, Model model, HttpSession session) {
        String usuarioNombre = (String) session.getAttribute("usuarioNombre");
        
        if (usuarioNombre == null) {
            return "redirect:/login";
        }
        
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("usuarioNombre", usuarioNombre);
            return "usuario/perfil";
        }
        
        return "redirect:/usuario/";
    }
}
