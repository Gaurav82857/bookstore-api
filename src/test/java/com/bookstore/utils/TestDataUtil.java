package com.bookstore.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.bookstore.entities.BookOrderDetails;
import com.bookstore.entities.Discount;
import com.bookstore.enums.BookType;
import com.bookstore.enums.PromotionCode;
import com.bookstore.model.dto.BookOrderDetailDto;
import com.bookstore.model.dto.BookQuantityDto;
import com.bookstore.model.dto.CheckoutDto;

public class TestDataUtil {

    public static final String TEST1_ISBN = "12345";
    public static final String TEST2_ISBN = "67890";
    public static final String TEST_ISBN_INVALID = "0000000";

    public static final String TEST_PROMOTION_CODE = PromotionCode.GOLD.getCode();

    public static BookOrderDetailDto testBook1() {
        BookOrderDetailDto book = new BookOrderDetailDto();
        book.setType(BookType.HISTORY);
        book.setPrice(100.00);
        book.setAuthor("Author1");
        book.setISBN(TEST1_ISBN);
        book.setName("Name1");
        book.setDescription("Description1");
        book.setQuantity(10);
        return book;
    }

    public static BookOrderDetailDto testBook2() {
        BookOrderDetailDto book = new BookOrderDetailDto();
        book.setType(BookType.MATHEMATICS);
        book.setPrice(100.00);
        book.setAuthor("Author1");
        book.setISBN(TEST2_ISBN);
        book.setName("Name2");
        book.setDescription("Description2");
        book.setQuantity(5);
        return book;
    }

    public static CheckoutDto getTestCheckoutDTOWithTwoItems(){
    	CheckoutDto checkoutDTO = new CheckoutDto();
        Set<BookQuantityDto> bookQtyDTOSet = new HashSet<>();
        BookQuantityDto bookQtyDTO1 = new BookQuantityDto();
        BookQuantityDto bookQtyDTO2 = new BookQuantityDto();
        bookQtyDTO1.setISBN(testBook1().getISBN());
        bookQtyDTOSet.add(bookQtyDTO1);
        bookQtyDTO2.setISBN(testBook2().getISBN());
        bookQtyDTOSet.add(bookQtyDTO2);
        checkoutDTO.setBooks(bookQtyDTOSet);
        checkoutDTO.setPromotionCode(PromotionCode.BASIC.getCode());
        return checkoutDTO;
    }

    public static Set<BookOrderDetails> getTestBookOrderDetailSetForCheckoutDto(){
        Set<BookOrderDetails> removedBooks = new LinkedHashSet<>();
        removedBooks.add(testBook1().getBookOrderDetail());
        removedBooks.add(testBook2().getBookOrderDetail());
        return removedBooks;
    }

    public static Discount getTestFictionalTypeDiscount_5PercentOff(){
        Discount fictionDiscount = new Discount();
        fictionDiscount.setBookType(BookType.COMPUTER);
        fictionDiscount.setDiscountPercent(5.00);
        return fictionDiscount;
    }

    public static Discount getTestOptionalDiscount_10PercentOff(){
        Discount tenPercentOff = new Discount();
        tenPercentOff.setDiscountPercent(10.00);
        return tenPercentOff;
    }

    public static Set<BookOrderDetails> getBookOrderDetailSet(BookOrderDetails... bookOrderDetails){
        return new LinkedHashSet<>(Arrays.asList(bookOrderDetails));
    }
}
