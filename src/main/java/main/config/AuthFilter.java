package main.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import main.util.TokenUtils;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		System.out.println(httpRequest.getServletPath());
		if(httpRequest.getServletPath().contains("/api/auth") || httpRequest.getServletPath().contains("/api/files")) {
			System.out.println("TEST1");
			super.doFilter(request, response, chain);
		}else {
			System.out.println("TEST2");
		String token = ((HttpServletRequest) request).getHeader("Authorization");
		
		if(tokenUtils.isTokenExpired(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
			String username = tokenUtils.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			ArrayList<GrantedAuthority> prava = new ArrayList<GrantedAuthority>();
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(),null, 
					user.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(((HttpServletRequest) request)));
			SecurityContextHolder.getContext().setAuthentication(authToken);		
		}
		
		super.doFilter(request, response, chain);
		}
	}
}
