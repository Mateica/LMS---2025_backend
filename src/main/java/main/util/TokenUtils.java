package main.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtils {
	private String secretKey = "UYG&dgwg24!cd7&4fhwojjIOJfwewioi*7rtrpo09*(98()?f9@iwjjefII";
	
	public Key getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String generateToken(UserDetails userDetails) {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		
		payload.put("sub", userDetails.getUsername());
		payload.put("authorities", userDetails.getAuthorities());
		
		return Jwts
				.builder()
				.addClaims(payload)
				.setExpiration(new Date(System.currentTimeMillis()+100000))
				.signWith(this.getKey())
				.compact();
	}
	
	public String getUsername(String token) {
		String username = null;
		try {
			return parseClaims(token).getSubject();
		} catch (Exception e) {
		}

		return username;
	}
	
	public Claims parseClaims(String token) {
		Claims claims = null;
		try {
			claims = (Claims) Jwts
					.parser()
					.setSigningKey(this.getKey())
					.build()
					.parse(token)
					.getPayload();
			
		}catch(JwtException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid JWT token: " + e);
		}
		return claims;
	}
	
	public boolean isTokenExpired(String token) {
		try {
			return this.parseClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
		}
		return true;

	}
	
	public boolean validateToken(String token) {
		boolean valid = true;
		
		if(parseClaims(token) == null) {
			valid = false;
		}
		
		if(valid && isTokenExpired(token)) {
			valid = false;
		}
		
		return valid;

	}
}
