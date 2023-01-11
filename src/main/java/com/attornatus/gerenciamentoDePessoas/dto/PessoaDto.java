package com.attornatus.gerenciamentoDePessoas.dto;

import javax.validation.constraints.NotNull;
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
    @NotNull
    private String nome;
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
	@NotNull
    private String data;
    
}
