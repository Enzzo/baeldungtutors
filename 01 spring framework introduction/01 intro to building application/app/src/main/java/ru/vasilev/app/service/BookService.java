package ru.vasilev.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vasilev.app.exception.BookIdMismatchException;
import ru.vasilev.app.model.Book;
import ru.vasilev.app.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book findById(Long id){
        return bookRepo.findById(id).orElse(null);
    }

    public Book findByTitle(String title){
        return bookRepo.findByTitle(title).get(0);
    }

    public Iterable<Book> findAll(){
        return bookRepo.findAll();
    }

    public Book create(Book book){
        return bookRepo.save(book);
    }

    public Book update(Long id, Book book) throws BookIdMismatchException {
        if(book.getId() != id){
            throw new BookIdMismatchException();
        }
    }
}