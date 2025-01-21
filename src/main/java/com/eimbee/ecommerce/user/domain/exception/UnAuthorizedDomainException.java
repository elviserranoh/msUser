package com.eimbee.ecommerce.user.domain.exception;

public class UnAuthorizedDomainException extends RuntimeException {
    public UnAuthorizedDomainException(String message) {
        super(message);
    }
}
