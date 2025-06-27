package com.planningapp.repository;

import com.planningapp.entity.Task;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
	Optional<Task> findFirstByEstimadaFalseOrderByIdAsc();
	//Optional<Task> findById();
}
