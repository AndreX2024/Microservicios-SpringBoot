package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.ImageProduct;
import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> getAll();
    Optional<Product> getByName(String name);
    List<Product> getByCategoryName(String categoryName);
    List<Product> getBySupplierName(String supplierName);
    List<Product> getDiscountedProducts();
    List<Product> getAvailableStock();
    List<ImageProduct> getImagesByProduct_IdProducto(Long productId);
    String saveImageFile(Long productId, MultipartFile file) throws IOException;


    Product saveProduct(Product product);
    ImageProduct addProductImage(Long productId, String imageUrl);
    Inventory updateProductStock(Long productId, String sizeName, String colorName, Integer stock);
}
