package io.graphql.service.datafetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.graphql.model.Book;
import io.graphql.repository.BookRepository;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>>{

    @Autowired
    BookRepository bookRepository;

    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return bookRepository.findAll();
    }
    
}
