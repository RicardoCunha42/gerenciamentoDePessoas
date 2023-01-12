package com.attornatus.gerenciamentoDePessoas.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.attornatus.gerenciamentoDePessoas.controller.PessoaController;
import com.attornatus.gerenciamentoDePessoas.dto.PessoaDto;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;
import com.attornatus.gerenciamentoDePessoas.service.PessoaService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PessoaControllerTests {
    @Autowired
    private PessoaController pessoaController;
    @Autowired
    private PessoaService pessoaService;

    @Order(1)
    @Test
    public void verifyCreatePessoa() throws IOException, InterruptedException {
        ResponseEntity<Pessoa> pessoa = this.pessoaController.createPessoa(new PessoaDto("Slartibartfast"
            , "05/08/1988"));

        assertInstanceOf(ResponseEntity.class, pessoa);
        assertInstanceOf(Pessoa.class, pessoa.getBody());
    }

    @Order(2)
    @Test
    public void verifyPessoaUpdate() throws IOException, InterruptedException {
        Pessoa pessoa1 = this.pessoaService.get(Long.valueOf(1));
        ResponseEntity<Pessoa> pessoa = this.pessoaController.updatePessoa(Long.valueOf(1),
            new PessoaDto("Geralt", "05/08/1988"));

        assertInstanceOf(ResponseEntity.class, pessoa);
        assertEquals(pessoa1.getMatricula(), pessoa.getBody().getMatricula());
        assertNotEquals(pessoa1.getNome(), pessoa.getBody().getNome());
    }

    @Order(3)
    @Test
    public void verifyGetPessoaResponse() throws IOException, InterruptedException {
        ResponseEntity<Pessoa> pessoa = this.pessoaController.getPessoa(Long.valueOf(1));

        assertInstanceOf(ResponseEntity.class, pessoa);
        assertInstanceOf(Pessoa.class, pessoa.getBody());
    }
    
    @Order(4)
    @Test
    public void verifyGetPessoas() throws IOException, InterruptedException {
        ResponseEntity<List<Pessoa>> pessoa = this.pessoaController.getPessoas();

        assertInstanceOf(ResponseEntity.class, pessoa);
        assertInstanceOf(List.class, pessoa.getBody());
    }

    @Order(5)
    @Test
    public void verifyDeletePessoa() throws IOException, InterruptedException {
        this.pessoaService.createPessoa(new PessoaDto("Geralt", "05/08/1988"));
        ResponseEntity<String> pessoa = this.pessoaController.deletePessoa(Long.valueOf(2));
        
        assertInstanceOf(ResponseEntity.class, pessoa);
        assertEquals("Pessoa deletada!", pessoa.getBody());
    }

}
