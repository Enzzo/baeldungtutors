package ru.vasilev.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.vasilev.app.exception.BookNotFoundException;
import ru.vasilev.app.model.Book;
import ru.vasilev.app.service.BookService;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private Logger log = LoggerFactory.getLogger(BookController.class);
	
	private BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping("/title/{bookTitle}")
	public List getByTitle(@PathVariable String bookTitle) {
		return bookService.findByTitle(bookTitle);
	}
	
	@GetMapping("/{id}")
	public Book getById(@PathVariable Long id) {
		return bookService.findById(id);
	}
	
	@GetMapping
	public Iterable getAll() {
		return bookService.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book save(@RequestBody Book book) {
		log.info(book + "is created");
		return bookService.create(book);
	}
	
	@PutMapping("/{id}")
	public Book update(@RequestBody Book book, @PathVariable Long id) {
		try {
			return bookService.update(book, id);
		}
		catch(BookNotFoundException e) {
			log.error("BookNotFoundException");
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		try {
			bookService.delete(id);
		}catch(BookNotFoundException e) {};
	}
	/*
	 * delete void
	 */
	
}