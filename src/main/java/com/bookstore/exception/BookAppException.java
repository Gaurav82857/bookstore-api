package com.bookstore.exception;

import org.springframework.http.HttpStatus;

import com.bookstore.enums.ErrorCode;
import com.bookstore.model.dto.ResponseDto;

import lombok.Getter;

@Getter
public class BookAppException extends RuntimeException {

  	private static final long serialVersionUID = 1L;
	private final String errorMessage;
    private final HttpStatus httpStatus;
    private final Throwable throwable;
    private ErrorCode errorCode;

    public String getErrorMessage() {
		return errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public BookAppException(ErrorCode errorCode, Throwable throwable){
        super(throwable);
        this.throwable = throwable;
        this.errorMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getStatus();
    }

    public BookAppException(ErrorCode errorCode){
        super(new RuntimeException(errorCode.getMessage()));
        this.throwable = super.getCause();
        this.errorMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getStatus();
        this.errorCode = errorCode;
    }

    public ResponseDto<?> getResponse(){
        return new ResponseDto<>(errorMessage);
    }

}
