package br.com.instagram.controller;

import br.com.instagram.dto.ResponseEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> responseStatusException(ResponseStatusException ex) {
        return factoryResponseEntityWithResponseEntityException(ex.getReason(), ex.getStatus(), ex);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<?> httpStatusCodeException(HttpStatusCodeException ex) {
        return factoryResponseEntityWithResponseEntityException(ex.getResponseBodyAsString(), ex.getStatusCode(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return factoryResponseEntityWithResponseEntityException(getStringErrors(ex.getBindingResult()), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return factoryResponseEntityWithResponseEntityException(ex.getMessage(), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return factoryResponseEntityWithResponseEntityException(getAllMessageFromException(ex), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        String msg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "dataIntegrityViolationException";
        return factoryResponseEntityWithResponseEntityException(msg, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> missingRequestHeaderException(MissingRequestHeaderException ex) {
        String msg = "Header '" + ex.getHeaderName() + "' is required";
        return factoryResponseEntityWithResponseEntityException(msg, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String msg = "Parameter '" + ex.getParameterName() + "' type '" + ex.getParameterType() + "' is required";
        return factoryResponseEntityWithResponseEntityException(msg, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException ex) {
        String stringErrors = "Query params errors, " + getStringErrors(ex.getBindingResult());
        return factoryResponseEntityWithResponseEntityException(stringErrors, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return factoryResponseEntityWithResponseEntityException(
                String.format(
                        "Failed to convert parameter %s, value %s, to %s",
                        ex.getName(),
                        ex.getValue(),
                        nonNull(ex.getRequiredType()) ? ex.getRequiredType().getSimpleName() : "Unknown"
                ),
                HttpStatus.BAD_REQUEST,
                ex
        );
    }

  /*  @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> jwtException(JwtException ex) {
        if (ex instanceof UnsupportedJwtException) {
            return factoryResponseEntityWithResponseEntityException("Argument does not represent an Claims JWS", HttpStatus.FORBIDDEN, ex);
        }
        if (ex instanceof MalformedJwtException) {
            return factoryResponseEntityWithResponseEntityException("String is not a valid JWS", HttpStatus.FORBIDDEN, ex);
        }
        if (ex instanceof SignatureException) {
            return factoryResponseEntityWithResponseEntityException("WS signature validation fails", HttpStatus.FORBIDDEN, ex);
        }
        if (ex instanceof ExpiredJwtException) {
            return factoryResponseEntityWithResponseEntityException(
                    "The specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked",
                    HttpStatus.FORBIDDEN,
                    ex
            );
        }
        return exception(ex);
    }*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception ex) {
        ex.printStackTrace();
        return factoryResponseEntityWithResponseEntityException(getAllMessageFromException(ex), HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private String getStringErrors(BindingResult bindingResult) {
        return bindingResult
                .getFieldErrors().stream()
                .map(it -> it.getField() + ": " + it.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }

    private ResponseEntity<?> factoryResponseEntityWithResponseEntityException(String message, HttpStatus httpStatus, Exception exception) {
        log.error(message, exception);
        return new ResponseEntity<>(
                ResponseEntityException.builder()
                        .timestamp(System.currentTimeMillis())
                        .message(message)
                        .status(httpStatus.value())
                        .build(),
                httpStatus
        );
    }

    private String getAllMessageFromException(Throwable throwable) {
        List<String> messages = new ArrayList<>(5);
        Throwable rootCause = throwable;
        do {
            messages.add(rootCause.getClass().getSimpleName() + ": " + rootCause.getMessage());
            rootCause = rootCause.getCause();
        } while (nonNull(rootCause));
        return String.join(" && ", messages);
    }
}
