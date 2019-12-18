package com.tolstolutskyi.resource;

import com.tolstolutskyi.service.BookService;
import com.tolstolutskyi.service.ImageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookResource {
    private final BookService bookService;
    private final ImageService imageService;

    public BookResource(BookService bookService, ImageService imageService) {
        this.bookService = bookService;
        this.imageService = imageService;
    }

    @PostMapping("/{id}/image")
    public Map<String, String> saveImage(@RequestParam("file") MultipartFile file, @PathVariable("id") long id) throws IOException {
        String link = imageService.saveImage(file);
        bookService.setImageLink(id, link);
        return Collections.singletonMap("photo", link);
    }
}
