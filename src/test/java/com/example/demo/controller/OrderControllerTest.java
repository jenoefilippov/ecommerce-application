package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;


public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private User user;

    /**
     * Setting up  all necessary data
     */
    @BeforeEach
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);

        user = new User();
        user.setId(1L);
        user.setUsername("jenoe");
        user.setPassword("password");

        Cart cart = new Cart();
        cart.setItems(itemRepository.findAll());
        cart.setTotal(BigDecimal.valueOf(2));
        cart.setUser(user);
        user.setCart(cart);
        userRepository.save(user);
        cartRepository.save(cart);
    }

    @Test
    public void submit() {

//        User user = userRepository.findByUsername("jenoe");
        assertNotNull(user);
        assertEquals("jenoe", user.getUsername());
        final ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void getOrdersForUser() {
//        User user = userRepository.findByUsername("jenoe");
        assertNotNull(user);
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(user.getUsername());
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }

}
