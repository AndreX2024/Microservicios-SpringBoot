package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.repository.ProductRepository;
import dev.carloscastano.get.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService service;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("catalog")
    public List<Product> getAll() {return service.getAll();}

}
