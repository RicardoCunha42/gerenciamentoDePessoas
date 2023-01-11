package com.attornatus.gerenciamentoDePessoas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attornatus.gerenciamentoDePessoas.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findAllByPessoaMatricula(Long matricula);

    Optional<Endereco> findByPrincipal(Boolean principal);
    
}
