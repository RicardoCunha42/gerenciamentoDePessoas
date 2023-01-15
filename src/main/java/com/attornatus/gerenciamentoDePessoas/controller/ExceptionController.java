package com.attornatus.gerenciamentoDePessoas.controller;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<String> DateTimeParseExceptionHandler() {
        return new ResponseEntity<String>("A data deve ser informada no padrão dd/MM/yyyy", HttpStatus.BAD_REQUEST);
	}
    
    @ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noSuchElementExceptionHandler() {
        return new ResponseEntity<String>("Não há registros com a matrícula informada", HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> illegalArgumentExceptionHandler() {
        return new ResponseEntity<String>("Mátricula inválida!", HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> httpMessageNotReadableExceptionHandler() {
        return new ResponseEntity<String>("Há irregularidade no formato JSON", HttpStatus.BAD_REQUEST);
	}
}
