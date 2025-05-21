package ru.vasilev.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.vasilev.app.model.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootBootstrapLiveTest {
	@LocalServerPort
	private int port;
	private String API_ROOT;
	
	@BeforeEach
	public void setUp() {
		API_ROOT = "http://localhost:" + port + "/api/books";
		RestAssured.port = port;
	}
	
	private Book createRandomBook() {
		final Book book = new Book();
		book.setTitle("title1");
		book.setAuthor("author1");
		return book;
	}
	
	private String createBookAsUri(Book book) {
		final Response response = RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(book)
				.post(API_ROOT);
		return API_ROOT + "/" + response.jsonPath().getByte("id");
	}
	
	@Test
	public void whenGetAllBooksThenOk() {
		Response response = RestAssured.get(API_ROOT);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
	
	@Test
	public void whenGetBookByTitleThenOk() {
		Book book = createRandomBook();
		createBookAsUri(book);
		Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.as(List.class).size() > 0);
	}
	
	@Test
	public void whenGetCreatedBookBuIdThenOk() {
		Book book = createRandomBook();
		String location = createBookAsUri(book);
		Response response = RestAssured.get(location);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals(book.getTitle(), response.jsonPath().get("title"));
	}
	
	@Test
	public void whenGetNotExistBookByIdThenNotFound() {
		Response response = RestAssured.get(API_ROOT + "/" + 9);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
	
	@Test
	public void whenCreateNewBookThenCreated() {
		Book book = createRandomBook();
		Response response = RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(book)
				.post(API_ROOT);
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	}
	
	@Test
	public void whenInvalidBookThenError() {
		Book book = createRandomBook();
		book.setAuthor(null);
		Response response = RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(book)
				.post(API_ROOT);
		assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());
	}
	
	@Test
	public void whenUpdateCreatedBookThenUpdated() {
		
	}
}
