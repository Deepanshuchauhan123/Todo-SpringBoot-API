package io.spring.todoapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.spring.todoapi.dto.Todo;
import io.spring.todoapi.repository.TodoRepository;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;

	public List<Todo> getAllTodo() {
		List<Todo> todoList = new ArrayList<Todo>();
		todoRepository.findAll().forEach(todoList::add);
		return todoList;
	}

	public Optional<Todo> getTodoById(int id) {
		return todoRepository.findById(id);
	}

	public void addTodo(Todo todo) {
		todoRepository.save(todo);
	}

	public void updateTodo(int id, Todo todo) {
		todo.setId(id);
		todoRepository.save(todo);
	}

	public void deleteTodo(int id) {
		todoRepository.deleteById(id);

	}

	public List<Todo> getTodoByCategory(String category) {
		return todoRepository.findByCategory(category);
	}

}
