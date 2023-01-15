package com.attornatus.gerenciamentoDePessoas.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.attornatus.gerenciamentoDePessoas.dto.PessoaDto;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;
import com.attornatus.gerenciamentoDePessoas.repository.PessoaRepository;
import com.attornatus.gerenciamentoDePessoas.validation.MessageManager;
@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private MessageManager messageManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    

    public ResponseEntity<Object> create(PessoaDto pessoaDto, BindingResult result) {
        if(result.hasErrors()) {
            List<String> errors = this.messageManager.getErrors(result);
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDto.getNome());
        LocalDate dataEntrega = LocalDate.parse(pessoaDto.getData(), formatter);
        pessoa.setDataNascimento(dataEntrega);

        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);

        return new ResponseEntity<Object>(pessoaSalva, HttpStatus.CREATED);
    }


    public ResponseEntity<Object> update(Long matricula, PessoaDto pessoaDto, BindingResult result) {
        if(result.hasErrors()) {
            List<String> errors = this.messageManager.getErrors(result);
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        Optional<Pessoa> maybePessoa = this.pessoaRepository.findById(matricula);
        Pessoa pessoa = maybePessoa.get();
        pessoa.setNome(pessoaDto.getNome());
        LocalDate dataEntrega = LocalDate.parse(pessoaDto.getData(), formatter);
        pessoa.setDataNascimento(dataEntrega);

        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);
        
        return new ResponseEntity<Object>(pessoaSalva, HttpStatus.OK);
    }


    public Pessoa get(Long matricula) {
        Optional<Pessoa> maybePessoa = this.pessoaRepository.findById(matricula);
        Pessoa pessoa = maybePessoa.get();
        return pessoa;
    }


    public List<Pessoa> getAll() {
        return this.pessoaRepository.findAll();
    }


    public void delete(Long matricula) {
        this.pessoaRepository.deleteById(matricula);
    }


}
