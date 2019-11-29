package com.tolstolutskyi.service;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(Book book) {

    }
}
