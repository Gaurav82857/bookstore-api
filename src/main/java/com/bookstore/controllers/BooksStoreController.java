package com.bookstore.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entities.BookOrderDetails;
import com.bookstore.entities.BookOrderMap;
import com.bookstore.model.dto.BookOrderDetailDto;
import com.bookstore.model.dto.CheckoutDto;
import com.bookstore.model.dto.ResponseDto;
import com.bookstore.service.BookStoreService;
import com.bookstore.service.CheckoutService;
import com.bookstore.service.impl.AssertServiceImpl;
import com.spring.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(Constants.API_URL)
public class BooksStoreController {

	@Autowired
	private final BookStoreService bookStoreService;
	@Autowired
	private final CheckoutService checkoutService;
	@Autowired
	private final AssertServiceImpl assertService;

	@Autowired
	public BooksStoreController(BookStoreService bookStoreService, CheckoutService checkoutService, AssertServiceImpl assertService) {
		this.bookStoreService = bookStoreService;
		this.checkoutService = checkoutService;
		this.assertService = assertService;
	}

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ResponseEntity<ResponseDto<?>> getAllBooks(){
		ResponseDto<Set<BookOrderDetails>> response = new ResponseDto<>(bookStoreService.getAllBooks());
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/{ISBN}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResponseDto<?>> getBookByISBN(@PathVariable("ISBN") String ISBN){
		log.info("Start getBookByISBN with id: {}", ISBN);
		assertService.assetISBN(ISBN);
		ResponseDto<BookOrderDetails> response = new ResponseDto<>(bookStoreService.getBookByISBN(ISBN));
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseDto<?>> checkout(@RequestBody CheckoutDto checkoutDTO){
		assertService.assetCheckout(checkoutDTO);
		ResponseDto<BookOrderMap> response = new ResponseDto<>(checkoutService.checkout(checkoutDTO));
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value="/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseDto<?>> addBook(@RequestBody BookOrderDetailDto book){
		assertService.assetSaveBook(book);
		ResponseDto<BookOrderDetails> response = new ResponseDto<>(bookStoreService.addBookToStore(book.getBookOrderDetail()));
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value="/", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ResponseDto<?>> updateBook(@RequestBody BookOrderDetailDto book){
		assertService.assetUpdateBook(book);
		bookStoreService.updateBookStore(book.getBookOrderDetail().getBookStore());
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@RequestMapping(value = "/{ISBN}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResponseDto<?>> deleteBook(@PathVariable("ISBN") String ISBN){
		log.info("Start deleteBook with id: {}", ISBN);
		assertService.assetISBN(ISBN);
		bookStoreService.deleteBookByISBN(ISBN);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}


}
