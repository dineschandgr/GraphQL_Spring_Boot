package io.graphql.service;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.graphql.model.Book;
import io.graphql.repository.BookRepository;
import io.graphql.service.datafetcher.AllBooksDataFetcher;
import io.graphql.service.datafetcher.BookDataFetcher;

@Service
public class GraphQLService {

	@Value("classpath:books.graphql")
	Resource resource;
	
	private GraphQL graphQL;
	
	@Autowired
	private AllBooksDataFetcher allBooksDataFetcher;
	
	@Autowired
	private BookDataFetcher bookDataFetcher;
	
	@Autowired
	private BookRepository bookRepository;
	
    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {

        //Load Books into the Book Repository
        loadDataIntoHSQL();

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }
    
    private RuntimeWiring buildRuntimeWiring() {
    	  return RuntimeWiring.newRuntimeWiring()
                  .type("Query", typeWiring -> typeWiring
                		  //.dataFetcher("allBooks", allBooksDataFetcher)
                          .dataFetcher("book", bookDataFetcher)
                          .dataFetcher("allBooks", allBooksDataFetcher)
                		  )
                  .build();
    }
    
    private void loadDataIntoHSQL() {

        Stream.of(
                new Book("123", "Book of Clouds", "Kindle Edition",
                        new String[] {
                        "Chloe Aridjis"
                        }, "Nov 2017"),
                new Book("124", "Cloud Arch & Engineering", "Orielly",
                        new String[] {
                                "Peter", "Sam"
                        }, "Jan 2015"),
                new Book("125", "Java 9 Programming", "Orielly",
                        new String[] {
                                "Venkat", "Ram"
                        }, "Dec 2016")
        ).forEach(book -> {
            bookRepository.save(book);
        });
    }
    
    public GraphQL getGraphQL() {
		return graphQL;
	}
	
    @Transactional
    public Book createBook(final String isn,final String title, final String publisher) {
        Book book = new Book();
        book.setIsn(isn);
        book.setTitle(title);
        book.setPublisher(publisher);
        return this.bookRepository.save(book);
    }
    
}
