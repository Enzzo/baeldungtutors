package ru.vasilev.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.vasilev.app.exception.BookNotFoundException;
import ru.vasilev.app.model.Book;
import ru.vasilev.app.repository.BookRepository;

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

    public List findByTitle(String title){
        return bookRepo.findByTitle(title);
    }

    public Iterable<Book> findAll(){
        return bookRepo.findAll();
    }

    public Book create(Book book){
        return bookRepo.save(book);
    }

    public Book update(Book book, Long id) throws BookNotFoundException{
		return bookRepo.findById(id).map(b -> {
			b.setAuthor(book.getAuthor());
			b.setTitle(book.getTitle());
			return bookRepo.save(b);
		})
		.orElseThrow(BookNotFoundException::new);
    }
    
    public void delete(Long id) throws BookNotFoundException{
    	bookRepo.findById(id).orElseThrow(BookNotFoundException::new);
    	bookRepo.deleteById(id);
    }
}