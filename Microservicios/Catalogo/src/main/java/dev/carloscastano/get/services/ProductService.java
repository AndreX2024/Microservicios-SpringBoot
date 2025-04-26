package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.*;
import dev.carloscastano.get.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageProductRepository imageProductRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> getByName(String name) {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> product.getNombre().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Product> getByCategoryName(String categoryName) {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> product.getCategoria().getNombre().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());
    }


    @Override
    public List<Product> getBySupplierName(String supplierName) {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> product.getProveedor().getNombre().equalsIgnoreCase(supplierName))
                .collect(Collectors.toList());
    }


    @Override
    public List<Product> getDiscountedProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(Product::getDescuentoActivo)
                .collect(Collectors.toList());
    }


    @Override
    public List<Product> getAvailableStock() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> product.getInventarios().stream().anyMatch(inv -> inv.getStock() > 0))
                .collect(Collectors.toList());
    }


    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public ImageProduct addProductImage(Long productId, String imageUrl) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            ImageProduct imageProduct = new ImageProduct();
            imageProduct.setUrlImagen(imageUrl);
            imageProduct.setProducto(product.get());
            return imageProductRepository.save(imageProduct);
        }
        return null;
    }

    @Override
    public List<ImageProduct> getImagesByProduct_IdProducto(Long productId) {
        return imageProductRepository.findByProducto_IdProducto(productId);
    }


    @Override
    public Inventory updateProductStock(Long productId, String sizeName, String colorName, Integer stock) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<Size> size = sizeRepository.findByNombre(sizeName);
        Optional<Color> color = colorRepository.findByNombre(colorName);

        if (product.isPresent() && size.isPresent() && color.isPresent()) {
            Inventory inventory = new Inventory();
            inventory.setProducto(product.get());
            inventory.setTalla(size.get());
            inventory.setColor(color.get());
            inventory.setStock(stock);
            return inventoryRepository.save(inventory);
        }
        return null;
    }

    @Override
    public String saveImageFile(Long productId, MultipartFile file) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new FileNotFoundException("Producto no encontrado con ID: " + productId);
        }

        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setProducto(optionalProduct.get());
        imageProduct.setUrlImagen("/catalog/products/images/" + fileName);
        imageProductRepository.save(imageProduct);

        return imageProduct.getUrlImagen();
    }


}
