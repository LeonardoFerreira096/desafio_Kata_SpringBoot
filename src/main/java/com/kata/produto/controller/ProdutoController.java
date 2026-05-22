package com.kata.produto.controller;

import com.kata.produto.dto.ProdutoRequest;
import com.kata.produto.dto.ProdutoResponse;
import com.kata.produto.entity.Produto;
import com.kata.produto.service.ProdutoService;
import com.kata.produto.service.ProdutoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoServiceImpl produtoServiceImpl;

    ProdutoController(ProdutoServiceImpl produtoServiceImpl) {
        this.produtoServiceImpl = produtoServiceImpl;
    }

    //POST 
    @Operation(summary = "Criar produto", responses = {

            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@RequestBody ProdutoRequest request) {
        ProdutoResponse response = produtoServiceImpl.criar(request);
        
        return ResponseEntity.status(201).body(response);

    }

    /*
     * @Operation(summary = "Listar produtos", responses = {
     * 
     * @ApiResponse(responseCode = "200", description = "Lista de produtos")
     * })
     * GET /produtos
     */

    /*
     * @Operation(summary = "Buscar produto por ID", responses = {
     * 
     * @ApiResponse(responseCode = "200", description = "Produto encontrado"),
     * 
     * @ApiResponse(responseCode = "404", description = "Produto não encontrado")
     * })
     * GET /produtos/{id}
     */

    /*
     * @Operation(summary = "Atualizar produto", responses = {
     * 
     * @ApiResponse(responseCode = "200", description = "Produto atualizado"),
     * 
     * @ApiResponse(responseCode = "404", description = "Produto não encontrado")
     * })
     * PUT /produtos/{id}
     */

    /*
     * @Operation(summary = "Deletar produto", responses = {
     * 
     * @ApiResponse(responseCode = "204", description = "Produto deletado"),
     * 
     * @ApiResponse(responseCode = "404", description = "Produto não encontrado")
     * })
     * DELETE /produtos/{id}
     */
}
