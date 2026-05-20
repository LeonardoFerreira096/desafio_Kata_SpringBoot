package com.kata.produto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.produto.dto.ProdutoRequest;
import com.kata.produto.dto.ProdutoResponse;
import com.kata.produto.exception.ProdutoNaoEncontradoException;
import com.kata.produto.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ProdutoResponse produtoResponse;
    private ProdutoRequest produtoRequest;

    @BeforeEach
    void setUp() {
        produtoResponse = new ProdutoResponse(1L, "Notebook", new BigDecimal("3500.00"), "Notebook para trabalho", 10, "Eletrônicos");
        produtoRequest = new ProdutoRequest("Notebook", new BigDecimal("3500.00"), "Notebook para trabalho", 10, "Eletrônicos");
    }

    @Test
    void deveCriarProdutoERetornar201() throws Exception {
        when(service.criar(any(ProdutoRequest.class))).thenReturn(produtoResponse);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Notebook"))
                .andExpect(jsonPath("$.preco").value(3500.00));
    }

    @Test
    void deveListarProdutosERetornar200() throws Exception {
        when(service.listar()).thenReturn(List.of(produtoResponse));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Notebook"));
    }

    @Test
    void deveBuscarProdutoPorIdERetornar200() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(produtoResponse);

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Notebook"));
    }

    @Test
    void deveRetornar404QuandoProdutoNaoEncontrado() throws Exception {
        when(service.buscarPorId(999L)).thenThrow(new ProdutoNaoEncontradoException(999L));

        mockMvc.perform(get("/produtos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarProdutoERetornar200() throws Exception {
        when(service.atualizar(eq(1L), any(ProdutoRequest.class))).thenReturn(produtoResponse);

        mockMvc.perform(put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveDeletarProdutoERetornar204() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar400QuandoCriarProdutoSemNome() throws Exception {
        ProdutoRequest semNome = new ProdutoRequest(null, new BigDecimal("100.00"), "desc", 1, "Eletrônicos");

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(semNome)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoCriarProdutoComPrecoNegativo() throws Exception {
        ProdutoRequest precoNegativo = new ProdutoRequest("Notebook", new BigDecimal("-100.00"), "desc", 1, "Eletrônicos");

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(precoNegativo)))
                .andExpect(status().isBadRequest());
    }
}
