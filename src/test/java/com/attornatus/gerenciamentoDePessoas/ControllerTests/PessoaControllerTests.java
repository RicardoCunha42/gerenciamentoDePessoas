package com.attornatus.gerenciamentoDePessoas.controllerTests;

import com.attornatus.gerenciamentoDePessoas.AbstractTest;
import com.attornatus.gerenciamentoDePessoas.PopulateDb;
import com.attornatus.gerenciamentoDePessoas.dto.PessoaDto;
import com.attornatus.gerenciamentoDePessoas.model.Pessoa;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class PessoaControllerTests extends AbstractTest {
    @Autowired
    private PopulateDb populateDb;
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }
    
    @Test
    public void createPessoa() throws Exception {
        String uri = "/pessoas";

        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setNome("geralt");
        pessoaDto.setData("02/05/1996");
        String inputJson = super.mapToJson(pessoaDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        Pessoa pessoa = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), Pessoa.class);

        assertEquals(201, status);
        assertInstanceOf(Pessoa.class, pessoa); 
    }

    
    @Test
    public void updatePessoa() throws Exception {
        Pessoa pessoaDb = this.populateDb.populatePessoaDb();
        String uri = "/pessoas/" + pessoaDb.getMatricula();

        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setNome("Ciri");
        pessoaDto.setData("02/05/1996");
        String inputJson = super.mapToJson(pessoaDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        Pessoa pessoa = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), Pessoa.class);

        assertEquals(200, status);
        assertInstanceOf(Pessoa.class, pessoa);
        assertEquals("Ciri", pessoa.getNome()); 
    }

    
    @Test
    public void getPessoa() throws Exception {
        Pessoa pessoaDb = this.populateDb.populatePessoaDb();
        String uri = "/pessoas/" + pessoaDb.getMatricula();
        
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        Pessoa pessoa = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), Pessoa.class);

        assertEquals(200, status);
        assertInstanceOf(Pessoa.class, pessoa);
        assertEquals(pessoaDb.getMatricula(), pessoa.getMatricula());
    }

    
    @Test
    public void getPessoas() throws Exception {
        String uri = "/pessoas";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();

        @SuppressWarnings("unchecked")
        List<Pessoa> pessoas =  super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), List.class);

        assertEquals(200, status);
        assertInstanceOf(List.class, pessoas);
    }

    
    @Test
    public void deletePessoa() throws Exception {
        Pessoa pessoaDb = this.populateDb.populatePessoaDb();
        String uri = "/pessoas/" + pessoaDb.getMatricula();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        String message = mvcResult.getResponse().getContentAsString();

        assertEquals(200, status);
        assertEquals("Pessoa deletada!", message);
    }

}
