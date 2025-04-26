package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Category;
import dev.carloscastano.get.entities.ImageProduct;
import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.repository.ProductRepository;
import dev.carloscastano.get.services.ICategoryService;
import dev.carloscastano.get.services.IProductService;
import dev.carloscastano.get.services.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("catalog/products")
public class ProductController {

    @Autowired
    private IProductService service;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private ProductRepository productRepository;

    // Métodos GET
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/product/{name}")
    public Optional<Product> getByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @GetMapping("/category/{categoryName}")
    public List<Product> getByCategoryName(@PathVariable String categoryName) {
        return service.getByCategoryName(categoryName);
    }

    @GetMapping("/supplier/{supplierName}")
    public List<Product> getBySupplierName(@PathVariable String supplierName) {
        return service.getBySupplierName(supplierName);
    }

    @GetMapping("/discounted")
    public List<Product> getDiscountedProducts() {
        return service.getDiscountedProducts();
    }

    @GetMapping("/available-stock")
    public List<Product> getAvailableStock() {
        return service.getAvailableStock();
    }


    // Métodos POST
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PostMapping("/{productId}/add-image")
    public ImageProduct addImage(@PathVariable Long productId, @RequestBody String imageUrl) {
        return service.addProductImage(productId, imageUrl);
    }

    @PostMapping("/{productId}/update-stock")
    public Inventory updateStock(@PathVariable Long productId, @RequestParam String size, @RequestParam String color, @RequestParam Integer stock) {
        return service.updateProductStock(productId, size, color, stock);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = service.saveImageFile(id, file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen: " + e.getMessage());
        }
    }



    // Métodos PUT
    @PutMapping("/{id}/update")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody Product product) {

        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product existingProduct = productData.get();

            // Update all product fields
            existingProduct.setNombre(product.getNombre());
            existingProduct.setDescripcion(product.getDescripcion());
            existingProduct.setCategoria(product.getCategoria());
            existingProduct.setPrecio(product.getPrecio());
            existingProduct.setDescuentoActivo(product.getDescuentoActivo());
            existingProduct.setPorcentajeDescuento(product.getPorcentajeDescuento());

            // Update images only if provided
            if (product.getImagenes() != null && !product.getImagenes().isEmpty()) {
                existingProduct.setImagenes(product.getImagenes());
            }

            return new ResponseEntity<>(productRepository.save(existingProduct), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{id}/update")
    public ResponseEntity<Product> patchProduct(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product existingProduct = productData.get();

            // Update only provided fields
            if (updates.containsKey("nombre")) {
                existingProduct.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("descripcion")) {
                existingProduct.setDescripcion((String) updates.get("descripcion"));
            }
            if (updates.containsKey("precio")) {
                existingProduct.setPrecio(Double.valueOf(updates.get("precio").toString()));
            }
            if (updates.containsKey("descuentoActivo")) {
                existingProduct.setDescuentoActivo(Boolean.valueOf(updates.get("descuentoActivo").toString()));
            }
            if (updates.containsKey("porcentajeDescuento")) {
                existingProduct.setPorcentajeDescuento(Double.valueOf(updates.get("porcentajeDescuento").toString()));
            }
            if (updates.containsKey("categoria")) {
                existingProduct.setCategoria((Category) updates.get("categoria"));
            }
            if (updates.containsKey("imagenes")) {
                existingProduct.setImagenes((List<ImageProduct>) updates.get("imagenes"));
            }

            return new ResponseEntity<>(productRepository.save(existingProduct), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
