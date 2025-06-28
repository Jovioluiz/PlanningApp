package com.planningapp.repository;

import com.planningapp.entity.Task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findFirstByEstimadaFalseOrderByIdAsc();
	Optional<Task> findFirstByEstimadaFalseAndLiberadaTrueOrderByIdAsc();
	List<Task> findByEstimadaFalseAndLiberadaTrueOrderByIdAsc();
	Optional<Task> findFirstByLiberadaTrueAndEstimadaFalseOrderByIdAsc();
	List<Task> findByEstimadaTrueOrderByIdDesc();

	//Optional<Task> findById();
}
