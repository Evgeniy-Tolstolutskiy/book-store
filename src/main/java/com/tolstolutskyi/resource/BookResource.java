package com.tolstolutskyi.resource;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookResource {
    private final BookRepository bookRepository;

    public BookResource(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PutMapping
    public ResponseEntity save(@RequestBody @Valid Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }
}
