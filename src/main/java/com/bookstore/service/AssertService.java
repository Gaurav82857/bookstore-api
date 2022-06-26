package com.bookstore.service;

import com.bookstore.model.dto.BookOrderDetailDto;
import com.bookstore.model.dto.CheckoutDto;

public interface AssertService {
    void assetISBN(String ISBN);
    
    void assetSaveBook(BookOrderDetailDto book);
    
    void assetUpdateBook(BookOrderDetailDto book);
    
    void assetCheckout(CheckoutDto checkoutDTO);
}
