package com.attornatus.gerenciamentoDePessoas.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private String logradouro;
    private Long cep;
    private Long numero;
    private String cidade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;
}
