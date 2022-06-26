package com.bookstore.model.dto;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.bookstore.entities.BookOrderDetails;
import com.bookstore.enums.BookType;

import lombok.Data;

@Data
public class BookOrderDetailDto {
	private String ISBN;
	private String name;
	private String description;
	private String author;
	private BookType type;
	private double price;
	private int quantity;

	public BookOrderDetails getBookOrderDetail(){
		BookOrderDetails orderDetail = new BookOrderDetails();
		BeanUtils.copyProperties(this, orderDetail);
		return orderDetail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookOrderDetailDto that = (BookOrderDetailDto) o;
		return Objects.equals(ISBN, that.ISBN);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ISBN);
	}

	public static BookOrderDetails toEntity(BookOrderDetailDto bookOrderDetailDto) {
		BookOrderDetails bookOrderDetail = new BookOrderDetails();
		BeanUtils.copyProperties(bookOrderDetailDto.getBookOrderDetail(),bookOrderDetail);
		return bookOrderDetail;
	}
}
