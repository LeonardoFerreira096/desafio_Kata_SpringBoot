package com.kata.produto.service;

import com.kata.produto.dto.ProdutoRequest;
import com.kata.produto.dto.ProdutoResponse;

import java.util.List;

public interface ProdutoService {

    ProdutoResponse criar(ProdutoRequest request);

    List<ProdutoResponse> listar();

    ProdutoResponse buscarPorId(Long id);

    ProdutoResponse atualizar(Long id, ProdutoRequest request);

    void deletar(Long id);
}
