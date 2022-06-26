package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static com.bookstore.utils.TestDataUtil.*;

import com.bookstore.controllers.BooksStoreController;
import com.bookstore.entities.BookOrderDetails;
import com.bookstore.model.dto.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksStoreServiceTest {

    private final String API_URL = "http://localhost:%s/bookstore-api%s";
    private final String ROOT ="/";
    private final String BY_ISBN ="/%s";

    @LocalServerPort
    private int port;

    @Autowired
    private BooksStoreController booksShopController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void contextLoads() {
        assertNotNull(booksShopController);
        assertNotNull(restTemplate);
        log.info("web tests started with port number {}", port);
    }

    @Test
    @Order(2)
    public void testSaveBooks() {
        String saveBookURI = String.format(API_URL, port);
        ResponseDto<?> resp1 = this.restTemplate.postForObject(saveBookURI,testBook1(),ResponseDto.class);
        log.info("response : {}", resp1);
        assertNotNull(resp1);
        ResponseDto<?> resp2 = this.restTemplate.postForObject(saveBookURI,testBook2(),ResponseDto.class);
        log.info("response : {}", resp2);
        assertNotNull(resp2);
    }

    @Test
    @Order(3)
    public void testGetAllBooks() {
        String getAllBooksURI = String.format(API_URL, port, ROOT);
        ResponseDto<?> resp = this.restTemplate.getForObject(getAllBooksURI, ResponseDto.class);
        log.info("response : {}", resp);
        assertNotNull(resp);
        assertNotNull(resp.getBody());
        Collection<BookOrderDetails> books = (Collection<BookOrderDetails>) resp.getBody();
        assertTrue(books.size()>0);
    }

    @Test
    @Order(3)
    public void testGetBookByISBN() {
        String getBookByISBNURI = String.format(API_URL, port,String.format(BY_ISBN, TEST1_ISBN));
        ResponseDto<?> resp = this.restTemplate.getForObject(getBookByISBNURI, ResponseDto.class);
        log.info("response : {}", resp);
        assertNotNull(resp);
        assertNotNull(resp.getBody());
    }

    @Test
    @Order(4)
    public void testUpdateBookByISBN() {
        String getBookByISBNURI = String.format(API_URL, port,String.format(BY_ISBN, TEST1_ISBN));

        BookOrderDetails bookToUpdate = testBook1().getBookOrderDetail();
        bookToUpdate.setName(bookToUpdate.getName()+" UPDATED");
        String putURI = String.format(API_URL, port, ROOT);
        restTemplate.put(putURI, bookToUpdate);
        ResponseDto<?> updatedBookResponse = this.restTemplate.getForObject(getBookByISBNURI, ResponseDto.class);
        String updatedName = (String) updatedBookResponse.getBodyAsMap().get("name");
        assertTrue(updatedName.contains("UPDATED"));
    }

    @Test
    @Order(5)
    public void testDeleteBook() {
        String getDropBookURI = String.format(API_URL, port, String.format(BY_ISBN, TEST1_ISBN));
        this.restTemplate.delete(getDropBookURI);
        String getBookByISBNURI = String.format(API_URL, port, String.format(BY_ISBN, TEST1_ISBN));
        ResponseDto searchResponse = restTemplate.getForObject(getBookByISBNURI, ResponseDto.class);
        assertTrue(searchResponse.getBodyAsString().contains("ISBN was not found"));
    }

    @Test
    @Order(6)
    public void testFindNonExistentISBN() {
        String getBookByISBNURI = String.format(API_URL, port,String.format(BY_ISBN, TEST_ISBN_INVALID));
        ResponseDto<?> resp = this.restTemplate.getForObject(getBookByISBNURI, ResponseDto.class);
        log.info("response : {}", resp);
        assertTrue(resp.getBodyAsString().contains("ISBN was not found"));
    }
}
