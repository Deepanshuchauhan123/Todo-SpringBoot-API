package io.spring.todoapi.repository

import org.springframework.data.repository.CrudRepository

import io.spring.todoapi.dto.Users
import io.spring.todoapi.dto.Todo

interface UserRepository extends CrudRepository<Users,String>{
	
	Users findByUsername(String username)
}
