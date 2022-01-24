package io.spring.todoapi.dto

class AuthenticationResponse implements Serializable{

	private final String  jwt;

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
}
