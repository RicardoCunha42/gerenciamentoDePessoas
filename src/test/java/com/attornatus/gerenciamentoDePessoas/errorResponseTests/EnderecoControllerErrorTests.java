package com.attornatus.gerenciamentoDePessoas.errorResponseTests;

import static org.junit.Assert.assertEquals;

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

public class EnderecoControllerErrorTests extends AbstractTest {
    @Autowired
    private PopulateDb populateDb;
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void naoPodeEstarVazio() throws Exception {
        Pessoa pessoaDb = this.populateDb.populatePessoaDb();
        String uri = "/enderecos/" + pessoaDb.getMatricula();

        Endereco endereco = new Endereco();
        endereco.setLogradouro("");
        endereco.setCep("8888000");
        endereco.setNumero("450");
        endereco.setCidade("Recife");
        endereco.setPrincipal(false);
        String inputJson = super.mapToJson(endereco);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
       
        @SuppressWarnings("unchecked")
        List<String> msgs = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), List.class);

        assertEquals(400, status);
        assertEquals("O campo logradouro deve ser preenchido", msgs.get(0));
    }

    @Test
    public void apenasNumeros() throws Exception {
        Pessoa pessoaDb = this.populateDb.populatePessoaDb();
        String uri = "/enderecos/" + pessoaDb.getMatricula();

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 30");
        endereco.setCep("8888-000");
        endereco.setNumero("450");
        endereco.setCidade("Recife");
        endereco.setPrincipal(false);
        String inputJson = super.mapToJson(endereco);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
       
        @SuppressWarnings("unchecked")
        List<String> msgs = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), List.class);

        assertEquals(400, status);
        assertEquals("Apenas numerais no campo CEP", msgs.get(0));
    }

    @Test
    public void noSuchElementExceptionHandler() throws Exception {
        String uri = "/enderecos/150";

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 50");
        endereco.setCep("8888000");
        endereco.setNumero("450");
        endereco.setCidade("Recife");
        endereco.setPrincipal(false);
        String inputJson = super.mapToJson(endereco);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        String msg = mvcResult
            .getResponse()
            .getContentAsString();

        assertEquals(400, status);
        assertEquals("Não há registros com a matrícula informada", msg);
    }

    @Test
    public void illegalArgumentExceptionHandler() throws Exception {
        String uri = "/enderecos/x";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        String msg = mvcResult
            .getResponse()
            .getContentAsString();

        assertEquals(400, status);
        assertEquals("Mátricula inválida!", msg);
    }
}
