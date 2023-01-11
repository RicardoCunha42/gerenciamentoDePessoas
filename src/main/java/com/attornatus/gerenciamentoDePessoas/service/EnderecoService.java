package com.attornatus.gerenciamentoDePessoas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attornatus.gerenciamentoDePessoas.model.Endereco;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;
import com.attornatus.gerenciamentoDePessoas.repository.EnderecoRepository;
import com.attornatus.gerenciamentoDePessoas.repository.PessoaRepository;

@Service
public class EnderecoService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco create(Long matricula, Endereco endereco) {

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
        
        return this.enderecoRepository.save(endereco);
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
