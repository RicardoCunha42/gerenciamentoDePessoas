package com.attornatus.gerenciamentoDePessoas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.attornatus.gerenciamentoDePessoas.model.Endereco;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;
import com.attornatus.gerenciamentoDePessoas.repository.EnderecoRepository;
import com.attornatus.gerenciamentoDePessoas.repository.PessoaRepository;
import com.attornatus.gerenciamentoDePessoas.validation.MessageManager;

@Service
public class EnderecoService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private MessageManager messageManager; 

    public ResponseEntity<Object> create(Long matricula, Endereco endereco, BindingResult result) {
        if(result.hasErrors()) {
            List<String> errors = this.messageManager.getErrors(result);
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }

        if(endereco.isPrincipal()) {
            Optional<Pessoa> maybepessoa = this.pessoaRepository.findByIdJoinEndereco(matricula);
            Pessoa pessoa = maybepessoa.get();
            endereco.setPessoa(pessoa);

            pessoa.getEnderecos().forEach(enderecoSalvo -> {
                if(enderecoSalvo.isPrincipal()){
                    enderecoSalvo.setPrincipal(false);
                    this.enderecoRepository.save(enderecoSalvo);
                }
            });

        } else {
            Optional<Pessoa> maybepessoa = this.pessoaRepository.findById(matricula);
            Pessoa pessoa = maybepessoa.get();
            endereco.setPessoa(pessoa);
        }
        
        Endereco enderecoSalvo = this.enderecoRepository.save(endereco);

        return new ResponseEntity<Object>(enderecoSalvo, HttpStatus.CREATED);
    }

    public List<Endereco> getAllByPessoa(Long matricula) {
        List<Endereco> enderecos = this.enderecoRepository.findAllByPessoaMatricula(matricula);
        
        return enderecos;
    }

    public Endereco getPrincipal(Long matricula) {
        Optional<Endereco> maybeEndereco = this.enderecoRepository.findByPrincipal(true);
        Endereco endereco = maybeEndereco.get();
        return endereco;
    }
    
}
