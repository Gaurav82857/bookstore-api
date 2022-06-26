package com.bookstore.entities;

import javax.persistence.Embeddable;

import org.springframework.beans.BeanUtils;

import com.bookstore.enums.BookType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Book {
    private String ISBN;
    private String name;
    private String description;
    private String author;
    private BookType type;
    private double price;

    public Book(){
    }
    
    public Book(Book book){
        BeanUtils.copyProperties(book, this);
    }

}
