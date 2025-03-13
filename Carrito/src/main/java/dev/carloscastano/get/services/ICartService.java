package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> getAll();
    Cart obtenerCarrito(Long id_usuario);
}
