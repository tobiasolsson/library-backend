package org.library.library.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class ApiError {
    private Instant timestamp;
    private int httpStatusCode;
    private String errorCode;
    private String defaultMessage;
}
