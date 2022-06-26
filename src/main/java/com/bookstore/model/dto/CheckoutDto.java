package com.bookstore.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CheckoutDto {
    private Set<BookQuantityDto> books;
    private String promotionCode;
	public Set<BookQuantityDto> getBooks() {
		return books;
	}
	public void setBooks(Set<BookQuantityDto> books) {
		this.books = books;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
}
