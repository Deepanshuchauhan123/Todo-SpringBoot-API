package io.spring.todoapi.dto

import javax.annotation.Generated
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

import com.fasterxml.jackson.annotation.JsonIgnore

import net.bytebuddy.dynamic.loading.ClassReloadingStrategy.Strategy

@Entity
class Users implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id
	private String username
	private String password

	public Users() {
	}

	public Users(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
