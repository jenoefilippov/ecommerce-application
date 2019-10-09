package com.example.demo.controller;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);

    @BeforeEach
    public void setUp() {

        userController = new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository", cartRepository);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);

    }

    @Test
    public void createUser_findByUserName_findById() {

        /**
         * Create User
         */

        when(bCryptPasswordEncoder.encode("password")).thenReturn("saltedPassword");
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("jenoe");
        userRequest.setPassword("password");
        userRequest.setConfirmPassword("password");

        final ResponseEntity<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("jenoe", user.getUsername());
        assertEquals("saltedPassword", user.getPassword());

        /**
         * findByUserName
         */
        final ResponseEntity<User> response1 = userController.findByUserName(user.getUsername());
        assertNotNull(response1);
        User findUser = response.getBody();
        assertEquals("jenoe", findUser.getUsername());

        /**
         * Find by id
         */

        user.setId(1L);
        assertNotNull(userController.findById(user.getId()));

    }


}
