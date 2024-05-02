package org.library.library.exception;

public enum ErrorCode {
    PASSWORD_SHORT("Password must be at least 6 characters long"),
    EMAIL_EMPTY("Email is empty"),
    EMAIL_EXISTS("Email already exists"),
    UNKNOWN_ERROR("Something unexpected happened"),
    ;

    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
