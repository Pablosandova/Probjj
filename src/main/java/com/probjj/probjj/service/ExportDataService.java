package com.probjj.probjj.service;

import com.probjj.probjj.entity.Usuario;
import com.probjj.probjj.dao.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExportDataService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private static final String IMPORT_SQL_PATH = "src/main/resources/import.sql";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Exporta todos los usuarios de la base de datos a import.sql
     */
    public void exportarUsuariosAImportSql() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            
            StringBuilder sql = new StringBuilder();
            sql.append("-- Insertar usuarios de prueba\n");
            sql.append("INSERT INTO USUARIO (rut, nombre, apellido, password, email, direccion, edad, estatura, rol, creat_at, update_at) VALUES\n");
            
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario usuario = usuarios.get(i);
                sql.append(formatInsertStatement(usuario));
                
                if (i < usuarios.size() - 1) {
                    sql.append(",\n");
                } else {
                    sql.append(";\n");
                }
            }
            
            // Escribir a archivo
            Files.write(Paths.get(IMPORT_SQL_PATH), sql.toString().getBytes());
            System.out.println("✅ import.sql actualizado con " + usuarios.size() + " usuarios");
            
        } catch (IOException e) {
            System.err.println("❌ Error al exportar a import.sql: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Formatea una sentencia INSERT para un usuario
     */
    private String formatInsertStatement(Usuario usuario) {
        String creatAt = usuario.getCreatAt() != null 
            ? usuario.getCreatAt().format(DATE_FORMATTER) 
            : LocalDateTime.now().format(DATE_FORMATTER);
        
        String updateAt = usuario.getUpdateAt() != null 
            ? usuario.getUpdateAt().format(DATE_FORMATTER) 
            : LocalDateTime.now().format(DATE_FORMATTER);
        
        return String.format(
            "('%s', '%s', '%s', '%s', '%s', '%s', %d, %.1f, '%s', PARSEDATETIME('%s','yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('%s','yyyy-MM-dd HH:mm:ss'))",
            escaparSQL(usuario.getRut()),
            escaparSQL(usuario.getNombre()),
            escaparSQL(usuario.getApellido()),
            escaparSQL(usuario.getPassword()),
            escaparSQL(usuario.getEmail()),
            escaparSQL(usuario.getDireccion()),
            usuario.getEdad(),
            usuario.getEstatura() != null ? usuario.getEstatura() : 0.0,
            usuario.getRol(),
            creatAt,
            updateAt
        );
    }
    
    /**
     * Escapa caracteres especiales en SQL
     */
    private String escaparSQL(String valor) {
        if (valor == null) return "";
        return valor.replace("'", "''");
    }
}
