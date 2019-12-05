package com.tolstolutskyi.service;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void setImageLink(Long id, String link) {
        bookRepository.saveImage(id, link);
    }
}
