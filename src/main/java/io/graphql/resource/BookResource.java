package io.graphql.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.graphql.service.GraphQLService;

@RequestMapping("/rest/books")
@RestController
public class BookResource {

	@Autowired
	GraphQLService graphQLService;
	
	@PostMapping
	public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
		return new ResponseEntity<>( graphQLService.getGraphQL().execute(query),HttpStatus.OK);
	}
}
