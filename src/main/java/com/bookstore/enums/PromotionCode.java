package com.bookstore.enums;

import lombok.Getter;

@Getter
public enum PromotionCode {

	BASIC("05PERCENTOFF", 5.0),
	SILVER("10PERCENTOFF", 10.05),
	GOLD("15PERCENTOFF", 15.50),
	ELITE("20PERCENTOFF", 19.99);
	
    private final String code;
    private final Double discount;

	PromotionCode(String code, Double discount) {
        this.code = code;
        this.discount = discount;
	}
}
