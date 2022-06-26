package com.bookstore.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	NO_BOOK("Book Not Available", HttpStatus.BAD_REQUEST),
	BOOK_NOT_FOUND("Book Not Found In Store", HttpStatus.NOT_FOUND),
	BOOK_ALREADY_EXIST("Book Already Exist In Store", HttpStatus.BAD_REQUEST), DATA_MISSING("Data Missing", HttpStatus.BAD_REQUEST),
	INVALID_QUANTITY("Invalid quantity passed", HttpStatus.BAD_REQUEST),
    INVALID_PRICE("Invalid price passed", HttpStatus.BAD_REQUEST),
    PROMOTION_NOT_FOUND("Promotion Code Not Founf", HttpStatus.BAD_REQUEST);

    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	ErrorCode(String msg, HttpStatus status) {
        this.message = msg;
        this.status = status;
    }
}
