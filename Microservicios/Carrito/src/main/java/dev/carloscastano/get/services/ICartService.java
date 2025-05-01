package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Cart;
import dev.carloscastano.get.entities.CartItems;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICartService {
    Cart save(Cart cart);
    Optional<Cart> findById(Long id);
    List<Cart> findAll();
    void deleteById(Long id);
    Optional<Cart> findByUserId(Long userId);
    Cart updateCart(Long id, Cart cart);
    Cart partialUpdateCart(Long id, Map<String, Object> updates);

    CartItems saveItem(CartItems item);
    Optional<CartItems> findItemById(Long id);
    List<CartItems> findAllItems();
    void deleteItemById(Long id);
    List<CartItems> findItemsByCartId(Long cartId);
    CartItems updateCartItem(Long id, CartItems item);
    CartItems partialUpdateCartItem(Long id, Map<String, Object> updates);
}