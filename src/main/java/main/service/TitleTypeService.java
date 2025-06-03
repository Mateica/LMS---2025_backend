package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.jena.arq.querybuilder.UpdateBuilder;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.model.TitleType;
import main.repository.TitleTypeRepository;
import main.repository.TitleTypeRepositoryImplementation;

@Service
public class TitleTypeService implements ServiceInterface<TitleType> {
	@Autowired
	private TitleTypeRepositoryImplementation repo;

	@Override
	public Iterable<TitleType> findAll() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Page<TitleType> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.repo.findAll(pageable);
	}

	@Override
	public Optional<TitleType> findById(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public TitleType create(TitleType t) {
		// TODO Auto-generated method stub
		return this.repo.save(t);
	}

	@Override
	public TitleType update(TitleType t) {
		// TODO Auto-generated method stub
		return this.repo.update(t);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		this.repo.softDelete(id);
	}

	public List<TitleType> findAllActive() {
		// TODO Auto-generated method stub
		return this.repo.findAllActive();
	}

}
