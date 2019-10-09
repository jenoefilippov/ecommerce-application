package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private Item item;

    @BeforeEach
    public void setUp() {

        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);

        item = new Item();
        item.setId(1L);
        item.setName("Items name");
        item.setDescription("Items description");
        item.setPrice(new BigDecimal(1));

        Optional<Item> optionalItem = Optional.of(item);
        List<Item> itemArrayList = new ArrayList<>();
        itemArrayList.add(item);
        when(itemRepository.findAll()).thenReturn(itemArrayList);
        when(itemRepository.findById(any(Long.class))).thenReturn(optionalItem);
        when(itemRepository.findByName(any(String.class))).thenReturn(itemArrayList);
    }

    @Test
    public void getItems() {

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(200, response.getStatusCode().value());
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertTrue(items.contains(item));

    }

    @Test
    public void getItemById(){

        ResponseEntity<Item> response = itemController.getItemById(item.getId());
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        Item responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Items name", responseBody.getName());

    }

    @Test
    public void getItemsByName() {

        ResponseEntity<List<Item>> response = itemController.getItemsByName(item.getName());
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        List<Item> itemsByNameBody = response.getBody();
        assertNotNull(itemsByNameBody);
        assertTrue(itemsByNameBody.contains(item));
    }
}
