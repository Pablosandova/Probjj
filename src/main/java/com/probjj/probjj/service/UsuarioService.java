package com.probjj.probjj.service;

import com.probjj.probjj.entity.Usuario;
import com.probjj.probjj.dao.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar nuevo usuario
    public Usuario registrar(Usuario usuario) {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        // Verificar si el RUT ya existe
        if (usuarioRepository.findByRut(usuario.getRut()).isPresent()) {
            throw new RuntimeException("El RUT ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    // Autenticar usuario
    public Optional<Usuario> autenticar(String identifier, String password) {
        System.out.println("=== AUTENTICACION ===");
        System.out.println("Buscando por identifier: " + identifier);
        
        // Intentar con email
        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmailAndPassword(identifier, password);
        if (usuarioEmail.isPresent()) {
            System.out.println("✓ Usuario encontrado por EMAIL");
            return usuarioEmail;
        }
        
        System.out.println("Usuario no encontrado por email, intentando con RUT...");
        
        // Intentar con RUT
        Optional<Usuario> usuarioRut = usuarioRepository.findByRutAndPassword(identifier, password);
        if (usuarioRut.isPresent()) {
            System.out.println("✓ Usuario encontrado por RUT");
        } else {
            System.out.println("✗ Usuario no encontrado por RUT tampoco");
            // Debug: mostrar todos los usuarios
            List<Usuario> todos = usuarioRepository.findAll();
            System.out.println("Total usuarios en DB: " + todos.size());
            for (Usuario u : todos) {
                System.out.println("  - Email: " + u.getEmail() + ", RUT: " + u.getRut());
            }
        }
        return usuarioRut;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obtener usuario por email
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Obtener usuario por RUT
    public Optional<Usuario> obtenerPorRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }

    // Actualizar usuario
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
