package com.drycode.web;

import com.drycode.models.Book;
import com.drycode.services.BookService;
import com.drycode.services.GenerateIDService;
import com.drycode.utils.ConstantString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class BookResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookResource.class);
    private static final String ENTITY = "Book";
    private final BookService bookService;
    private final GenerateIDService generateSequence;

    public BookResource(BookService bookService, GenerateIDService generateSequence) {
        this.bookService = bookService;
        this.generateSequence = generateSequence;
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws Exception {
        LOGGER.info(ConstantString.REST_SAVE_LOG.replaceAll("#ENTITY", ENTITY), book);
        if (book.getId() != null) {
            throw new Exception(ConstantString.ID_EXISTS.replaceAll("#ID", String.valueOf(book.getId()))
                    .replaceAll("#ENTITY", ENTITY));
        }
        Book save = this.bookService.save(book);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/book")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) throws Exception {
        LOGGER.info(ConstantString.REST_UPDATE_LOG.replaceAll("#ENTITY", ENTITY), book);
        if (book.getId() == null) {
            throw new Exception(ConstantString.ID_NULL.replaceAll("#ID", null)
                    .replaceAll("#ENTITY", ENTITY));
        }
        Book save = this.bookService.save(book);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/book")
    public ResponseEntity<Page<Book>> findAllBook(Pageable pageable) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return ResponseEntity.ok(this.bookService.findAll(pageable));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), id);
        return ResponseEntity.ok(this.bookService.findById(id));
    }

    @DeleteMapping("/book")
    public ResponseEntity deleteBook(@RequestBody Book book) throws Exception {
        LOGGER.info(ConstantString.REST_DELETE_LOG.replaceAll("#ENTITY", ENTITY), book);
        this.bookService.delete(book);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
