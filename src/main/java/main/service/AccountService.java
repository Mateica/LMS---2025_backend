package main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.Account;
import main.repository.AccountRepository;

@Service
public class AccountService implements ServiceInterface<Account> {
	@Autowired
	private AccountRepository repo;

	@Override
	public Iterable<Account> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<Account> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Account create(Account t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public Account update(Account t) {
		// TODO Auto-generated method stub
		Account a = repo.findById(t.getId()).orElse(t);
		
		if(a != null) {
			this.repo.save(a);
		}
		
		throw new RuntimeException("No account with such ID!");
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Account a = repo.findById(id).orElse(null);
		
		if(a == null) {
			throw new RuntimeException("No account with such ID!");
		}
		
		this.repo.deleteById(id);
		
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		Account a = repo.findById(id).orElse(null);
		
		if(a == null) {
			throw new RuntimeException("No account with such ID!");
		}
		
		a.setActive(false);
		this.repo.save(a);
	}
	
	
}
