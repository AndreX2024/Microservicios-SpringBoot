package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAll() {
        List<Product> productos = (List<Product>) repository.findAll();
        productos.forEach(producto -> {
            if (producto.getCategoria() != null) producto.getCategoria().getNombre(); // Fuerza la carga
            if (producto.getProveedor() != null) producto.getProveedor().getNombre();
            if (producto.getImagen() != null) producto.getImagen().getUrlImagen();
        });
        return productos;
    }
}
