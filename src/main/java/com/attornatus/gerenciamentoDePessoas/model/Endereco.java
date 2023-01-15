package com.attornatus.gerenciamentoDePessoas.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enderecos")
public class Endereco {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo logradouro deve ser preenchido")
    private String logradouro;

    @NotBlank(message = "O campo CEP deve ser preenchido")
    @Pattern(regexp = "\\d+", message = "Apenas numerais no campo CEP")
    private String cep;

    @NotBlank(message = "O campo numero deve ser preenchido")
    @Pattern(regexp = "\\d+", message = "Apenas numerais no campo numero")
    private String numero;

    @NotBlank(message = "O campo cidade deve ser preenchido")
    private String cidade;
    private boolean principal;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;
}
