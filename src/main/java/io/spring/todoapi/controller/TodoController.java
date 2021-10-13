package io.spring.todoapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.spring.todoapi.dto.Todo;
import io.spring.todoapi.service.TodoService;

@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;

	@RequestMapping("/todos")
	public List<Todo> getAllTodo() {
		return todoService.getAllTodo();
	}

	@RequestMapping("/todos/{id}")
	public Optional<Todo> getTodoById(@PathVariable int id) {
		return todoService.getTodoById(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/todos")
	public void addTodo(@RequestBody Todo todo) {
		todoService.addTodo(todo);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/todos/{id}")
	public void updateTodo(@RequestBody Todo todo, @PathVariable int id) {
		todoService.updateTodo(id, todo);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/todos/{id}")
	public void deleteTodo(@PathVariable int id) {
		todoService.deleteTodo(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/todos/{category}")
	public List<Todo> getTodoByCategory(@PathVariable String category) {
		return todoService.getTodoByCategory(category);
	}

}
