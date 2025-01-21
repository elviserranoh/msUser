package com.eimbee.ecommerce.user.domain.exception;

public class AlreadyDisableUserException extends RuntimeException {
    public AlreadyDisableUserException(String message) {
        super(message);
    }
}
