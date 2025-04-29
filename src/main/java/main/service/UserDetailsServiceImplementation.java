package main.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import main.model.RegisteredUser;
import main.model.Role;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	@Autowired
	private RegisteredUserService service;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		RegisteredUser user = service.findByUsername(username);
		
		if(user != null) {
			ArrayList<GrantedAuthority> prava = new ArrayList<GrantedAuthority>();
			
			for(Role r : user.getRoles()) {
				prava.add(new SimpleGrantedAuthority(r.getName()));
			}
			
			return new User(user.getUsername(), user.getPassword(),prava);
		}
		
		throw new UsernameNotFoundException("User not found!");
	}

}
