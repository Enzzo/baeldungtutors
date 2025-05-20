package ru.vasilev.app.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vasilev.app.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
