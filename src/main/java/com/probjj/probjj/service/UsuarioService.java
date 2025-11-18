package com.probjj.probjj.service;

import com.probjj.probjj.entity.Usuario;
import com.probjj.probjj.dao.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        // Intentar con email
        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmailAndPassword(identifier, password);
        if (usuarioEmail.isPresent()) {
            return usuarioEmail;
        }
        // Intentar con RUT
        return usuarioRepository.findByRutAndPassword(identifier, password);
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
