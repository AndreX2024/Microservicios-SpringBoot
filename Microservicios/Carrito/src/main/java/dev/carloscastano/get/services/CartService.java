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
        return repository.findAll();
    }

    @Override
    public Cart obtenerCarrito(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario).orElse(null);
    }
}
