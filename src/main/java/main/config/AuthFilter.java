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
	    HttpServletRequest httpRequest = (HttpServletRequest) request;

	    String path = httpRequest.getServletPath();

	    if(path.contains("/api/auth") || path.contains("/api/files") || path.contains("/api/roles") || path.contains("/api/evaluations")) {
	        chain.doFilter(request, response);
	        return;
	    }

	    String authHeader = httpRequest.getHeader("Authorization");
	    if(authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
	        if(tokenUtils.validateToken(token) && !tokenUtils.isTokenExpired(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
	            String username = tokenUtils.getUsername(token);
	            UserDetails user = userDetailsService.loadUserByUsername(username);

	            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                user, null, user.getAuthorities()
	            );
	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
	    }
	    
	    chain.doFilter(request, response);
	}
}
