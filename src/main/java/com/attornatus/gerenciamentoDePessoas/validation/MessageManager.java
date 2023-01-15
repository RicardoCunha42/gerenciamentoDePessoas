package com.attornatus.gerenciamentoDePessoas.validation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class MessageManager {
    public List<String> getErrors(BindingResult result) {
        List<String> errors = result.getAllErrors().stream().map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());

        return errors;
    }
}
