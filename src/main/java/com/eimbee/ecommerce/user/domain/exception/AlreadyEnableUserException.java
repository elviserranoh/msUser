package com.eimbee.ecommerce.user.domain.exception;

public class AlreadyEnableUserException extends RuntimeException {
    public AlreadyEnableUserException(String message) {
        super(message);
    }
}
