package main.model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class TitleType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Boolean active;

	public TitleType() {
		super();
	}

	public TitleType(Long id, String name, Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
	}

	
	public Resource toRDF() {
		Model model = (Model) ModelFactory.createDefaultModel();
		Resource titleTypeResource = model.createResource("http://tt/titleTypes/titleType/"+id)
				.addProperty(RDF.type, "http://tt/titleTypes/Doctor")
				.addProperty(model.createProperty("http://tt/titleTypes/id"), String.valueOf(id))
				.addProperty(model.createProperty("http://tt/titleTypes/name"), name)
				.addProperty(model.createProperty("http://tt/titleTypes/active"), String.valueOf(active));
		
		
		return titleTypeResource;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
