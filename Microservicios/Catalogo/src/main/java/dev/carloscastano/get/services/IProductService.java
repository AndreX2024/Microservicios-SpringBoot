package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
}
