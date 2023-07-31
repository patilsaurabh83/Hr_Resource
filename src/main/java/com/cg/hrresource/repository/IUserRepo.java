package com.cg.hrresource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.hrresource.entities.User;



public interface IUserRepo extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

}
