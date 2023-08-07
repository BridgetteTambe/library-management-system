package com.drycode.services.impl;

import com.drycode.models.Book;
import com.drycode.repositories.BookRepository;
import com.drycode.services.BookService;
import com.drycode.services.GenerateIDService;
import com.drycode.utils.ConstantString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private static final String ENTITY = "Book";

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final GenerateIDService generateSequence;

    public BookServiceImpl(BookRepository bookRepository, GenerateIDService generateSequence) {
        this.bookRepository = bookRepository;
        this.generateSequence = generateSequence;
    }

    @Override
    public Book save(Book book) throws Exception {
        LOGGER.info(ConstantString.SAVE_LOG.replaceAll("#ENTITY", ENTITY), book);
        if (StringUtils.isEmpty(book.getId())) {
            book.setId(generateSequence.generateSequence(Book.SEQUENCE_NAME));
        }
        if (StringUtils.isEmpty(book.getName())) {
            throw new Exception(ConstantString.NAME_NULL.replaceAll("#ENTITY", ENTITY)
                    .replaceAll("#NAME", "Name"));
        }
        if (StringUtils.isEmpty(book.getQuantity())) {
            throw new Exception(ConstantString.NAME_NULL.replaceAll("#ENTITY", ENTITY)
                    .replaceAll("#NAME", "Quantity"));
        }
        return this.bookRepository.save(book);
    }

    @Override
    public void delete(Book book) throws Exception {
        LOGGER.info(ConstantString.DELETE_LOG.replaceAll("#ENTITY", ENTITY), book);
        this.bookRepository.delete(book);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        LOGGER.info(ConstantString.GET_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return this.bookRepository.findAll(pageable);
    }

    @Override
    public void saveAll(Set<Book> booksToBeUpdated) {
        if (!ObjectUtils.isEmpty(booksToBeUpdated)) {
            booksToBeUpdated.forEach(book -> {
                try {
                    this.save(book);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public Book findById(Long id) {
        LOGGER.info(ConstantString.GET_LOG.replaceAll("#ENTITY", ENTITY), id);
        return this.bookRepository.findById(id).orElse(null);
    }
}
