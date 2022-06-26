package com.bookstore.service.impl;


import java.util.Objects;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.entities.BookOrderDetails;
import com.bookstore.entities.BookOrderMap;
import com.bookstore.entities.Discount;
import com.bookstore.enums.OrderStatus;
import com.bookstore.enums.PromotionCode;
import com.bookstore.model.dto.CheckoutDto;
import com.bookstore.repository.CheckoutRepository;
import com.bookstore.repository.DiscountRepository;
import com.bookstore.repository.OrderBookDetailRepository;
import com.bookstore.service.BookStoreService;
import com.bookstore.service.CheckoutService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final DiscountRepository discountRepository;
    private final BookStoreService bookStoreService;
    private final OrderBookDetailRepository orderBookDetailRepository;


    public CheckoutServiceImpl(CheckoutRepository checkoutRepository, DiscountRepository discountRepository, BookStoreService bookStoreService,
                               OrderBookDetailRepository orderBookDetailRepository) {
        this.checkoutRepository = checkoutRepository;
        this.discountRepository = discountRepository;
        this.bookStoreService = bookStoreService;
        this.orderBookDetailRepository = orderBookDetailRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BookOrderMap checkout(CheckoutDto checkoutDTO) {
        BookOrderMap bookOrder = new BookOrderMap();
        checkoutRepository.save(bookOrder);
        log.info("created a {} order with id {}", bookOrder.getOrderStatus(), bookOrder.getId());
        Set<BookOrderDetails> booksToBuy = bookStoreService.deleteBooksFromStore(checkoutDTO.getBooks());
        orderBookDetailRepository.saveAll(booksToBuy);
        for (BookOrderDetails bookOrderDetails : booksToBuy) {
        	fillOrderDetails(bookOrderDetails, bookOrder);
		}
        bookOrder.setOrderStatus(OrderStatus.COMPLETE);
        checkoutRepository.save(bookOrder);
        return bookOrder;
    }

    private void fillOrderDetails(BookOrderDetails book, BookOrderMap order){
        order.setBook(book);
        Discount discounts = discountRepository.findDiscountsByBookType(order.getBook().getType());
        order.setDiscount(discounts);
        order.setDiscountedPrice(getDiscountedPrice(order));
    }
    
	private double getDiscountedPrice(BookOrderMap order){
		double price = 0.0;
		
		BookOrderDetails book = order.getBook();
		double currentPrice = book.getPrice();
		double disccount = order.getDiscount().getDiscountPercent();
		
		price = currentPrice - currentPrice * (disccount/100) ;
		
		String promotionCodeDiscount = order.getDiscount().getPromotionCode();
		if(Objects.nonNull(promotionCodeDiscount)){
			Double discount = PromotionCode.valueOf(promotionCodeDiscount).getDiscount();
			price = price * (discount)/100;
		}
		return price;
	}
}