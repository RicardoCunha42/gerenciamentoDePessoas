package com.attornatus.gerenciamentoDePessoas.errorResponseTests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.attornatus.gerenciamentoDePessoas.AbstractTest;
import com.attornatus.gerenciamentoDePessoas.dto.PessoaDto;

public class PessoaControllerErrorTests extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void naoPodeEstarVazio() throws Exception {
        String uri = "/pessoas";

        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setNome("");
        pessoaDto.setData("02/05/1996");
        String inputJson = super.mapToJson(pessoaDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
       
        @SuppressWarnings("unchecked")
        List<String> msgs = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), List.class);

        assertEquals(400, status);
        assertEquals(2, msgs.size());
    }

    @Test
    public void apenasLetrasNoNome() throws Exception {
        String uri = "/pessoas";

        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setNome("C4io");
        pessoaDto.setData("02/05/1996");
        String inputJson = super.mapToJson(pessoaDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
       
        @SuppressWarnings("unchecked")
        List<String> msgs = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), List.class);

        assertEquals(400, status);
        assertEquals("O campo nome deve conter apenas letras", msgs.get(0));
    }

    @Test
    public void padraoErradoDaData() throws Exception {
        String uri = "/pessoas";

        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setNome("Caio");
        pessoaDto.setData("02-05-1996");
        String inputJson = super.mapToJson(pessoaDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
       
        @SuppressWarnings("unchecked")
        List<String> msgs = super.mapFromJson(mvcResult
            .getResponse()
            .getContentAsString(), List.class);

        assertEquals(400, status);
        assertEquals("Data deve ser informada no formato dd/MM/yyyy", msgs.get(0));
    }

    @Test
    public void noSuchElementExceptionHandler() throws Exception {
        String uri = "/pessoas/1";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        String msg = mvcResult
            .getResponse()
            .getContentAsString();

        assertEquals(400, status);
        assertEquals("Não há registros com a matrícula informada", msg);
    }

    @Test
    public void illegalArgumentExceptionHandler() throws Exception {
        String uri = "/pessoas/x";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
        String msg = mvcResult
            .getResponse()
            .getContentAsString();

        assertEquals(400, status);
        assertEquals("Mátricula inválida!", msg);
    }


    @Test
    public void httpMessageNotReadableExceptionHandler() throws Exception {
        String uri = "/pessoas";

        String inputJson = "{'nome': 'Ricardo','data': '30/06/1995'}";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(inputJson)).andReturn();
        
        int status = mvcResult.getResponse().getStatus();
       
        String msg = mvcResult
        .getResponse()
        .getContentAsString();

        assertEquals(400, status);
        assertEquals("Há irregularidade no formato JSON", msg);
    }

}
