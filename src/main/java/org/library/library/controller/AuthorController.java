package org.library.library.controller;

import lombok.RequiredArgsConstructor;
import org.library.library.entity.Author;
import org.library.library.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.createNewAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authorList = authorService.getAllAuthors();
        return new ResponseEntity<>(authorList, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<Author>> getAuthorByName(@RequestParam String name) {
        return new ResponseEntity<>(authorService.findAuthorByName(name), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<Author> getAuthorById(@RequestParam Long id) {
        return new ResponseEntity<>(authorService.findAuthorById(id), HttpStatus.OK);
    }
}
