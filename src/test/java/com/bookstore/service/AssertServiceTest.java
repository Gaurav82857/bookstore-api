package com.bookstore.service;

import static com.bookstore.utils.TestDataUtil.TEST_ISBN_INVALID;
import static com.bookstore.utils.TestDataUtil.getTestCheckoutDTOWithTwoItems;
import static com.bookstore.utils.TestDataUtil.testBook1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookstore.entities.BookStore;
import com.bookstore.enums.ErrorCode;
import com.bookstore.exception.BookAppException;
import com.bookstore.model.dto.BookOrderDetailDto;
import com.bookstore.model.dto.CheckoutDto;
import com.bookstore.repository.BookStoreRepository;
import com.bookstore.repository.CheckoutRepository;
import com.bookstore.repository.DiscountRepository;
import com.bookstore.repository.OrderBookDetailRepository;
import com.bookstore.service.impl.AssertServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AssertServiceTest {
	@Mock
	private BookStoreRepository bookStoreRepository;
	@Mock
	private CheckoutRepository checkoutRepository;
	@Mock
	private DiscountRepository discountRepository;
	@Mock
	private OrderBookDetailRepository orderBookDetailRepository;

	private AssertService assertService;

	@BeforeEach
	void setupValidationServiceWithMocks() {
		assertService = new AssertServiceImpl(bookStoreRepository,checkoutRepository,discountRepository,orderBookDetailRepository);
	}

	@Test
	public void validateISBN_WithNonExistentISBN(){

		when(bookStoreRepository.findBookByISBN(TEST_ISBN_INVALID)).thenReturn(null);
		BookAppException ex = assertThrows(BookAppException.class,() -> {
			assertService.assetISBN(TEST_ISBN_INVALID);
		});
		assertEquals(ex.getErrorCode(),ErrorCode.BOOK_NOT_FOUND);
	}

	@Test
	public void validateSaveBook_WithExistingISBN(){

		when(bookStoreRepository.findBookByISBN(testBook1().getISBN())).thenReturn(new BookStore());
		BookAppException ex = assertThrows(BookAppException.class,() -> {
			assertService.assetSaveBook(testBook1());
		});
		assertEquals(ex.getErrorCode(),ErrorCode.BOOK_ALREADY_EXIST);
	}

	@Test
	public void validateUpdateBook_WithBadISBN(){

		when(bookStoreRepository.findBookByISBN(TEST_ISBN_INVALID)).thenReturn(new BookStore());
		BookAppException ex = assertThrows(BookAppException.class,() -> {
			BookOrderDetailDto invalidIdBook = testBook1();
			assertService.assetUpdateBook(invalidIdBook);
		});
		assertEquals(ex.getErrorCode(),ErrorCode.BOOK_NOT_FOUND);
	}

	@Test
	public void validateCheckout_InvalidCouponCode(){
		when(bookStoreRepository.findBookByISBN(anyString())).thenReturn(new BookStore());

		BookAppException ex = assertThrows(BookAppException.class,() -> {
			CheckoutDto checkoutDTO = getTestCheckoutDTOWithTwoItems();
			assertService.assetCheckout(checkoutDTO);
		});
		assertEquals(ex.getErrorCode(),ErrorCode.PROMOTION_NOT_FOUND);
	}
}
