package com.tolstolutskyi.service;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.tolstolutskyi.common.Constants.CLOUDINARY_IMAGES_URL_HOST;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ImageService imageService;

    public BookService(BookRepository bookRepository, ImageService imageService) {
        this.bookRepository = bookRepository;
        this.imageService = imageService;
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void setImageLink(Long id, String link) throws IOException {
        Book book = bookRepository.findById(id).orElseGet(null);
        if (book != null && book.getPhotoLink() != null && book.getPhotoLink().startsWith(CLOUDINARY_IMAGES_URL_HOST)) {
            imageService.deleteImage(book.getPhotoLink());
        }
        bookRepository.saveImage(id, link);
    }
}
