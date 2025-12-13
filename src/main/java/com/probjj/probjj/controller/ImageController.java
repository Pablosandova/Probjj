package com.probjj.probjj.controller;

import com.probjj.probjj.model.Image;
import com.probjj.probjj.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ImageController {
    
    @Autowired
    private ImageRepository imageRepository;
    
    /**
     * POST /api/upload - Subir una imagen
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "El archivo está vacío");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Validar tipo de archivo (solo imágenes)
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("success", false);
                response.put("message", "El archivo debe ser una imagen");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Crear y guardar la imagen
            Image image = new Image(
                file.getOriginalFilename(),
                file.getBytes(),
                contentType
            );
            
            Image savedImage = imageRepository.save(image);
            
            // Respuesta exitosa
            response.put("success", true);
            response.put("message", "Imagen subida exitosamente");
            response.put("id", savedImage.getId());
            response.put("name", savedImage.getName());
            response.put("uploadDate", savedImage.getUploadDate().toString());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al subir la imagen: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * GET /api/images - Listar todas las imágenes (solo metadata)
     */
    @GetMapping("/images")
    public ResponseEntity<List<Map<String, Object>>> getAllImages() {
        try {
            List<Image> images = imageRepository.findAll();
            
            // Retornar solo metadata (sin los bytes de la imagen)
            List<Map<String, Object>> imageMetadata = images.stream()
                .map(img -> {
                    Map<String, Object> metadata = new HashMap<>();
                    metadata.put("id", img.getId());
                    metadata.put("name", img.getName());
                    metadata.put("contentType", img.getContentType());
                    metadata.put("uploadDate", img.getUploadDate().toString());
                    metadata.put("size", img.getData() != null ? img.getData().length : 0);
                    return metadata;
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(imageMetadata);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * GET /api/images/{id} - Obtener una imagen específica con sus bytes
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        try {
            Optional<Image> imageOptional = imageRepository.findById(id);
            
            if (imageOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Image image = imageOptional.get();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(image.getContentType()));
            headers.setContentDispositionFormData("inline", image.getName());
            
            return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * DELETE /api/images/{id} - Eliminar una imagen
     */
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Image> imageOptional = imageRepository.findById(id);
            
            if (imageOptional.isEmpty()) {
                response.put("success", false);
                response.put("message", "Imagen no encontrada");
                return ResponseEntity.notFound().build();
            }
            
            imageRepository.deleteById(id);
            
            response.put("success", true);
            response.put("message", "Imagen eliminada exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar la imagen: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
