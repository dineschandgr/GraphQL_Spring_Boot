# GraphQL_Spring_Boot
GraphQL implementation using Spring Boot

This source code only implements the GraphQL query and not the mutation.
Generally, GraphQL is used to query data as the data modification is done in the Database using other APIs

#Preqequisute

1. Download the following jars. Spring boot will run GraphQL server internally
		    graphql-spring-boot-starter		    
		    graphql-java-tools
 2. Download a database like HSql to load data and GraphQL will retrieve data from that       
 
#Notes
- server-side technology
- a query language for API
- an advanced version of REST
- can use HTTP or TCP
- alternative to REST
- fetch only the required data
- single rest endpoint for all clients and clients can choose what data they need

#Steps 
1. Create a .graphql file to define the schema,query, mutation and the Entity
		 
2. in GraphQLService.java, 

  a. a TypeDefinitionRegistry is defined to parse the schema file
  b. a RunTimeWiring is defined with the datafetchers for fetching all books and individual books
  c. AllBooksDataFetcher and BookDataFetcher classes are defined to retrieve data from the database using Spring Data JPA
  d. a GraphQL object is defined and getter method is provided to be accessed from Controller 

3. in BookResource.java, a single post rest endpoint is defined with the url /rest/books
4. When the request hits this endpoint, the GraphQL service is called and the loading of schema and processing of data is done and retrieved to the user
5. Send a post request to the url from postman to http://localhost:8091/rest/books with the following payload

sample post request body

#returns only the 3 fields for allBooks query

{
    "one": 2,
    "three": {
        "point_1": "point_2",
        "point_3": 3.4
    },
    "list": [
        "one",
        "two",
        "three"
    ]
}

allBooks{
        title
        publishedDate
        authors
}

#returns only the 1 field for book query based on bookid
{
    book(id:"123"){
        title
    }
}

#returns only the 3 fields for book query based on bookid
{
    book(id:"123"){
        title
        publishedDate
        authors
    }
}

#returns both the queries in a single response
{
    allBooks{
        title
        publishedDate
        authors
    }
    book(id:"123"){
        title
        publishedDate
        authors
    }
}


