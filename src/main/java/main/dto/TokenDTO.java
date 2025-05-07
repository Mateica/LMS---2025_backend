package main.dto;

public class TokenDTO {
	private String jwtToken;

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

}
