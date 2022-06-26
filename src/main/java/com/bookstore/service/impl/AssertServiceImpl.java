package com.bookstore.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bookstore.enums.ErrorCode;
import com.bookstore.exception.BookAppException;
import com.bookstore.model.dto.BookOrderDetailDto;
import com.bookstore.model.dto.BookQuantityDto;
import com.bookstore.model.dto.CheckoutDto;
import com.bookstore.repository.BookStoreRepository;
import com.bookstore.repository.CheckoutRepository;
import com.bookstore.repository.DiscountRepository;
import com.bookstore.repository.OrderBookDetailRepository;
import com.bookstore.service.AssertService;

public class AssertServiceImpl implements AssertService {

    final BookStoreRepository bookStoreRepository;
    final CheckoutRepository checkoutRepository;
    final DiscountRepository discountRepository;
    final OrderBookDetailRepository orderBookDetailRepository;

    public AssertServiceImpl(
            BookStoreRepository bookStoreRepository, CheckoutRepository checkoutRepository,
            DiscountRepository discountRepository, OrderBookDetailRepository orderBookDetailRepository) {
        this.bookStoreRepository = bookStoreRepository;
        this.checkoutRepository = checkoutRepository;
        this.discountRepository = discountRepository;
        this.orderBookDetailRepository = orderBookDetailRepository;
    }

    @Override
    public void assetISBN(String isbn) {
        if(StringUtils.isEmpty(isbn)){
            throw new BookAppException(ErrorCode.DATA_MISSING);
        }
        if(Objects.isNull(bookStoreRepository.findBookByISBN(isbn))){
            throw new BookAppException(ErrorCode.BOOK_NOT_FOUND);
        }
    }

    @Override
    public void assetSaveBook(BookOrderDetailDto book) {
    	basicValidation(book);
        if(Objects.nonNull(bookStoreRepository.findBookByISBN(book.getISBN()))){
            throw new BookAppException(ErrorCode.BOOK_ALREADY_EXIST);
        }
    }

    @Override
    public void assetUpdateBook(BookOrderDetailDto book) {
    	basicValidation(book);
        this.assetISBN(book.getISBN());
    }

    @Override
    public void assetCheckout(CheckoutDto checkoutDTO) {
        checkoutDTO.getBooks().stream().map(BookQuantityDto::getISBN).forEach(this::assetISBN);
        checkoutDTO.getBooks().stream().map(BookQuantityDto::getQuantity).forEach(quantity->{
            if(Objects.isNull(quantity) || !(quantity>0)){
                throw new BookAppException(ErrorCode.INVALID_QUANTITY);
            }
        });
    }

    private void basicValidation(BookOrderDetailDto book) {
        if(!(book.getQuantity()>0)){
            throw new BookAppException(ErrorCode.INVALID_QUANTITY);
        }
        if(!(book.getPrice()>0)){
            throw new BookAppException(ErrorCode.INVALID_PRICE);
        }
        if(StringUtils.isEmpty(book.getAuthor()) || Objects.isNull(book.getType()) 
        		|| StringUtils.isEmpty(book.getISBN()) || StringUtils.isEmpty(book.getName())){
            throw new BookAppException(ErrorCode.DATA_MISSING);
        }
    }
}
