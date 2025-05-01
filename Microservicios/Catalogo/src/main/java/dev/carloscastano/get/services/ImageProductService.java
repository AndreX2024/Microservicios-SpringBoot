package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.ImageProduct;
import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.repository.ImageProductRepository;
import dev.carloscastano.get.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ImageProductService implements IImageProductService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ImageProductRepository imageProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ImageProduct saveImage(MultipartFile file, Long productId) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path destinationFile = Paths.get(uploadDir).resolve(Paths.get(filename)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().toFile().exists()) {
            Files.createDirectories(destinationFile.getParent());
        }

        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

            ImageProduct imageProduct = new ImageProduct();
            imageProduct.setUrlImagen("/catalog/products/images/" + filename);
            imageProduct.setProducto(product);
            return imageProductRepository.save(imageProduct);

        } catch (IOException e) {
            throw new IOException("Failed to store file " + filename, e);
        }
    }

    @Override
    public List<ImageProduct> getImagesByProductId(Long productId) {
        return imageProductRepository.findByProducto_IdProducto(productId);
    }

    @Override
    public void deleteImage(Long imageId) throws IOException {
        ImageProduct imageProduct = imageProductRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
        Path fileToDeletePath = Paths.get(uploadDir).resolve(imageProduct.getUrlImagen());
        Files.deleteIfExists(fileToDeletePath);
        imageProductRepository.deleteById(imageId);
    }

    @Override
    public ImageProduct getImageById(Long imageId) {
        return imageProductRepository.findById(imageId)
                .orElse(null);
    }
}