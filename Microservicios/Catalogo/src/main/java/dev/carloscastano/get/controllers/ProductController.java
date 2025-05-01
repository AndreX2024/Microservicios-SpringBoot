package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.entities.ImageProduct;
import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Product> getProductByNombre(@PathVariable String nombre) {
        Optional<Product> product = productService.findByNombre(nombre);
        return product.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.findByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<Product>> getProductsBySupplierId(@PathVariable Long supplierId) {
        List<Product> products = productService.findBySupplierId(supplierId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}/inventories")
    public ResponseEntity<List<Inventory>> getProductInventories(@PathVariable Long productId) {
        List<Inventory> inventories = productService.findById(productId)
                .map(Product::getInventarios)
                .orElse(List.of());
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
        Product newProduct = productService.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Validated @RequestBody Product updatedProduct) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isPresent()) {
            updatedProduct.setIdProducto(id);
            Product savedProduct = productService.save(updatedProduct);
            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            Product updatedProduct = productService.partialUpdate(productToUpdate, updates);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}