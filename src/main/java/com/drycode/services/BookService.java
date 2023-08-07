package com.drycode.services;

import com.drycode.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface BookService {
    Book save(Book book) throws Exception;
    void delete(Book book) throws Exception;
    Book findById(Long id);
    Page<Book> findAll(Pageable pageable);
    void saveAll(Set<Book> booksToBeUpdated);
}
