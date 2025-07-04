package com.planningapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planningapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsuario(String usuario);
}
