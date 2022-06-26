package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.BookStore;

@Repository
public interface BookStoreRepository extends CrudRepository<BookStore,String> {
    @Query("from BookStore where book.ISBN = :ISBN")
    BookStore findBookByISBN(String ISBN);
    
    @Query("from BookStore")
    List<BookStore> findAllBooks();
}
