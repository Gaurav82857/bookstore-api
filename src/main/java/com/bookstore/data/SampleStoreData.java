package com.bookstore.data;

import com.bookstore.entities.Book;
import com.bookstore.entities.BookStore;
import com.bookstore.enums.BookType;
import com.bookstore.repository.BookStoreRepository;

/* Sample Data*/
public class SampleStoreData {

    private BookStoreRepository bookStoreRepository;

    public SampleStoreData() {
    	
    }
    
    public SampleStoreData(BookStoreRepository bookStoreRepository) {
        this.bookStoreRepository = bookStoreRepository;
    }

    public void loadStore(){
        Book b1 = new Book();
        b1.setName("Book1");
        b1.setAuthor("Author1");
        b1.setISBN("A12345");
        b1.setDescription("UAE History");
        b1.setType(BookType.HISTORY);
        b1.setPrice(100);
        BookStore b1Store = new BookStore();
        b1Store.setBook(b1);
        b1Store.setQuantity(10);

        Book b2 = new Book();
        b2.setName("Book2");
        b2.setAuthor("Author2");
        b2.setISBN("B12345");
        b2.setDescription("Programming Language");
        b2.setType(BookType.COMPUTER);
        b2.setPrice(85.50);
        BookStore b2Store = new BookStore();
        b2Store.setBook(b2);
        b2Store.setQuantity(2);

        Book b3 = new Book();
        b3.setName("Book3");
        b3.setAuthor("Author3");
        b3.setISBN("C12345");
        b3.setDescription("ALGEBRA");
        b3.setType(BookType.MATHEMATICS);
        b3.setPrice(91.90);
        BookStore b3Store = new BookStore();
        b3Store.setBook(b3);
        b3Store.setQuantity(1);

        this.bookStoreRepository.save(b1Store);
        this.bookStoreRepository.save(b2Store);
        this.bookStoreRepository.save(b3Store);
    }
}
