package com.bookstore.data;

import com.bookstore.entities.Discount;
import com.bookstore.enums.BookType;
import com.bookstore.enums.PromotionCode;
import com.bookstore.repository.DiscountRepository;

public class SampleDiscountsData {

    private final DiscountRepository discountRepository;

    public SampleDiscountsData(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void loadDiscounts(){
        Discount discountOnHistory = new Discount();
        discountOnHistory.setBookType(BookType.HISTORY);
        discountOnHistory.setDiscountPercent(5.0);
        discountOnHistory.setPromotionCode(PromotionCode.BASIC.getCode());

        Discount discountOnComputer = new Discount();
        discountOnComputer.setBookType(BookType.COMPUTER);
        discountOnComputer.setDiscountPercent(10.50);
        discountOnComputer.setPromotionCode(PromotionCode.GOLD.getCode());
        
        Discount discountOnMaths = new Discount();
        discountOnMaths.setBookType(BookType.MATHEMATICS);
        discountOnMaths.setDiscountPercent(14.90);
        discountOnMaths.setPromotionCode(PromotionCode.ELITE.getCode());
       
        this.discountRepository.save(discountOnHistory);
        this.discountRepository.save(discountOnComputer);
        this.discountRepository.save(discountOnMaths);
    }
}
