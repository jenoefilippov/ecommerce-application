package com.example.demo.model.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.User;
import org.springframework.stereotype.Repository;

import java.util.stream.LongStream;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

}
