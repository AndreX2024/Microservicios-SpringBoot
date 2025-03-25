package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }
}
