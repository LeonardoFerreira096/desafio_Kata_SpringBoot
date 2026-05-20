package com.kata.produto.service;

import com.kata.produto.dto.ProdutoRequest;
import com.kata.produto.dto.ProdutoResponse;
import com.kata.produto.entity.Produto;
import com.kata.produto.exception.ProdutoNaoEncontradoException;
import com.kata.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {


    @Override
    public ProdutoResponse criar(ProdutoRequest request) {
        return null;
    }

    @Override
    public List<ProdutoResponse> listar() {
        return List.of();
    }

    @Override
    public ProdutoResponse buscarPorId(Long id) {
        return null;
    }

    @Override
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
