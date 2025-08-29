package com.kleim.pets_owners.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ServerErrorDTO> handleValidateException(
            MethodArgumentNotValidException exception
    ) {
        String detailedMessage = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(exp -> exp.getField() + ": " + exp.getDefaultMessage())
                .collect(Collectors.joining(", "));


        ServerErrorDTO errorDTO = new ServerErrorDTO("Ошибка валидации запроса",
                detailedMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<ServerErrorDTO> handleNotFoundException(
            NoSuchElementException e
    ) {
        var errorDTO = new ServerErrorDTO("Такого элемента не существует",
                e.getMessage(),
                LocalDateTime.now()
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ServerErrorDTO> handleServerException(
            Exception exception
            ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServerErrorDTO(
                "Ошибка на стороне сервера",
                exception.getMessage(),
                LocalDateTime.now()
        ));
    }

}
