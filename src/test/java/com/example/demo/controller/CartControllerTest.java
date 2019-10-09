package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CartControllerTest {

    private CartController cartController;
    private CartRepository cartRepository = mock(CartRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private ModifyCartRequest cartRequest;
    private User user;
    private Item item;

    @BeforeEach
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);

        user = new User();
        user.setId(1L);
        user.setUsername("jenoe");
        user.setPassword("password");

        item = new Item();
        item.setId(1L);
        item.setName("Items name");
        item.setDescription("Items description");
        item.setPrice(new BigDecimal(1));

        Cart cart = new Cart();
        cart.setItems(itemRepository.findAll());
        cart.setTotal(BigDecimal.valueOf(2));
        cart.setUser(user);
        user.setCart(cart);
        userRepository.save(user);
        cartRepository.save(cart);

        cartRequest = new ModifyCartRequest();
        cartRequest.setUsername(user.getUsername());
        cartRequest.setItemId(item.getId());
        cartRequest.setQuantity(1);

        Optional<Item> optionalItem = Optional.of(item);

        when(itemRepository.findById(any(Long.class))).thenReturn(optionalItem);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);

    }

    @Test
    public void addTocart() {
        ResponseEntity<Cart> cartResponse = cartController.addTocart(cartRequest);
        assertEquals(200, cartResponse.getStatusCode().value());
        assertNotNull(cartResponse);
        assertTrue(cartResponse.getBody().getItems().contains(item));
    }

    @Test
    public void removeFromcart() {
        ResponseEntity<Cart> cartResponse = cartController.removeFromcart(cartRequest);
        assertEquals(200, cartResponse.getStatusCode().value());
        assertNotNull(cartResponse);
        assertFalse(cartResponse.getBody().getItems().contains(item));
    }

}
