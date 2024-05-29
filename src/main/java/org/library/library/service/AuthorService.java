package org.library.library.service;

import lombok.RequiredArgsConstructor;
import org.library.library.entity.Author;
import org.library.library.exception.AuthorNotFoundException;
import org.library.library.exception.EmptyParameterException;
import org.library.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author createNewAuthor(Author author) {
        if (author.getFirstName().isEmpty() || author.getLastName().isEmpty()) {
            throw new EmptyParameterException("Author must have at least one first and last name");
        }
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthorByName(String name) {
        List<Author> authors = getAllAuthors();
        List<Author> filteredAuthors = new ArrayList<>();

        for (Author author : authors) {
            if (author.getFirstName().equals(name) || author.getLastName().equals(name)) {
                filteredAuthors.add(author);
            }
        }

        if (filteredAuthors.isEmpty()) {
            throw new AuthorNotFoundException("Author not found with name " + name);
        }

        return filteredAuthors;
    }

    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(String.valueOf(id)));
    }
}
