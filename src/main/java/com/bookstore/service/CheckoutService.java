package com.bookstore.service;

import com.bookstore.entities.BookOrderMap;
import com.bookstore.model.dto.CheckoutDto;

public interface CheckoutService {
    BookOrderMap checkout(CheckoutDto checkoutDTO);
}
