package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.Discount;
import com.bookstore.enums.BookType;

@Repository
public interface DiscountRepository extends CrudRepository<Discount,String> {
	Discount findDiscountsByBookType(BookType bookTypes);
    Discount findDiscountByPromotionCode(String promotionCode);
}
