package com.example.demo.controller;

import com.example.demo.model.requests.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {


    @PostMapping("/login")
    public ResponseEntity<?> login(@PathVariable AuthenticationRequest authenticationRequest) {



        return ResponseEntity.ok(authenticationRequest);
    }
}
