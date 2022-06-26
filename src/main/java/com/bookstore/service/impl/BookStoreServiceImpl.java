package com.bookstore.service.impl;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.entities.BookOrderDetails;
import com.bookstore.entities.BookStore;
import com.bookstore.enums.ErrorCode;
import com.bookstore.exception.BookAppException;
import com.bookstore.model.dto.BookQuantityDto;
import com.bookstore.repository.BookStoreRepository;
import com.bookstore.service.BookStoreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookStoreServiceImpl implements BookStoreService {

	private final BookStoreRepository bookStoreRepository;

	public BookStoreServiceImpl(BookStoreRepository bookStoreRepository) {
		this.bookStoreRepository = bookStoreRepository;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public BookOrderDetails addBookToStore(BookOrderDetails bookItem) {
		BookStore savedBookStoreItem = bookStoreRepository.save(bookItem.getBookStore());
		return BookOrderDetails.toEntity(savedBookStoreItem);
	}

	@Override
	public BookOrderDetails getBookByISBN(String ISBN) {
		return BookOrderDetails.toEntity(bookStoreRepository.findBookByISBN(ISBN));
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void updateBookStore(BookStore bookStore) {
		BookStore currentBookStore = bookStoreRepository.findBookByISBN(bookStore.getBook().getISBN());
		BookStore updatedBookStore = new BookStore();
		updatedBookStore.setBook(bookStore.getBook());
		updatedBookStore.setQuantity(bookStore.getQuantity());
		updatedBookStore.setId(currentBookStore.getId());
		updatedBookStore = bookStoreRepository.save(updatedBookStore);
	}

	@Override
	public void deleteBookByISBN(String ISBN) {
		BookStore bookEntryToDrop = bookStoreRepository.findBookByISBN(ISBN);
		bookStoreRepository.delete(bookEntryToDrop);
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Set<BookOrderDetails> deleteBooksFromStore(Set<BookQuantityDto> BookQuantityDtoSet) {
		Set<BookOrderDetails> removedBooks = new LinkedHashSet<>();

		for(BookQuantityDto bookQuantityDto : BookQuantityDtoSet){

			BookStore bookStore = bookStoreRepository.findBookByISBN(bookQuantityDto.getISBN());
			int quantityToRemove = bookQuantityDto.getQuantity();
			int remainingQuantity = bookStore.getQuantity();
			if(quantityToRemove<=remainingQuantity) {
				bookStore.setQuantity(remainingQuantity-quantityToRemove);
			}
			else {
				throw new BookAppException(ErrorCode.NO_BOOK);
			}
			BookOrderDetails bookOrderDetail = new BookOrderDetails();
			BeanUtils.copyProperties(bookStore.getBook(), bookOrderDetail);
			removedBooks.add(bookOrderDetail);
			this.bookStoreRepository.save(bookStore);
		}
		return removedBooks;
	}

	@Override
	public Set<BookOrderDetails> getAllBooks() {
		Iterator<BookStore> iterator = this.bookStoreRepository.findAll().iterator();
		Set<BookOrderDetails> bookOrderDetails = new LinkedHashSet<>();
		while (iterator.hasNext()){
			BookStore bookStore = iterator.next();
			bookOrderDetails.add(BookOrderDetails.toEntity(bookStore));
		}
		return bookOrderDetails;
	}
}
