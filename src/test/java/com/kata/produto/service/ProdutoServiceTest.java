package com.kata.produto.service;

import com.kata.produto.dto.ProdutoRequest;
import com.kata.produto.dto.ProdutoResponse;
import com.kata.produto.entity.Produto;
import com.kata.produto.exception.ProdutoNaoEncontradoException;
import com.kata.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoServiceImpl service;

    private Produto produto;
    private ProdutoRequest request;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Notebook", new BigDecimal("3500.00"), "Notebook para trabalho", 10, "Eletrônicos");
        request = new ProdutoRequest("Notebook", new BigDecimal("3500.00"), "Notebook para trabalho", 10,
                "Eletrônicos");
    }

    @Test
    void deveCriarProdutoERetornarResponseComDadosCorretos() {
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponse resultado = service.criar(request);

        assertThat(resultado.id()).isEqualTo(1L);
        assertThat(resultado.nome()).isEqualTo("Notebook");
        assertThat(resultado.preco()).isEqualByComparingTo(new BigDecimal("3500.00"));
        assertThat(resultado.descricao()).isEqualTo("Notebook para trabalho");
        assertThat(resultado.quantidadeEstoque()).isEqualTo(10);
        assertThat(resultado.categoria()).isEqualTo("Eletrônicos");
        verify(repository).save(any(Produto.class));
    }

    @Test
    void deveListarTodosOsProdutos() {
        Produto outro = new Produto(2L, "Mouse", new BigDecimal("150.00"), "Mouse sem fio", 50, "Periféricos");
        when(repository.findAll()).thenReturn(List.of(produto, outro));

        List<ProdutoResponse> resultado = service.listar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(ProdutoResponse::nome)
                .containsExactly("Notebook", "Mouse");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaProdutos() {
        when(repository.findAll()).thenReturn(List.of());

        List<ProdutoResponse> resultado = service.listar();

        assertThat(resultado).isEmpty();
    }

    @Test
    void deveBuscarProdutoPorIdExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoResponse resultado = service.buscarPorId(1L);

        assertThat(resultado.id()).isEqualTo(1L);
        assertThat(resultado.nome()).isEqualTo("Notebook");
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoComIdInexistente() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(999L))
                .isInstanceOf(ProdutoNaoEncontradoException.class)
                .hasMessageContaining("999");
    }

    @Test
    void deveAtualizarProdutoExistente() {
        ProdutoRequest novosDados = new ProdutoRequest("Notebook Pro", new BigDecimal("5000.00"), "Top de linha", 5,
                "Eletrônicos");
        Produto atualizado = new Produto(1L, "Notebook Pro", new BigDecimal("5000.00"), "Top de linha", 5,
                "Eletrônicos");
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(atualizado);

        ProdutoResponse resultado = service.atualizar(1L, novosDados);

        assertThat(resultado.nome()).isEqualTo("Notebook Pro");
        assertThat(resultado.preco()).isEqualByComparingTo(new BigDecimal("5000.00"));
        verify(repository).save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoComIdInexistente() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(999L, request))
                .isInstanceOf(ProdutoNaoEncontradoException.class)
                .hasMessageContaining("999");
    }

    @Test
    void deveDeletarProdutoExistente() {
        when(repository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> service.deletar(1L)).doesNotThrowAnyException();

        verify(repository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarProdutoComIdInexistente() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> service.deletar(999L))
                .isInstanceOf(ProdutoNaoEncontradoException.class)
                .hasMessageContaining("999");
    }
}
