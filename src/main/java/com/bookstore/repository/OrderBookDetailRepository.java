package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.BookOrderDetails;

@Repository
public interface OrderBookDetailRepository extends CrudRepository<BookOrderDetails,String> {
	
}