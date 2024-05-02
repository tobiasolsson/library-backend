package org.library.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleDuplicateEmailException(DuplicateEmailException ex) {
        return ApiError.builder()
                       .timestamp(Instant.now())
                       .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                       .errorCode(ErrorCode.EMAIL_EXISTS.name())
                       .defaultMessage(ex.getMessage())
                       .build();
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidEmailException(InvalidEmailException ex) {
        return ApiError.builder()
                       .timestamp(Instant.now())
                       .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                       .errorCode(ErrorCode.EMAIL_EMPTY.name())
                       .defaultMessage(ex.getMessage())
                       .build();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidPasswordException(InvalidPasswordException ex) {
        return ApiError.builder()
                       .timestamp(Instant.now())
                       .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                       .errorCode(ErrorCode.PASSWORD_SHORT.name())
                       .defaultMessage(ex.getMessage())
                       .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError globalExceptionHandler(Exception ex) {
        return ApiError.builder()
                       .timestamp(Instant.now())
                       .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                       .errorCode(ErrorCode.UNKNOWN_ERROR.name())
                       .defaultMessage(ex.getMessage())
                       .build();
    }
}
