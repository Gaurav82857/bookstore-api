package com.bookstore.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.BeanUtils;

import com.bookstore.enums.BookType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "book_order_details")
public class BookOrderDetails {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
    @CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date orderDate;
	
    private String ISBN;
    private String name;
    private String description;
    private String author;
    private BookType type;
    private double price;

    public BookOrderDetails() {
        this.id = UUID.randomUUID().toString();
        this.orderDate = new Date();
    }

    @JsonIgnore
    public Book getBook(){
        Book book = new Book();
        BeanUtils.copyProperties(this,book);
        return book;
    }

    @JsonIgnore
    public BookStore getBookStore(){
        BookStore bookStore = new BookStore();
        bookStore.setBook(this.getBook());
        bookStore.setId(StringUtils.isEmpty(this.id)?UUID.randomUUID().toString():this.id);
        return bookStore;
    }

    public static BookOrderDetails toEntity(BookStore bookStore){
        BookOrderDetails bookOrderDetail = new BookOrderDetails();
        BeanUtils.copyProperties(bookStore.getBook(), bookOrderDetail);
        return bookOrderDetail;
    }
}
