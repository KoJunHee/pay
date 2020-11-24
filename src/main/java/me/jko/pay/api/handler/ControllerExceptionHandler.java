package me.jko.pay.api.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        InvalidParameterException.class
    })
    public ResponseDto badRequest(Exception ex) {
        log.error("", ex);

        return ResponseDto.builder()
            .exception(ex.getClass().getName())
            .message(ex.getMessage())
            .build();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {
        NoHandlerFoundException.class,
        EntityNotFoundException.class
    })
    public ResponseDto notFound(Exception ex) {
        log.error("", ex);

        return ResponseDto.builder()
            .exception(ex.getClass().getName())
            .message(ex.getMessage())
            .build();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {
        Exception.class
    })
    public ResponseDto unknown(Exception ex) {
        log.error("", ex);

        return ResponseDto.builder()
            .exception(ex.getClass().getName())
            .message(ex.getMessage())
            .build();
    }
}
