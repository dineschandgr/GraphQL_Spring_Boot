package io.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.graphql.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
