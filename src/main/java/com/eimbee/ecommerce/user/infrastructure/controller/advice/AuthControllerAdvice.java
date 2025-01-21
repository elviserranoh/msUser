package com.eimbee.ecommerce.user.infrastructure.controller.advice;

import com.eimbee.ecommerce.user.domain.exception.*;
import com.eimbee.ecommerce.user.domain.model.error.ErrorVO;
import com.eimbee.ecommerce.user.infrastructure.controller.AuthController;
import com.eimbee.ecommerce.user.infrastructure.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice(assignableTypes = {AuthController.class})
public class AuthControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> methodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnknownUserException.class})
    public ResponseEntity<?> unknownUserException(final UnknownUserException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UserEmailAlreadyExistsException.class})
    public ResponseEntity<?> userEmailAlreadyExistsException(final UserEmailAlreadyExistsException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AlreadyDisableUserException.class})
    public ResponseEntity<?> alreadyDisableUserException(final AlreadyDisableUserException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AlreadyEnableUserException.class})
    public ResponseEntity<?> alreadyEnableUserException(final AlreadyEnableUserException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<?> invalidTokenException(final InvalidTokenException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({TokenGenerationException.class})
    public ResponseEntity<?> tokenGenerationException(final TokenGenerationException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({TokenRevocationException.class})
    public ResponseEntity<?> tokenRevocationException(final TokenRevocationException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({TokenValidationException.class})
    public ResponseEntity<?> tokenValidationException(final TokenValidationException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UnAuthorizedDomainException.class})
    public ResponseEntity<?> unauthorizedDomainException(final UnAuthorizedDomainException ex) {

        ErrorVO errorVO = new ErrorVO();
        errorVO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorVO, HttpStatus.FORBIDDEN);
    }


}
