package com.bookstore.service;

import java.util.Set;

import com.bookstore.entities.BookOrderDetails;
import com.bookstore.entities.BookStore;
import com.bookstore.model.dto.BookQuantityDto;

public interface BookStoreService {

	BookOrderDetails addBookToStore(BookOrderDetails bookDetail); // add book to store
	
	void updateBookStore(BookStore bookDetail); // update book details to store
	
	Set<BookOrderDetails> getAllBooks();// get all books
	
	BookOrderDetails getBookByISBN(String ISBN);// get single book by ISBN
	
	void deleteBookByISBN(String ISBN);// delete single book by ISBN

	Set<BookOrderDetails> deleteBooksFromStore(Set<BookQuantityDto> books); //delete multiple books
}
