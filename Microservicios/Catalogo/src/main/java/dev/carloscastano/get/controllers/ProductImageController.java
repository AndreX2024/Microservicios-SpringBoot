package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.ImageProduct;
import dev.carloscastano.get.repository.ImageProductRepository;
import dev.carloscastano.get.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/productImages")
public class ProductImageController {

    @Autowired
    private IProductService service;

    @Autowired
    private ImageProductRepository imageProductRepository;

    @Value("${image.upload.dir}")
    private String uploadDir;

    @GetMapping("/{id}/images")
    public ResponseEntity<List<String>> getImagesByProductId_producto(@PathVariable Long id) {
        List<ImageProduct> images = service.getImagesByProduct_IdProducto(id);

        if (images.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Extraer solo las URLs
        List<String> imageUrls = images.stream()
                .map(ImageProduct::getUrlImagen)
                .toList();

        return ResponseEntity.ok(imageUrls);
    }


    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<String> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {
            String imageUrl = service.saveImageFile(id, file);
            return ResponseEntity.ok("Imagen guardada en BD: " + imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al guardar la imagen.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Ej: Producto no existe
        }
    }

    @PutMapping("/{imageId}/updateImageFile")
    public ResponseEntity<String> updateProductImageFile(
            @PathVariable Long imageId,
            @RequestParam("file") MultipartFile file) {

        Optional<ImageProduct> imageOptional = imageProductRepository.findById(imageId);

        if (imageOptional.isPresent()) {
            ImageProduct existingImage = imageOptional.get();

            try {
                String uploadDir = "uploads/";

                // Eliminar imagen anterior
                String oldImagePath = existingImage.getUrlImagen().replace("/catalog/products/images/", "");
                Path oldFilePath = Paths.get(uploadDir, oldImagePath);
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath);
                }

                // Crear directorio si no existe
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generar nombre único para la nueva imagen
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                String fileName = UUID.randomUUID().toString() + fileExtension;

                // Guardar nueva imagen
                Path newFilePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

                // Actualizar la URL
                String newUrl = "/catalog/products/images/" + fileName;
                existingImage.setUrlImagen(newUrl);
                imageProductRepository.save(existingImage);

                return ResponseEntity.ok("Imagen actualizada y anterior eliminada: " + newUrl);

            } catch (IOException e) {
                return ResponseEntity.status(500).body("Error al actualizar la imagen.");
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imagen no encontrada con ID: " + imageId);
        }
    }

}
