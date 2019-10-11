package com.example.demo.controller;

import com.example.demo.ECommerceApplication;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	private Logger log = LoggerFactory.getLogger(ECommerceApplication.class);
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		log.info("Order submitting.");
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.warn("Order cannot be submitted. Because user {} not found", username);
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		log.info("Order submitted.");
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		log.info("Searching orders for user {}", username);
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.warn("Order cannot be found. Because user {} not found", username);
			return ResponseEntity.notFound().build();
		}
		log.info("Orders for user {} founded.", username);
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
