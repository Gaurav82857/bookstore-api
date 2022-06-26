package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.bookstore.utils.TestDataUtil.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookstore.entities.BookOrderMap;
import com.bookstore.enums.OrderStatus;
import com.bookstore.repository.CheckoutRepository;
import com.bookstore.repository.DiscountRepository;
import com.bookstore.repository.OrderBookDetailRepository;
import com.bookstore.service.impl.CheckoutServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CheckoutServiceTest {

    @Mock
    private CheckoutRepository checkoutRepository;
    @Mock
    private DiscountRepository discountRepository;
    @Mock
    private BookStoreService bookStoreService;
    @Mock
    private OrderBookDetailRepository orderBookDetailRepository;

    private CheckoutService checkoutService;

    @BeforeEach
    void setupValidationServiceWithMocks() {
        checkoutService = new CheckoutServiceImpl(checkoutRepository,discountRepository,bookStoreService,orderBookDetailRepository);
    }

    @Test
    public void testSuccessfulCheckout(){
        when(checkoutRepository.save(any())).thenReturn(new BookOrderMap());
        when(bookStoreService.deleteBooksFromStore(getTestCheckoutDTOWithTwoItems().getBooks())).thenReturn(getTestBookOrderDetailSetForCheckoutDto());
        when(orderBookDetailRepository.saveAll(any())).then(returnsFirstArg());
        when(discountRepository.findDiscountsByBookType(any())).thenReturn( getTestFictionalTypeDiscount_5PercentOff());
        when(checkoutRepository.save(any())).then(returnsFirstArg());

        BookOrderMap completedOrder = checkoutService.checkout(getTestCheckoutDTOWithTwoItems());
        log.info("checkout data : {}", completedOrder);

        assertEquals(completedOrder.getOrderStatus(), OrderStatus.COMPLETE);
        assertTrue(completedOrder.getBook().equals( testBook1().getBookOrderDetail()));

        Double expectedPriceBeforeDiscount = testBook1().getPrice() * testBook1().getQuantity() +
                        testBook2().getPrice() * testBook2().getQuantity();

        assertEquals(expectedPriceBeforeDiscount, completedOrder.getDiscountedPrice());

        Double expectedPriceAfterDiscounts = (testBook1().getPrice() * testBook1().getQuantity()
                        * ((100-getTestFictionalTypeDiscount_5PercentOff().getDiscountPercent())/100))
                        +
                (testBook2().getPrice() * testBook2().getQuantity()
                        * ((100-getTestFictionalTypeDiscount_5PercentOff().getDiscountPercent())/100));

        /* optional promotion code off */
        expectedPriceAfterDiscounts *= ((100-getTestOptionalDiscount_10PercentOff().getDiscountPercent())/100);

        assertEquals(expectedPriceAfterDiscounts, completedOrder.getDiscountedPrice());
    }
}
