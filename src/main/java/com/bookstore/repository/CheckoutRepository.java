package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.BookOrderMap;

@Repository
public interface CheckoutRepository extends CrudRepository<BookOrderMap,String> {
	
}