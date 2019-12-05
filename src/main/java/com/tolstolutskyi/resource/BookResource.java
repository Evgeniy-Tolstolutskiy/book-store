package com.tolstolutskyi.resource;

import com.tolstolutskyi.model.Book;
import com.tolstolutskyi.repository.BookRepository;
import com.tolstolutskyi.service.BookService;
import com.tolstolutskyi.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

import static com.tolstolutskyi.common.FileUtils.convert;

@RestController
@RequestMapping("/books")
public class BookResource {
    private final BookService bookService;
    private final ImageService imageService;

    public BookResource(BookService bookService, ImageService imageService) {
        this.bookService = bookService;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid Book book) {
        bookService.save(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/image")
    public String saveImage(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) throws IOException {
        String link = imageService.saveImage(convert(file));
        bookService.setImageLink(id, link);
        return link;
    }
}
