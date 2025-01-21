package com.eimbee.ecommerce.user.domain.exception;

public class TokenRevocationException extends RuntimeException {
    public TokenRevocationException(String message) {
        super(message);
    }

    public TokenRevocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
