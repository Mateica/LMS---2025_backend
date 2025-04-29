package main.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import primer2.model.DodeljenoPravoPristupa;
import primer2.model.Korisnik;

public class UserDetailsServiceImplementation implements UserDetailsService {
	@Autowired
	private KorisnikService service;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Korisnik k = service.findByUsername(username);
		
		if(k != null) {
			ArrayList<GrantedAuthority> prava = new ArrayList<GrantedAuthority>();
			
			for(DodeljenoPravoPristupa p : k.getPravaPristupa()) {
				prava.add(new SimpleGrantedAuthority(p.getPravoPristupa().getNaziv()));
			}
			
			return new User(k.getKorisnickoIme(), k.getLozinka(),prava);
		}
		
		throw new UsernameNotFoundException("User not found!");
	}

}
