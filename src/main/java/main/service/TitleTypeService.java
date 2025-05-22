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

@Service
public class TitleTypeService implements ServiceInterface<TitleType> {

	private static final String FUSEKI_ENDPOINT = "http://localhost:3030/titleTypes";
    private static final String NS_BASE = "http://tt/titleTypes/";
    private static final String FOAF_PREFIX = "http://xmlns.com/foaf/0.1/";
    private static final String SCHEMA_PREFIX = "http://schema.org/";
    
    @Autowired
    private TitleTypeRepository repo;

    private RDFConnection getConnection() {
        return RDFConnection.connect(FUSEKI_ENDPOINT);
    }

    private Long extractId(String uri) {
        return Long.parseLong(uri.substring(uri.lastIndexOf('/') + 1));
    }

    @Override
    public Iterable<TitleType> findAll() {
        List<TitleType> list = new ArrayList<>();

        String query = String.join("\n",
            "PREFIX foaf: <" + FOAF_PREFIX + ">",
            "PREFIX schema: <" + SCHEMA_PREFIX + ">",
            "SELECT ?s ?name ?active WHERE {",
            "  ?s foaf:name ?name .",
            "  OPTIONAL { ?s schema:active ?active }",
            "}"
        );

        try (RDFConnection conn = getConnection()) {
            conn.querySelect(query, qs -> {
                TitleType tt = new TitleType();
                tt.setId(extractId(qs.getResource("s").getURI()));
                tt.setName(qs.getLiteral("name").getString());
                if (qs.contains("active")) {
                    tt.setActive(qs.getLiteral("active").getBoolean());
                } else {
                    tt.setActive(true);
                }
                list.add(tt);
            });
        }

        return list;
    }

    @Override
    public Page<TitleType> findAll(Pageable pageable) {
        List<TitleType> list = new ArrayList<TitleType>();

        String query = String.join("\n",
            "PREFIX foaf: <" + FOAF_PREFIX + ">",
            "PREFIX schema: <" + SCHEMA_PREFIX + ">",
            "SELECT ?s ?name ?active WHERE {",
            "  ?s foaf:name ?name .",
            "  OPTIONAL { ?s schema:active ?active }",
            "} LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset()
        );

        try (RDFConnection conn = getConnection()) {
            conn.querySelect(query, qs -> {
                TitleType tt = new TitleType();
                tt.setId(extractId(qs.getResource("s").getURI()));
                tt.setName(qs.getLiteral("name").getString());
                if (qs.contains("active")) {
                    tt.setActive(qs.getLiteral("active").getBoolean());
                } else {
                    tt.setActive(true);
                }
                list.add(tt);
            });
        }

        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    public Optional<TitleType> findById(Long id) {
        String uri = NS_BASE + id;
        String query = String.join("\n",
            "PREFIX foaf: <" + FOAF_PREFIX + ">",
            "PREFIX schema: <" + SCHEMA_PREFIX + ">",
            "SELECT ?name ?active WHERE {",
            "  <" + uri + "> foaf:name ?name .",
            "  OPTIONAL { <" + uri + "> schema:active ?active }",
            "}"
        );

        try (RDFConnection conn = getConnection()) {
            final TitleType[] tt = new TitleType[1];
            conn.querySelect(query, qs -> {
                tt[0] = new TitleType();
                tt[0].setId(id);
                tt[0].setName(qs.getLiteral("name").getString());
                if (qs.contains("active")) {
                    tt[0].setActive(qs.getLiteral("active").getBoolean());
                } else {
                    tt[0].setActive(true);
                }
            });
            return Optional.ofNullable(tt[0]);
        }
    }

    @Override
    public TitleType create(TitleType t) {
        Node subject = NodeFactory.createURI(NS_BASE + t.getId());
        Node namePredicate = NodeFactory.createURI(FOAF_PREFIX + "name");
        Node activePredicate = NodeFactory.createURI(SCHEMA_PREFIX + "active");

        UpdateBuilder ub = new UpdateBuilder()
            .addInsert(subject, namePredicate, NodeFactory.createLiteral(t.getName()))
            .addInsert(subject, activePredicate, NodeFactory.createLiteral(String.valueOf(t.getActive())));

        try {
            UpdateRequest req = ub.buildRequest();
            UpdateProcessor u = UpdateExecutionFactory.createRemote(req, FUSEKI_ENDPOINT);
            u.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    @Override
    public TitleType update(TitleType t) {
        Node subject = NodeFactory.createURI(NS_BASE + t.getId());
        Node namePredicate = NodeFactory.createURI(FOAF_PREFIX + "name");
        Node activePredicate = NodeFactory.createURI(SCHEMA_PREFIX + "active");

        UpdateBuilder delete = new UpdateBuilder()
            .addDelete(subject, namePredicate, "?name")
            .addDelete(subject, activePredicate, "?active")
            .addWhere(subject, namePredicate, "?name")
            .addOptional(subject, activePredicate, "?active");

        UpdateBuilder insert = new UpdateBuilder()
            .addInsert(subject, namePredicate, NodeFactory.createLiteral(t.getName()))
            .addInsert(subject, activePredicate, NodeFactory.createLiteral(String.valueOf(t.getActive())));

        try {
            UpdateExecutionFactory.createRemote(delete.buildRequest(), FUSEKI_ENDPOINT).execute();
            UpdateExecutionFactory.createRemote(insert.buildRequest(), FUSEKI_ENDPOINT).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    @Override
    public void delete(Long id) {
        Node subject = NodeFactory.createURI(NS_BASE + id);
        UpdateBuilder delete = new UpdateBuilder()
            .addDelete(subject, "?p", "?o")
            .addWhere(subject, "?p", "?o");

        try {
            UpdateExecutionFactory.createRemote(delete.buildRequest(), FUSEKI_ENDPOINT).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void softDelete(Long id) {
        Node subject = NodeFactory.createURI(NS_BASE + id);
        Node activePredicate = NodeFactory.createURI(SCHEMA_PREFIX + "active");

        UpdateBuilder ub = new UpdateBuilder()
            .addDelete(subject, activePredicate, "?oldVal")
            .addWhere(subject, activePredicate, "?oldVal")
            .addInsert(subject, activePredicate, NodeFactory.createLiteral("false"));

        try {
            UpdateProcessor u = UpdateExecutionFactory.createRemote(ub.buildRequest(), FUSEKI_ENDPOINT);
            u.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
