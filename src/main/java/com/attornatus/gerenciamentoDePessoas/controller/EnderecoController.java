package com.attornatus.gerenciamentoDePessoas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.gerenciamentoDePessoas.model.Endereco;
import com.attornatus.gerenciamentoDePessoas.service.EnderecoService;

@RestController
@RequestMapping("enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("{matricula}")
    public ResponseEntity<Endereco> createEndereco(@PathVariable Long matricula, @RequestBody Endereco endereco){
       Endereco enderecoSalvo =  this.enderecoService.create(matricula, endereco);

        return new ResponseEntity<Endereco>(enderecoSalvo, HttpStatus.CREATED);
    }

    @GetMapping("{matricula}")
    public ResponseEntity<List<Endereco>> getEnderecos(@PathVariable Long matricula) {
        List<Endereco> enderecos = this.enderecoService.getAllByPessoa(matricula);

        return new ResponseEntity<List<Endereco>>(enderecos, HttpStatus.OK);
    }

    @GetMapping("principal/{matricula}") 
    public ResponseEntity<Endereco> getEnderecoPrincipal(@PathVariable Long matricula) {
        Endereco endereco = this.enderecoService.getPrincipal(matricula);

        return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Mátricula inválida!")
    @ExceptionHandler(IllegalArgumentException.class)
	public void exceptionHandler() {
	}
}
