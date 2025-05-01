package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.ImageProduct;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageProductService {
    ImageProduct saveImage(MultipartFile file, Long productId) throws IOException;
    List<ImageProduct> getImagesByProductId(Long productId);
    void deleteImage(Long imageId) throws IOException;
    ImageProduct getImageById(Long imageId);
}