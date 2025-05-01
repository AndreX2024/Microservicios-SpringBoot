package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.ImageProduct;
import dev.carloscastano.get.repository.ImageProductRepository;
import dev.carloscastano.get.services.IImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products/{productId}/images")
public class ImageProductController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private IImageProductService imageProductService;

    @Autowired
    private ImageProductRepository imageProductRepository;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<ImageProduct> uploadImage(@PathVariable Long productId,
                                                    @RequestParam("file") MultipartFile file) {
        try {
            ImageProduct savedImage = imageProductService.saveImage(file, productId);
            return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ImageProduct>> getProductImages(@PathVariable Long productId) {
        List<ImageProduct> images = imageProductService.getImagesByProductId(productId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageProduct> getImageById(@PathVariable Long imageId) {
        ImageProduct image = imageProductService.getImageById(imageId);
        if (image != null) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<ImageProduct> updateImage(@PathVariable Long productId,
                                                    @PathVariable Long imageId,
                                                    @RequestParam("file") MultipartFile file) {
        try {
            ImageProduct existingImage = imageProductRepository.findById(imageId)
                    .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

            // Eliminar el archivo antiguo si existe
            Path oldFilePath = Paths.get(uploadDir).resolve(existingImage.getUrlImagen().replace("/catalog/products/images/", ""));
            Files.deleteIfExists(oldFilePath);

            // Guardar el nuevo archivo
            if (!file.isEmpty()) {
                String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path newFilePath = Paths.get(uploadDir).resolve(filename).normalize().toAbsolutePath();

                if (!newFilePath.getParent().toFile().exists()) {
                    Files.createDirectories(newFilePath.getParent());
                }

                try (var inputStream = file.getInputStream()) {
                    Files.copy(inputStream, newFilePath, StandardCopyOption.REPLACE_EXISTING);
                    existingImage.setUrlImagen("/catalog/products/images/" + filename);
                    ImageProduct updatedImage = imageProductRepository.save(existingImage);
                    return new ResponseEntity<>(updatedImage, HttpStatus.OK);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            } else {
                return new ResponseEntity<>(existingImage, HttpStatus.OK);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        try {
            ImageProduct imageProduct = imageProductRepository.findById(imageId)
                    .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
            Path fileToDeletePath = Paths.get(uploadDir).resolve(imageProduct.getUrlImagen().replace("/catalog/products/images/", ""));
            Files.deleteIfExists(fileToDeletePath);
            imageProductRepository.deleteById(imageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}