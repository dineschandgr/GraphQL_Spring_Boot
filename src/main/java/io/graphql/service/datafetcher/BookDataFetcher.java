package io.graphql.service.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.graphql.model.Book;
import io.graphql.repository.BookRepository;

@Component
public class BookDataFetcher implements DataFetcher<Book>{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) {

        String isn = dataFetchingEnvironment.getArgument("id");

        return bookRepository.findById(isn).get();
    }
}