package org.library.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private long id;
    private String title;
    @ManyToMany
    private List<Author> author;
    private String isbn;
    private String description;
    private String language;
    private String category;
    private String publishedDate;
    private int pages;
}
