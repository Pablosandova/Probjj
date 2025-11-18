package com.probjj.probjj.dao;

import com.probjj.probjj.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRut(String rut);
    Optional<Usuario> findByEmailAndPassword(String email, String password);
    Optional<Usuario> findByRutAndPassword(String rut, String password);
}
