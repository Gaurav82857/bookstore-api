package com.bookstore.entities;

import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BookStore")
public class BookStore {
   
	@Id
    private String id;
   
	@Embedded
    private Book book;
    private int quantity;

    public BookStore(){
        this.id = UUID.randomUUID().toString();
    }
}
