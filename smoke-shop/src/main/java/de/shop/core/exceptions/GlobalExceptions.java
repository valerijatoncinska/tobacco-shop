package de.shop.core.exceptions;

import de.shop.core.ResponseError;
import de.shop.core.components.LanguageResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Класс по обработки ошибок.
 * Он будет перехватывать различные exception, в том числе кастомные
 */
@RestControllerAdvice
public class GlobalExceptions {
    private LanguageResolver lang; // класс мультиязычности

    public GlobalExceptions(LanguageResolver lang) {
        this.lang = lang;
    }

    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<?> emailServiceException(EmailServiceException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        System.out.println("503 - Service Unavailable \n " + resp.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
    @ExceptionHandler(OrderStatusNotFoundException.class)
    public ResponseEntity<?> orderStatusNotFoundException(OrderStatusNotFoundException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }


    @ExceptionHandler(UserSearchException.class)
    public ResponseEntity<?> userSearchException(UserSearchException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        if (e.getMessage() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(AddressNotfoundException.class)
    public ResponseEntity<?> addressNotFoundException(AddressNotfoundException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        if (e.getMessage() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<?> cartItemException(CartItemException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        if (e.getMessage() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(CartConflictException.class)
    public ResponseEntity<?> cartConflictException(CartConflictException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        if (e.getMessage() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);

        } else {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<?> accessException(AccessException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        if (e.getMessage() != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);

        } else {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @ExceptionHandler(JwtUtilException.class)
    public ResponseEntity<?> jwtUtilException(JwtUtilException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        System.out.println("401 - Unauthorized \n " + resp.getMessage() + " \n");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<?> refreshTokenException(RefreshTokenException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        System.out.println("401 - Unauthorized \n " + resp.getMessage() + " \n");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> loginException(LoginException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(RegException.class)
    public ResponseEntity<?> regException(RegException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        System.out.println("500 - internal server error \n " + resp.getMessage() + " \n");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(DBException.class)
    public ResponseEntity<?> dbException(DBException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        System.out.println("500 - internal server error \n " + resp.getMessage() + " \n");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(RegConflictException.class)
    public ResponseEntity<?> regConflictException(RegConflictException e) {
        ResponseError resp = new ResponseError(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(LoadUserByUsernameException.class)
    public ResponseEntity<?> loadUserByUsernameException(LoadUserByUsernameException e) {
        ResponseError resp = new ResponseError(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validateException(MethodArgumentNotValidException e) {
        ResponseError resp = new ResponseError(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<?> validateException(ValidateException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }


    @ExceptionHandler(ParsePropertiesException.class)
    public ResponseEntity<?> parsePropertiesException(ParsePropertiesException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        System.out.println("500 - internal server error#\n " + resp.getMessage() + " \n");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFoundException(ProductNotFoundException e) {
        ResponseError resp = new ResponseError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



    @ExceptionHandler(ProductNotSavedException.class)
    public ResponseEntity<?> productNotSavedException(ProductNotSavedException e) {
        ResponseError resp = new ResponseError(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

    @ExceptionHandler(ProductAlreadyNotActiveException.class)
    public ResponseEntity<?> productAlreadyNotActiveException(ProductAlreadyNotActiveException e) {
        ResponseError resp = new ResponseError(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }


}
