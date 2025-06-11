package main.repository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.arq.querybuilder.UpdateBuilder;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.vocabulary.RDF;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import main.model.TitleType;

@Component
public class TitleTypeRepositoryImplementation implements TitleTypeRepository {
	private static final String NS = "http://tt/titleTypes#";
    private static final String QUERY_ENDPOINT = "http://localhost:3030/titleTypes/query";
    private static final String UPDATE_ENDPOINT = "http://localhost:3030/titleTypes/update";
    private static final String DATA_ENDPOINT = "http://localhost:3030/titleTypes/data";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Iterable<TitleType> findAll() {
        String queryStr = """
            PREFIX tt: <http://tt/titleTypes/>
            SELECT ?id ?name ?active WHERE {
                ?s a tt:TitleType ;
                   tt:id ?id ;
                   tt:name ?name ;
                   tt:active ?active .
            }
            """;

        List<TitleType> results = new ArrayList<>();
        Query query = QueryFactory.create(queryStr);

        try (QueryExecution qexec = QueryExecution.service(QUERY_ENDPOINT)
                .query(query)
                .build()) {
            ResultSet rs = qexec.execSelect();
            while (rs.hasNext()) {
                QuerySolution sol = rs.next();
                Long id = sol.getLiteral("id").getLong();
                String name = sol.getLiteral("name").getString();
                Boolean active = sol.getLiteral("active").getBoolean();
                results.add(new TitleType(id, name, active));
            }
        }
        return results;
    }

    @Override
    public Optional<TitleType> findById(Long id) {
        String queryStr = """
            PREFIX tt: <http://tt/titleTypes/>
            SELECT ?name ?active WHERE {
                ?s a tt:TitleType ;
                   tt:id %d ;
                   tt:name ?name ;
                   tt:active ?active .
            }
            """.formatted(id);

        Query query = QueryFactory.create(queryStr);
        TitleType tt = null;

        try (QueryExecution qexec = QueryExecution.service(QUERY_ENDPOINT)
                .query(query)
                .build()) {
            ResultSet rs = qexec.execSelect();
            if (rs.hasNext()) {
                QuerySolution sol = rs.next();
                String name = sol.getLiteral("name").getString();
                Boolean active = sol.getLiteral("active").getBoolean();
                tt = new TitleType(id, name, active);
            }
        }

        return Optional.ofNullable(tt);
    }

    @Override
    public TitleType save(TitleType tt) {
        // Build RDF model for TitleType
        Model model = ModelFactory.createDefaultModel();
        Resource res = model.createResource(NS + "titleType" + tt.getId());

        res.addProperty(RDF.type, model.createResource(NS + "TitleType"))
           .addLiteral(model.createProperty(NS, "id"), tt.getId())
           .addLiteral(model.createProperty(NS, "name"), tt.getName())
           .addLiteral(model.createProperty(NS, "active"), tt.getActive());

        uploadModel(model);
        return tt;
    }

    @Override
    public TitleType update(TitleType tt) {
        String deleteQuery = """
            PREFIX tt: <http://tt/titleTypes/>
            DELETE WHERE {
              ?s a tt:TitleType ;
                 tt:id %d ;
                 ?p ?o .
            }
            """.formatted(tt.getId());

        String insertQuery = """
            PREFIX tt: <http://tt/titleTypes/>
            INSERT DATA {
                <http://tt/titleTypes/titleType%d> a tt:TitleType ;
                    tt:id %d ;
                    tt:name "%s" ;
                    tt:active %b .
            }
            """.formatted(tt.getId(), tt.getId(), tt.getName(), tt.getActive());

        UpdateRequest updateRequest = UpdateFactory.create();
        updateRequest.add(deleteQuery);
        updateRequest.add(insertQuery);

        UpdateProcessor proc = UpdateExecutionFactory.createRemote(updateRequest, UPDATE_ENDPOINT);
        proc.execute();

        return tt;
    }

    @Override
    public void softDelete(Long id) {
        String updateStr = """
            PREFIX tt: <http://tt/titleTypes/>
            DELETE { ?s tt:active true }
            INSERT { ?s tt:active false }
            WHERE {
              ?s a tt:TitleType ;
                 tt:id %d ;
                 tt:active true .
            }
            """.formatted(id);

        UpdateRequest updateRequest = UpdateFactory.create(updateStr);
        UpdateProcessor proc = UpdateExecutionFactory.createRemote(updateRequest, UPDATE_ENDPOINT);
        proc.execute();
    }

    @Override
    public List<TitleType> findAllActive() {
        String queryStr = """
            PREFIX tt: <http://tt/titleTypes/>
            SELECT ?id ?name WHERE {
                ?s a tt:TitleType ;
                   tt:id ?id ;
                   tt:name ?name ;
                   tt:active true .
            }
            """;

        List<TitleType> results = new ArrayList<>();
        Query query = QueryFactory.create(queryStr);

        try (QueryExecution qexec = QueryExecution.service(QUERY_ENDPOINT)
                .query(query)
                .build()) {
            ResultSet rs = qexec.execSelect();
            while (rs.hasNext()) {
                QuerySolution sol = rs.next();
                Long id = sol.getLiteral("id").getLong();
                String name = sol.getLiteral("name").getString();
                results.add(new TitleType(id, name, true));
            }
        }
        return results;
    }

    private void uploadModel(Model model) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            RDFDataMgr.write(baos, model, RDFFormat.TURTLE_PRETTY);
            String turtle = baos.toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("text/turtle"));

            HttpEntity<String> entity = new HttpEntity<>(turtle, headers);

            restTemplate.put(DATA_ENDPOINT, entity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload RDF model", e);
        }
	
    }

    @Override
    public Page<TitleType> findAll(Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("""
            PREFIX tt: <http://tt/titleTypes/>
            SELECT ?id ?name ?active WHERE {
                ?s a tt:TitleType ;
                   tt:id ?id ;
                   tt:name ?name ;
                   tt:active ?active .
            }
            """);

        if (pageable.getSort().isSorted()) {
            queryBuilder.append("ORDER BY ");
            for (Order order : pageable.getSort()) {
                String field = switch (order.getProperty()) {
                    case "id" -> "?id";
                    case "name" -> "?name";
                    case "active" -> "?active";
                    default -> "?id"; // default/fallback
                };
                queryBuilder.append(order.isAscending() ? "ASC(" : "DESC(")
                             .append(field).append(") ");
            }
        } else {
            queryBuilder.append("ORDER BY ?id ");
        }

        queryBuilder.append(String.format("LIMIT %d OFFSET %d", pageable.getPageSize(), pageable.getOffset()));

        List<TitleType> results = new ArrayList<>();
        Query query = QueryFactory.create(queryBuilder.toString());

        try (QueryExecution qexec = QueryExecution.service(QUERY_ENDPOINT)
                .query(query)
                .build()) {
            ResultSet rs = qexec.execSelect();
            while (rs.hasNext()) {
                QuerySolution sol = rs.next();
                Long id = sol.getLiteral("id").getLong();
                String name = sol.getLiteral("name").getString();
                Boolean active = sol.getLiteral("active").getBoolean();
                results.add(new TitleType(id, name, active));
            }
        }

        long totalCount = countAll();
        return new PageImpl<>(results, pageable, totalCount);
    }


    private long countAll() {
        String countQueryStr = """
            PREFIX tt: <http://tt/titleTypes/>
            SELECT (COUNT(?s) AS ?count) WHERE {
                ?s a tt:TitleType .
            }
            """;

        Query query = QueryFactory.create(countQueryStr);
        try (QueryExecution qexec = QueryExecution.service(QUERY_ENDPOINT)
                .query(query)
                .build()) {
            ResultSet rs = qexec.execSelect();
            if (rs.hasNext()) {
                QuerySolution sol = rs.next();
                return sol.getLiteral("count").getLong();
            }
        }
        return 0;
    }

	@Override
	public List<TitleType> findByActiveIsTrue() {
		// TODO Auto-generated method stub
		String queryStr = """
		        PREFIX tt: <http://tt/titleTypes/>
		        SELECT ?id ?name WHERE {
		            ?s a tt:TitleType ;
		               tt:id ?id ;
		               tt:name ?name ;
		               tt:active true .
		        }
		        """;

		    List<TitleType> results = new ArrayList<>();
		    Query query = QueryFactory.create(queryStr);

		    try (QueryExecution qexec = QueryExecution.service(QUERY_ENDPOINT)
		            .query(query)
		            .build()) {
		        ResultSet rs = qexec.execSelect();
		        while (rs.hasNext()) {
		            QuerySolution sol = rs.next();
		            Long id = sol.getLiteral("id").getLong();
		            String name = sol.getLiteral("name").getString();
		            results.add(new TitleType(id, name, true));
		        }
		    }

		    return results;
	}
}
