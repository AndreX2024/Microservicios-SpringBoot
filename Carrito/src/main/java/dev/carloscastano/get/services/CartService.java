package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository repository;

    @Override
    public List<Cart> getAll() {
        return (List<Cart>) repository.findAll();
    }

    @Override
    public Cart obtenerCarrito(Long id_usuario) {
        return repository.findByIdUsuario(id_usuario).orElse(null);
    }
}
