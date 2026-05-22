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

    private final ProdutoRepository produtoRepository;

    ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public ProdutoResponse criar(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setDescricao(request.descricao());
        produto.setQuantidadeEstoque(request.quantidadeEstoque());
        produto.setCategoria(request.categoria());

        Produto salvo = produtoRepository.save(produto);

        return new ProdutoResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getPreco(),
                salvo.getDescricao(),
                salvo.getQuantidadeEstoque(),
                salvo.getCategoria());
    }

    @Override
    public List<ProdutoResponse> listar() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(produto -> new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria()))
                .toList();

    }

    @Override
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria());
    }

    @Override
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setDescricao(request.descricao());
        produto.setQuantidadeEstoque(request.quantidadeEstoque());
        produto.setCategoria(request.categoria());

        Produto salvo = produtoRepository.save(produto);

        return new ProdutoResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getPreco(),
                salvo.getDescricao(),
                salvo.getQuantidadeEstoque(),
                salvo.getCategoria());
    }

    @Override
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        produtoRepository.deleteById(id);
    }
}
