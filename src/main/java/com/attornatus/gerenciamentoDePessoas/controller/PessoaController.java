package com.attornatus.gerenciamentoDePessoas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.gerenciamentoDePessoas.dto.PessoaDto;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;
import com.attornatus.gerenciamentoDePessoas.service.PessoaService;

@RestController
@RequestMapping("pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @PostMapping()
    public ResponseEntity<Object> createPessoa (@Valid @RequestBody PessoaDto pessoaDto, BindingResult result) {
        ResponseEntity<Object> response = this.pessoaService.create(pessoaDto, result);

        return response;
    }

    @PutMapping("{matricula}")
    public ResponseEntity<Object> updatePessoa(@PathVariable Long matricula, @Valid @RequestBody PessoaDto pessoaDto, 
        BindingResult result) {
        ResponseEntity<Object> response = this.pessoaService.update(matricula, pessoaDto, result);

        return response;
    }

    @GetMapping("{matricula}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long matricula){
        Pessoa pessoa = this.pessoaService.get(matricula);

        return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Pessoa>> getPessoas(){
        List<Pessoa> pessoas = this.pessoaService.getAll();

        return new ResponseEntity<List<Pessoa>>(pessoas, HttpStatus.OK);
    }

    @DeleteMapping("{matricula}")
    public ResponseEntity<String> deletePessoa(@PathVariable Long matricula){
        this.pessoaService.delete(matricula);

        return new ResponseEntity<String>("Pessoa deletada!", HttpStatus.OK);
    }
}
