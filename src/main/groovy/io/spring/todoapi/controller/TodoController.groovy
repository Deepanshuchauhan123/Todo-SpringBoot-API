package io.spring.todoapi.controller

import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import io.spring.todoapi.dto.AuthenticationResponse
import io.spring.todoapi.dto.Folder
import io.spring.todoapi.dto.Todo
import io.spring.todoapi.dto.Users
import io.spring.todoapi.service.MyUserDetailsService
import io.spring.todoapi.service.TodoService
import io.spring.todoapi.util.JwtUtil

@RestController
class TodoController {

	@Autowired
	private AuthenticationManager authenticationManager

	@Autowired
	MyUserDetailsService myUserDetailsService

	@Autowired
	JwtUtil jwtUtil

	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Users authenticationRequest)throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()))
		}catch(BadCredentialsException exp) {

			throw new Exception("Incorrect username and password",exp)
		}

		final UserDetails userDetails =  myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername())

		final String jwt = jwtUtil.generateToken(userDetails)

		return ResponseEntity.ok(new  AuthenticationResponse(jwt))
	}

	@Autowired
	TodoService todoService


	// GET
	@GetMapping("/tasks")
	public List getAllTasks(@RequestParam String values){
		return todoService.getAllTasks(values)
	}
	//GET
	@GetMapping("/tasks/search")
	public List<Todo> searchTask(@RequestParam String value){
		return todoService.searchTask(value)
	}
	//GET
	@GetMapping("/tasks/pages")
	public List<Todo> getPaginationTasks(@RequestParam(defaultValue="0")int pageset ,@RequestParam(defaultValue="2") int size){
		return todoService.getPaginationTasks(pageset,size);
	}

	// GET
	@GetMapping("/tasks/{id}")
	public Object getTaskById(@PathVariable int id) {
		return todoService.getTaskById(id)
	}

	// GET
	@GetMapping("/tasks/priority/{priority}")
	public List<Todo> getTaskByPriority(@PathVariable String priority){
		return todoService.getTaskByPriority(priority)
	}

	// GET
	@GetMapping("/tasks/category/{category}")
	public List<Todo> getTaskByCategory(@PathVariable String category){
		return todoService.getTaskByCategory(category)
	}

	//POST
	@PostMapping("/tasks/folder")
	public ResponseEntity<String> addFolder(@RequestBody Folder folder){
		todoService.addFolder(folder)
		return ResponseEntity.ok("Folder added successfully!")
	}

	//DELETE
	@DeleteMapping("/tasks/folder/{id}")
	public ResponseEntity<String> deleteFolder(@PathVariable long id){
		todoService.deleteFolder(id)
		return ResponseEntity.ok("Folder Deleted successfully!")
	}

	//POST
	@PostMapping("/tasks")
	public ResponseEntity<String> addTask(@Valid @RequestBody Todo todo) {
		return todoService.addTask(todo)
	}

	//UPDATE OR PUT
	@PutMapping ("/tasks/{id}")
	public ResponseEntity<String> updateTaskById(@PathVariable int id, @RequestBody Todo todo) {
		return todoService.updateTaskById(id, todo)
	}

	@PutMapping("/folders/{folderId}/tasks/{taskId}")
	public ResponseEntity<String> archiveTasks(@PathVariable int folderId, @PathVariable int taskId, @RequestBody Map<String,Boolean> archive){
		return todoService.archiveTasks(folderId, taskId, archive.values().getAt(0))
	}

	//DELETE
	@DeleteMapping ("/tasks/{id}")
	public void deleteTaskById(@PathVariable int id) {
		todoService.deleteTaskById(id)
	}

}
