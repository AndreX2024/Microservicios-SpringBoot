package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.entities.CartItems;
import dev.carloscastano.get.repository.CartItemRepository;
import dev.carloscastano.get.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository itemRepository;

    @Override
    public List<Cart> getAll() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Override
    public Cart obtenerCarrito(Long idUsuario) {
        return cartRepository.findByIdUsuario(idUsuario).orElse(null);
    }

    @Override
    public List<CartItems> obtenerItemsDelCarrito(Long idCarrito) {
        Cart carrito = cartRepository.findById(idCarrito).orElse(null);
        return (carrito != null) ? carrito.getItems() : List.of();
    }

    @Override
    public Cart crearCarrito(Cart carrito) {
        return cartRepository.save(carrito);
    }

    @Override
    public CartItems agregarItemAlCarrito(Long idCarrito, CartItems item) {
        Cart carrito = cartRepository.findById(idCarrito).orElse(null);
        if (carrito != null) {
            item.setCarrito(carrito);
            return itemRepository.save(item);
        }
        return null;
    }

}