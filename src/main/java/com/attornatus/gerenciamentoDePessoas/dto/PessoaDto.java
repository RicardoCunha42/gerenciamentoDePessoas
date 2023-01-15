package com.attornatus.gerenciamentoDePessoas.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto {
    @NotBlank(message = "O campo nome deve ser preenchido")
    @Pattern (regexp = "[A-Za-z]+", message = "O campo nome deve conter apenas letras")
    private String nome;

    @NotBlank(message = "O campo data de nascimento deve ser preenchido")
    @Pattern (regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Data deve ser informada no formato dd/MM/yyyy")
    private String data;
}
