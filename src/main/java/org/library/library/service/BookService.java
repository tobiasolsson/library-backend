package org.library.library.service;

import lombok.RequiredArgsConstructor;
import org.library.library.entity.Book;
import org.library.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        // TODO: lookup author and add to the book before save. Or other solution?
        return bookRepository.save(book);
    }
}
