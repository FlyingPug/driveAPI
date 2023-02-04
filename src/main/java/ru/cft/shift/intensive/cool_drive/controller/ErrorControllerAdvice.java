package ru.cft.shift.intensive.cool_drive.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.cft.shift.intensive.cool_drive.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Обработка исключений API
 */
@RestControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        Map<String, Object> body = body(status, ex);
        body.put("message", message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(PasswordNotConfirmedException.class)
    public ResponseEntity<Map<String, Object>> handlePasswordNotConfirmedException(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> tokenExpiredException(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.GATEWAY_TIMEOUT, ex);
    }

    @ExceptionHandler(AccessForbidden.class)
    public ResponseEntity<Map<String, Object>> accessForbiden(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.FORBIDDEN, ex);
    }

    @ExceptionHandler(DirectoryAlreadyExists.class)
    public ResponseEntity<Map<String, Object>> directoryAlreadyExists(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(DirectoryCantBeCreated.class)
    public ResponseEntity<Map<String, Object>> directoryCantBeCreated(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.BAD_GATEWAY, ex);
    }

    @ExceptionHandler(DirectoryNotFound.class)
    public ResponseEntity<Map<String, Object>> directoryNotFound(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(FileAlreadyExists.class)
    public ResponseEntity<Map<String, Object>> fileAlreadyExists(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(FileNotFound.class)
    public ResponseEntity<Map<String, Object>> fileNotFound(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(UserNotAuthorized.class)
    public ResponseEntity<Map<String, Object>> userNotAuthorized(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.UNAUTHORIZED, ex);
    }

    protected Map<String, Object> body(HttpStatus status, Exception exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", status.value());
        map.put("message", exception.getMessage());
        return map;
    }

    protected ResponseEntity<Map<String, Object>> handleCustomException(HttpStatus status, Exception exception) {
        return ResponseEntity.status(status).body(body(status, exception));
    }
}
