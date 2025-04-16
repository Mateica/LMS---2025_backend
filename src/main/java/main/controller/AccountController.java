package main.controller;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.AccountDTO;
import main.dto.RegisteredUserDTO;
import main.model.Account;
import main.model.ForumUser;
import main.model.RegisteredUser;
import main.model.Role;
import main.service.AccountService;

@RestController
@RequestMapping(path = "/api/accounts")
public class AccountController implements ControllerInterface<AccountDTO> {
	@Autowired
	private AccountService service;

	@Override
	@GetMapping("")
	public ResponseEntity<Iterable<AccountDTO>> findAll() {
		// TODO Auto-generated method stub
		ArrayList<AccountDTO> accounts = new ArrayList<AccountDTO>();
		
		for(Account a : service.findAll()) {
			accounts.add(new AccountDTO(a.getId(), a.getUsername(), null, a.getEmail(),
					new RegisteredUserDTO(a.getRegisteredUser().getUsername(), null, a.getRegisteredUser().getEmail()),
					a.getActive()));
		}
		
		return new ResponseEntity<Iterable<AccountDTO>>(accounts, HttpStatus.OK);
	}

	@Override
	@GetMapping("")
	public ResponseEntity<AccountDTO> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Account a = service.findById(id).orElse(null);
		
		if(a == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AccountDTO>(new AccountDTO(a.getId(), a.getUsername(), null, a.getEmail(),
				new RegisteredUserDTO(a.getRegisteredUser().getUsername(), null, a.getRegisteredUser().getEmail()),
				a.getActive()), HttpStatus.OK);
	}

	@Override
	@PostMapping("")
	public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO t) {
		// TODO Auto-generated method stub
		Account a  = service.create(new Account(null, t.getUsername(), t.getPassword(), t.getEmail(), null, t.getActive()));
		
		if(a == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(a.getId(), a.getUsername(), null, a.getEmail(),
				new RegisteredUserDTO(a.getRegisteredUser().getUsername(), null, a.getRegisteredUser().getEmail()),
				a.getActive()), HttpStatus.OK);
	}

	@Override
	@PutMapping(path = "/{id}")
	public ResponseEntity<AccountDTO> update(@RequestBody AccountDTO t, @PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Account a = service.findById(id).orElse(null);
		
		if(a == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
		
		a.setId(t.getId());
		a.setUsername(t.getUsername());
		a.setPassword(t.getPassword());
		a.setEmail(t.getEmail());
		a.setRegisteredUser(new RegisteredUser(t.getRegisteredUser().getId(), t.getRegisteredUser().getUsername(), t.getRegisteredUser().getPassword(), t.getRegisteredUser().getEmail(), new ArrayList<ForumUser>(), new ArrayList<Account>(), new HashSet<Role>(),  t.getRegisteredUser().getActive()));
		a.setActive(t.getActive());
		
		a = service.update(a);
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(a.getId(), a.getUsername(), null, a.getEmail(),
				new RegisteredUserDTO(a.getRegisteredUser().getUsername(), null, a.getRegisteredUser().getEmail()),
				a.getActive()), HttpStatus.OK);
	}

	@Override
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<AccountDTO> delete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PutMapping(path = "/softDelete/{id}")
	public ResponseEntity<AccountDTO> softDelete(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Account a = service.findById(id).orElse(null);
		
		if(a == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(a.getId(), a.getUsername(), null, a.getEmail(),
				new RegisteredUserDTO(a.getRegisteredUser().getUsername(), null, a.getRegisteredUser().getEmail()),
				a.getActive()), HttpStatus.OK);
	}
	
	
}	
