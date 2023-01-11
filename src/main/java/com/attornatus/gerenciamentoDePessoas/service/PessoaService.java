package com.attornatus.gerenciamentoDePessoas.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attornatus.gerenciamentoDePessoas.dto.PessoaDto;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;
import com.attornatus.gerenciamentoDePessoas.repository.PessoaRepository;
@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    

    public Pessoa createPessoa(PessoaDto pessoaDto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDto.getNome());
        LocalDate dataEntrega = LocalDate.parse(pessoaDto.getData(), formatter);
        pessoa.setDataNascimento(dataEntrega);

        return this.pessoaRepository.save(pessoa);
    }


    public Pessoa update(Long matricula, PessoaDto pessoaDto) {
        Optional<Pessoa> maybePessoa = this.pessoaRepository.findById(matricula);
        Pessoa pessoa = maybePessoa.get();
        pessoa.setNome(pessoaDto.getNome());
        LocalDate dataEntrega = LocalDate.parse(pessoaDto.getData(), formatter);
        pessoa.setDataNascimento(dataEntrega);
        
        return this.pessoaRepository.save(pessoa);
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
