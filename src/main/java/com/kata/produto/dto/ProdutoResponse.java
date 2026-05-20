package com.kata.produto.dto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        String descricao,
        Integer quantidadeEstoque,
        String categoria
) {}
