package io.spring.todoapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import io.spring.todoapi.dto.Users
import io.spring.todoapi.repository.UserRepository

@Service
class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository

	int userId;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Users user = userRepository.findByUsername(userName)
		userId = user.getId();

		return new User(user.getUsername(), user.getPassword(), new ArrayList<>())
	}
}
