package com.attornatus.gerenciamentoDePessoas.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.attornatus.gerenciamentoDePessoas.controller.EnderecoController;
import com.attornatus.gerenciamentoDePessoas.dto.PessoaDto;
import com.attornatus.gerenciamentoDePessoas.model.Endereco;
import com.attornatus.gerenciamentoDePessoas.service.EnderecoService;
import com.attornatus.gerenciamentoDePessoas.service.PessoaService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class EnderecoControllerTests {
    @Autowired
    private EnderecoController enderecoController;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private PessoaService pessoaService;

    @Order(1)
    @Test
    public void verifyCreateEndereco(){
        this.pessoaService.createPessoa(new PessoaDto("Slartibartfast", 
            "05/08/1988"));

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 84");
        endereco.setCep("8888-000");
        endereco.setNumero(Long.valueOf(450));
        endereco.setCidade("Recife");
        endereco.setPrincipal(true);

        ResponseEntity<Endereco> resp = this.enderecoController
            .createEndereco(Long.valueOf(1), endereco);

        assertInstanceOf(ResponseEntity.class, resp);
        assertInstanceOf(Endereco.class, resp.getBody());

    }

    @Order(2)
    @Test
    public void verifyGetEnderecos(){
        ResponseEntity<List<Endereco>> resp = this.enderecoController
            .getEnderecos(Long.valueOf(1));

        assertInstanceOf(ResponseEntity.class, resp);
        assertInstanceOf(List.class, resp.getBody());
    }

    @Order(3)
    @Test
    public void verifyGetEnderecoPrincipal(){
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 20");
        endereco.setCep("8888-000");
        endereco.setNumero(Long.valueOf(60));
        endereco.setCidade("Recife");
        endereco.setPrincipal(false);

        ResponseEntity<Endereco> resp = this.enderecoController
            .getEnderecoPrincipal(Long.valueOf(1));

        assertInstanceOf(ResponseEntity.class, resp);
        assertInstanceOf(Endereco.class, resp.getBody());
        assertEquals(true, resp.getBody().isPrincipal());
    }

    @Order(4)
    @Test
    public void verifyPrincipalSubstitution(){
        Endereco principal1 = this.enderecoService.getPrincipal(Long.valueOf(1));

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 50");
        endereco.setCep("84588-000");
        endereco.setNumero(Long.valueOf(63));
        endereco.setCidade("Recife");
        endereco.setPrincipal(true);
        
        this.enderecoService.create(Long.valueOf(1), endereco);
        Endereco principal2 = this.enderecoService.getPrincipal(Long.valueOf(1));
    
        assertNotEquals(principal1.getId(), principal2.getId());
    }
    
}
