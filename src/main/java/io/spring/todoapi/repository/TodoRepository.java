package io.spring.todoapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.spring.todoapi.dto.Todo;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
	
	public List<Todo> findByCategory(String category);
}
