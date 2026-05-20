# Kata Spring Boot — Cadastro de Produtos

## Objetivo

Implementar uma API REST de cadastro de produtos seguindo o ciclo TDD: os testes já estão escritos e falhando — seu trabalho é fazer todos eles passarem.

## Requisitos

- Java 21
- Maven 3.8+

## Como começar

```bash
# Instalar dependências e verificar que os testes estão vermelhos
mvn test
```

Você deve ver **15 testes falhando**. Seu objetivo é chegar em **17/17 verdes** sem alterar nenhum arquivo de teste.

---

## O que implementar

### 1. `ProdutoServiceImpl`

Implemente os métodos da interface `ProdutoService`:

| Método | O que deve fazer |
|---|---|
| `criar` | Converte `ProdutoRequest` → `Produto`, salva no repositório e retorna `ProdutoResponse` |
| `listar` | Retorna todos os produtos como `List<ProdutoResponse>` |
| `buscarPorId` | Retorna o produto pelo id ou lança `ProdutoNaoEncontradoException` |
| `atualizar` | Busca o produto, atualiza os campos e salva. Lança exceção se não encontrar |
| `deletar` | Remove o produto pelo id. Lança exceção se não encontrar |

### 2. `ProdutoController`

Implemente os endpoints REST. Para cada método, descomente a anotação `@Operation` correspondente:

| Método HTTP | Rota | Status esperado |
|---|---|---|
| `POST` | `/produtos` | `201 Created` |
| `GET` | `/produtos` | `200 OK` |
| `GET` | `/produtos/{id}` | `200 OK` / `404 Not Found` |
| `PUT` | `/produtos/{id}` | `200 OK` / `404 Not Found` |
| `DELETE` | `/produtos/{id}` | `204 No Content` |

---

## Regras

- **Não altere** nenhum arquivo dentro de `src/test/`
- **Não altere** `ProdutoRequest`, `ProdutoResponse`, `Produto`, `ProdutoRepository` ou `ProdutoNaoEncontradoException`
- **Não use IA** o motivo principal é você colocar em prática os seus conhecimentos
- Apenas `ProdutoServiceImpl` e `ProdutoController` precisam ser implementados
- Não sabe por onde começar? leia esse artigo: https://alexsousadev.com/posts/do-zero-ao-endpoint-criando-um-crud-no-spring-boot-e-entendendo-cada-camada


---

## Estrutura do projeto

```
src/
├── main/java/com/kata/produto/
│   ├── controller/   ProdutoController       ← implementar
│   ├── dto/          ProdutoRequest, ProdutoResponse
│   ├── entity/       Produto
│   ├── exception/    ProdutoNaoEncontradoException
│   ├── repository/   ProdutoRepository
│   └── service/      ProdutoService (interface)
│                     ProdutoServiceImpl      ← implementar
└── test/java/com/kata/produto/
    ├── controller/   ProdutoControllerTest   ← não alterar
    └── service/      ProdutoServiceTest      ← não alterar
```

---

## Rodando os testes

```bash
# Todos os testes
mvn test

# Apenas o service
mvn test -Dtest=ProdutoServiceTest

# Apenas o controller
mvn test -Dtest=ProdutoControllerTest

# Um teste específico
mvn test -Dtest=ProdutoServiceTest#deveCriarProdutoERetornarResponseComDadosCorretos
```

## Testando a API manualmente

Após implementar, suba a aplicação e acesse o Swagger:

```bash
mvn spring-boot:run
```

- Swagger UI: http://localhost:8080/swagger-ui/index.html
