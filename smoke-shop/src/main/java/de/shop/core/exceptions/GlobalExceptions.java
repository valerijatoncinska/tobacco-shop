package de.shop.core.exceptions;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
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
    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<ResponseDto<?>> emailServiceException(EmailServiceException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "503 - SERVICE UNAVAILABLE", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(resp);
    }

    public GlobalExceptions(LanguageResolver lang) {
        this.lang = lang;
    }

    @ExceptionHandler(JwtUtilException.class)
    public ResponseEntity<ResponseDto<?>> jwtUtilException(JwtUtilException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "401 - UNAUTHORIZED", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }


    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ResponseDto<?>> refreshTokenException(RefreshTokenException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "401 - UNAUTHORIZED", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ResponseDto<?>> loginException(LoginException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "404 - NOT FOUND", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler(RegException.class)
    public ResponseEntity<ResponseDto<?>> regException(RegException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "500 - INTERNAL_SERVER_ERROR", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

    @ExceptionHandler(DBException.class)
    public ResponseEntity<ResponseDto<?>> dbException(DBException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "500 - INTERNAL_SERVER_ERROR", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

    @ExceptionHandler(RegConflictException.class)
    public ResponseEntity<ResponseDto<?>> regConflictException(RegConflictException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "409 - CONFLICT", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
    }

    @ExceptionHandler(LoadUserByUsernameException.class)
    public ResponseEntity<ResponseDto<?>> loadUserByUsernameException(LoadUserByUsernameException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "404 - Not found", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> validateException(MethodArgumentNotValidException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "400 BAD REQUEST", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ResponseDto<?>> validateException(ValidateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, e.getMessage(), "400 Bad request", lang.getCurrentLang()));
    }


    @ExceptionHandler(ParsePropertiesException.class)
    public ResponseEntity<ResponseDto<?>> parsePropertiesException(ParsePropertiesException e) {
        ResponseDto<?> resp = new ResponseDto(false, e.getMessage(), "500 - INTERNAL_SERVER_ERROR", lang.getCurrentLang());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

}
