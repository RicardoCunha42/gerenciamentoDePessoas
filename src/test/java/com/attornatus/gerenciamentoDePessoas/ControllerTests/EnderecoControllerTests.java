package com.attornatus.gerenciamentoDePessoas.controllerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.attornatus.gerenciamentoDePessoas.AbstractTest;
import com.attornatus.gerenciamentoDePessoas.PopulateDb;
import com.attornatus.gerenciamentoDePessoas.model.Endereco;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;

public class EnderecoControllerTests extends AbstractTest {
    @Autowired
    private PopulateDb populateDb;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createEndereco() throws Exception{
        Pessoa pessoaDb = this.populateDb.populatePessoaDb();
        String uri = "/enderecos/" + pessoaDb.getMatricula();

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 84");
        endereco.setCep("8888000");
        endereco.setNumero("450");
        endereco.setCidade("Recife");
        endereco.setPrincipal(false);

        String inputJson = super.mapToJson(endereco);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        Endereco enderecoSalvo = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), Endereco.class);

        assertEquals(201, status);
        assertInstanceOf(Endereco.class, enderecoSalvo);
    }

    @Test
    public void getEnderecos() throws Exception {
        Pessoa pessoaDb = this.populateDb.populatePessoaEEnderecoDb();
        String uri = "/enderecos/" + pessoaDb.getMatricula();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        @SuppressWarnings("unchecked")
        List<Endereco> enderecos = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), List.class);

        assertEquals(200, status);
        assertInstanceOf(List.class, enderecos);
        this.populateDb.dePopulateDb();
    }

    @Test
    public void getEnderecoPrincipal() throws Exception {
        Pessoa pessoaDb = this.populateDb.populatePessoaEEnderecoDb();
        String uri = "/enderecos/principal/" + pessoaDb.getMatricula();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();

        Endereco endereco =  super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), Endereco.class);

        assertEquals(200, status);
        assertInstanceOf(Endereco.class, endereco);
        assertEquals(true, endereco.isPrincipal());
        this.populateDb.dePopulateDb();
    }
    
}
