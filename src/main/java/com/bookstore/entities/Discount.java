package com.bookstore.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bookstore.enums.BookType;
import com.bookstore.enums.PromotionCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "discounts")
public class Discount {
   
	@Id
    private String id;
    
	private Double discountPercent;
    private String promotionCode;
    private BookType bookType;

	public Discount(){
        this.id = UUID.randomUUID().toString();
    }
}
