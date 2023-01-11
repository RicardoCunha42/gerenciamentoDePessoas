package com.attornatus.gerenciamentoDePessoas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.attornatus.gerenciamentoDePessoas.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository <Pessoa, Long> {

    @Query("select p from Pessoa p left join fetch p.enderecos where p.matricula=:matricula")
    Optional<Pessoa> findByIdJoinEndereco(@Param("matricula") Long matricula);
    
}
