package org.library.library.exception;

public enum ErrorCode {
    PASSWORD_SHORT("Password must be at least 6 characters long"),
    EMAIL_EMPTY("Email is empty"),
    EMAIL_EXISTS("Email already exists"),
    UNKNOWN_ERROR("Something unexpected happened"),
    USER_NOT_FOUND("User not found"),
    BAD_CREDENTIALS("Wrong username or password"),
    AUTHOR_NOT_FOUND("Author not found"),;

    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
