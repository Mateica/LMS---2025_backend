package main.config;

import org.apache.jena.query.Dataset;
import org.apache.jena.tdb1.TDB1Factory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TDBConfig {
	private static final String FUSEKI_ENDPOINT = "http://localhost:3030/titleTypes";
    private static final String NS_BASE = "http://tt/titleTypes/";
    private static final String FOAF_PREFIX = "http://xmlns.com/foaf/0.1/";
    private static final String SCHEMA_PREFIX = "http://schema.org/";
	
	public Dataset dataset() {
		return TDB1Factory.createDataset(FUSEKI_ENDPOINT);
	}
}
