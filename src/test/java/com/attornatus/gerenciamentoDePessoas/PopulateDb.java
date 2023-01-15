package com.attornatus.gerenciamentoDePessoas;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.attornatus.gerenciamentoDePessoas.model.Endereco;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;
import com.attornatus.gerenciamentoDePessoas.repository.EnderecoRepository;
import com.attornatus.gerenciamentoDePessoas.repository.PessoaRepository;

@Component
public class PopulateDb {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Pessoa populatePessoaDb() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Geralt");
        pessoa.setDataNascimento(LocalDate.parse("1996-05-16"));
        pessoa.setMatricula(Long.valueOf(1));
        return this.pessoaRepository.save(pessoa);
    }

    public Pessoa populatePessoaEEnderecoDb() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Geralt");
        pessoa.setDataNascimento(LocalDate.parse("1996-05-16"));
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 84");
        endereco.setCep("8888000");
        endereco.setNumero("450");
        endereco.setCidade("Recife");
        endereco.setPrincipal(true);
        endereco.setPessoa(pessoaSalva);
        this.enderecoRepository.save(endereco);

        return pessoaSalva;
    }

    public void dePopulateDb() {
        this.pessoaRepository.deleteAll();
    }
}
