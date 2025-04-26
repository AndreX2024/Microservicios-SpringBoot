package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.entities.CartItems;

import java.util.List;

public interface ICartService {
    List<Cart> getAll();
    Cart obtenerCarrito(Long id_usuario);
    List<CartItems> obtenerItemsDelCarrito(Long idCarrito);
    Cart crearCarrito(Cart carrito);
    CartItems agregarItemAlCarrito(Long idCarrito, CartItems item);
}