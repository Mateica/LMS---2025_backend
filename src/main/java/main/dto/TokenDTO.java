package main.dto;

import java.util.HashSet;
import java.util.Set;

import main.model.Role;

public class TokenDTO {
	private String jwtToken;
	private Set<String> roles = new HashSet<String>();

	public TokenDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TokenDTO(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	

}
