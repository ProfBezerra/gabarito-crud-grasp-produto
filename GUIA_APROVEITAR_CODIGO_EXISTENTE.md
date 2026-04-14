# Guia Adaptado - Aproveitar Projeto Console Existente para Migrar para Web

## Visao Geral

O projeto console atual JATEM:

1. Entidades de dominio (Produto, TipoProduto) com regras de negocio
2. Servicos que encapsulam a logica
3. Excecoes padronizadas de negocio
4. Padroes GRASP bem aplicados

**Estrategia aqui:** Copiar e reaproveitar essas classes na estrutura Spring Boot, sem reescrever tudo.

---

## Regras de Negocio Existentes (Important ressaltar para aluno)

### Regras de TipoProduto

```
Nome obrigatorio.
Nome deve ter minimo 3 caracteres.
Description pode ser vazio (opcional).
```

### Regras de Produto

```
Nome obrigatorio com minimo 3 caracteres.
Preco deve ser maior que zero.
Estoque nao pode ser negativo.
Tipo de produto obrigatorio e deve existir.
```

### Excecao de Negocio

```
RegraNegocioException captura violacoes de regra.
Mensagem clara para usuario.
```

---

## Passo a Passo de Migracao (Reutilizando Codigo Existente)

### Fase 1 - Copiar Dominio (Aula 3 Adaptada)

**O que fazer:**

1. No projeto Spring Boot, copiar as classes de dominio do console:
   - Produto.java
   - TipoProduto.java
   - RegraNegocioException.java

2. Adaptar apenas as anotacoes para JPA, sem alterar regras.

**Classe Produto adaptada para JPA:**

Original (console):

```java
public class Produto {
    private Long id;
    private String nome;
    private double preco;
    private int estoque;
    private long tipoProdutoId;
    
    public void validar() {
        if (nome == null || nome.length() < 3) {
            throw new RegraNegocioException("Nome do produto deve ter ao menos 3 caracteres.");
        }
        if (preco <= 0) {
            throw new RegraNegocioException("Preco deve ser maior que zero.");
        }
        if (estoque < 0) {
            throw new RegraNegocioException("Estoque nao pode ser negativo.");
        }
        if (tipoProdutoId <= 0) {
            throw new RegraNegocioException("Tipo de produto invalido.");
        }
    }
}
```

Adaptada para JPA (Spring):

```java
package br.unifor.produtosapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import br.unifor.produtosapi.exception.RegraNegocioException;

@Entity
@Table(name = "produto")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 150)
    private String nome;
    
    @Column(nullable = false)
    private double preco;
    
    @Column(nullable = false)
    private int estoque;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoProduto tipo;
    
    // Construtor vazio para JPA
    public Produto() {}
    
    // Construtor original
    public Produto(Long id, String nome, double preco, int estoque, TipoProduto tipo) {
        this.id = id;
        this.nome = nome == null ? null : nome.trim();
        this.preco = preco;
        this.estoque = estoque;
        this.tipo = tipo;
    }
    
    /**
     * REGRAS DE NEGOCIO DO PROJETO CONSOLE
     * Mantidas exatamente como estavam!
     */
    public void validar() {
        if (nome == null || nome.length() < 3) {
            throw new RegraNegocioException("Nome do produto deve ter ao menos 3 caracteres.");
        }
        if (preco <= 0) {
            throw new RegraNegocioException("Preco deve ser maior que zero.");
        }
        if (estoque < 0) {
            throw new RegraNegocioException("Estoque nao pode ser negativo.");
        }
        if (tipo == null || tipo.getId() <= 0) {
            throw new RegraNegocioException("Tipo de produto invalido.");
        }
    }
    
    // Getters e Setters (mantidos iguais ao original)
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public int getEstoque() {
        return estoque;
    }
    
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    
    public TipoProduto getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }
}
```

**O que o aluno aprende:**

- Regras de negocio NÃO mudam de console para web.
- Apenas adicionar anotacoes @Entity, @Column, @ManyToOne.
- Metodo validar() continua identico.
- RegraNegocioException continua siendo usada.

**Procedimento:**

1. Copiar classe Produto.java do projeto console.
2. Adicionar anotacoes JPA nas propriedades.
3. Adicionar construtor vazio para JPA.
4. Trocar `long tipoProdutoId` por `TipoProduto tipo` (objeto, nao ID).
5. Adaptar metodo validar() para usar `tipo.getId()` em vez de `tipoProdutoId`.

---

### Fase 2 - Copiar Excecao (Aula 3, subsecao)

**O que fazer:**

1. Copiar RegraNegocioException.java do console diretamente.
2. Nao muda nada, ja esta pronta para Spring.

```java
package br.unifor.produtosapi.exception;

/**
 * Excecao para representar violacoes de regra de negocio.
 * REUTILIZADA DO PROJETO CONSOLE COM ZERO MUDANCAS.
 */
public class RegraNegocioException extends RuntimeException {
    
    public RegraNegocioException(String message) {
        super(message);
    }
}
```

---

### Fase 3 - Services Reutilizando Regras (Aula 4 Adaptada)

**O que fazer:**

1. Copiar ProdutoService.java do console.
2. Adaptar imports para Spring Boot.
3. Manter logica de validacao intacta.

Servico original do console:

```java
public Produto criar(ProdutoRequest request) {
    validarTipoExiste(request.getTipoProdutoId());
    Produto produto = new Produto(null, request.getNome(), request.getPreco(), 
                                   request.getEstoque(), request.getTipoProdutoId());
    produto.validar();  // <-- REGRA DE NEGOCIO ENCAPSULADA
    return produtoRepository.salvar(produto);
}
```

Adaptado para Spring:

```java
package br.unifor.produtosapi.service;

import br.unifor.produtosapi.domain.Produto;
import br.unifor.produtosapi.domain.TipoProduto;
import br.unifor.produtosapi.dto.ProdutoRequest;
import br.unifor.produtosapi.exception.RegraNegocioException;
import br.unifor.produtosapi.repository.ProdutoRepository;
import br.unifor.produtosapi.repository.TipoProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LOGICA DE NEGOCIO REUTILIZADA DO PROJETO CONSOLE
 * Service encapsula regras que estavam espalhadas antes.
 */
@Service
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    private final TipoProdutoRepository tipoRepository;
    
    public ProdutoService(ProdutoRepository produtoRepository, 
                          TipoProdutoRepository tipoRepository) {
        this.produtoRepository = produtoRepository;
        this.tipoRepository = tipoRepository;
    }
    
    /**
     * Criar produto com MESMAS REGRAS DO CONSOLE
     */
    public Produto criar(ProdutoRequest request) {
        // Validar tipo existe
        TipoProduto tipo = tipoRepository.findById(request.getTipoProdutoId())
            .orElseThrow(() -> new RegraNegocioException("Tipo de produto nao encontrado."));
        
        // Criar entidade com regra de negocio
        Produto produto = new Produto(null, request.getNome(), request.getPreco(), 
                                       request.getEstoque(), tipo);
        
        // Chamar validacao que vem do console (INTACTA)
        produto.validar();
        
        // Salvar
        return produtoRepository.save(produto);
    }
    
    /**
     * Buscar por ID com mensagem clara
     * MANTIDA IGUAL AO CONSOLE
     */
    public Produto buscarPorId(long id) {
        return produtoRepository.findById(id)
            .orElseThrow(() -> new RegraNegocioException("Produto nao encontrado."));
    }
    
    /**
     * Listar todos
     * MANTIDA IGUAL AO CONSOLE
     */
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    /**
     * Deletar com validacao
     * MANTIDA IGUAL AO CONSOLE
     */
    public void remover(long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RegraNegocioException("Produto nao encontrado.");
        }
        produtoRepository.deleteById(id);
    }
}
```

**O que o aluno aprende:**

- Service do console pode ser reaproveitado em Spring quase identico.
- Regras continuam no mesmo lugar, sem espalhamento.
- Spring apenas troca implementacao de repository (JPA no lugar de JSON).

---

### Fase 4 - DTOs Encapsulando Entrada (Aula 4, subsecao)

**O que fazer:**

1. Criar DTOs seguindo mesma estrutura do console se existir, ou nova.
2. DTOs aplicam validacoes de entrada ANTES de chamar validacao de dominio.

```java
package br.unifor.produtosapi.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de entrada reutilizando conceitos de validacao do console.
 */
public class ProdutoRequest {
    
    @NotBlank(message = "Nome nao pode estar vazio")
    @Size(min = 3, message = "Nome deve ter minimo 3 caracteres")
    private String nome;
    
    @NotNull(message = "Preco nao pode ser nulo")
    @DecimalMin(value = "0.01", message = "Preco deve ser maior que zero")
    private Double preco;
    
    @NotNull(message = "Estoque nao pode ser nulo")
    @Min(value = 0, message = "Estoque nao pode ser negativo")
    private Integer estoque;
    
    @NotNull(message = "Tipo nao pode ser nulo")
    private Long tipoProdutoId;
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Double getPreco() {
        return preco;
    }
    
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    
    public Integer getEstoque() {
        return estoque;
    }
    
    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
    
    public Long getTipoProdutoId() {
        return tipoProdutoId;
    }
    
    public void setTipoProdutoId(Long tipoProdutoId) {
        this.tipoProdutoId = tipoProdutoId;
    }
}
```

**DTO de response:**

```java
package br.unifor.produtosapi.dto;

/**
 * Response retorna apenas dados relevantes para cliente.
 * Esconde detalhes internos de persistencia.
 */
public class ProdutoResponse {
    
    private Long id;
    private String nome;
    private Double preco;
    private Integer estoque;
    private String tipoNome;
    
    public ProdutoResponse(Long id, String nome, Double preco, Integer estoque, String tipoNome) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.tipoNome = tipoNome;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public Double getPreco() {
        return preco;
    }
    
    public Integer getEstoque() {
        return estoque;
    }
    
    public String getTipoNome() {
        return tipoNome;
    }
}
```

---

### Fase 5 - Controllers Expondo Services (Aula 5)

**Controller reutiliza servico do console:**

```java
package br.unifor.produtosapi.controller;

import br.unifor.produtosapi.domain.Produto;
import br.unifor.produtosapi.dto.ProdutoRequest;
import br.unifor.produtosapi.dto.ProdutoResponse;
import br.unifor.produtosapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller expoe SERVICE DO CONSOLE COMO API REST
 * Nao adiciona nova logica, apenas converte HTTP em chamadas de servico.
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    private final ProdutoService service;  // <-- SERVICO DO CONSOLE
    
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {
        List<Produto> produtos = service.listarTodos();
        List<ProdutoResponse> response = produtos.stream()
            .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getPreco(), 
                                          p.getEstoque(), p.getTipo().getNome()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        Produto produto = service.criar(request);  // <-- REGRAS DO CONSOLE
        ProdutoResponse response = new ProdutoResponse(produto.getId(), produto.getNome(), 
                                                       produto.getPreco(), produto.getEstoque(),
                                                       produto.getTipo().getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.remover(id);  // <-- VALIDACAO DO CONSOLE
        return ResponseEntity.noContent().build();
    }
}
```

**O que o aluno aprende:**

- Controller NAO cria regra.
- Controller APENAS expoe servico como HTTP.
- Regras continuam no Service (vindo do console).

---

## Tabela Resumida: De Console Para Web

| Aspecto | Console | Web/Spring | Mudanca |
|---------|---------|-----------|---------|
| Dominio | Produto.java | Produto.java + @Entity | Anotacoes |
| Validacao | produto.validar() | produto.validar() | NENHUMA |
| Excecao | RegraNegocioException | RegraNegocioException | NENHUMA |
| Service | ProdutoService | ProdutoService | Imports |
| Repository | JSON em memoria | JpaRepository | Implementacao |
| Entrada | Scanner console | DTO + HTTP | Serialize |
| Saida | println | JSON Response | Serialize |

---

## Checkpoint por Fase

**Fase 1 (Dominio + Excecao):**
- Projeto compila.
- Anotacoes JPA presentes.

**Fase 2 (Service):**
- Service injeta repositories Spring.
- Regras de validacao funcionam igual ao console.

**Fase 3 (DTOs):**
- DTO Request valida entrada.
- DTO Response encapsula saida.

**Fase 4 (Controller):**
- GET /produtos lista.
- POST /produtos cria com regras do console.
- DELETE /produtos/{id} remove.

**Fase 5 (Frontend Angular):**
- Angular consome /produtos.
- Erros de validacao aparecem na tela.

---

## Discussao Pedagogica

### Por que reutilizar codigo existente?

1. **Regras nao mudam:** negocio continua identico.
2. **Ganha-se tempo:** aluno nao reescreve tudo.
3. **Aprende arquitetura:** ve como separar dominio de persistencia.
4. **Menos erros:** codigo testado ja esta pronto.

### Qual ganho de migrar para Spring?

Antes (console):
```
Scanner -> Main -> Service -> Repository JSON -> println
```

Depois (web):
```
HTTP Request -> Controller -> Service -> Repository JPA -> HTTP Response
```

Resultado:

- Multiplos usuarios simultaneamente (HTTP).
- Persistencia real em banco (JPA).
- API reutilizavel (REST).
- Interface moderna (Angular).

### Qual padrao de projeto aparece?

- **GRASP:** separacao de responsabilidades (dominio, service, repository).
- **DTO:** encapsula dados da rede.
- **Service Layer:** regras centralizadas.
- **Repository:** abstrai acesso a dados.
- **MVC:** controller coordena request.

---

## Exemplo Concreto: Teste de Regra em Ambos

### Console Original

```
Digite nome (minimo 3): XY
ERRO: Nome do produto deve ter ao menos 3 caracteres.

Digite preco: -50
ERRO: Preco deve ser maior que zero.
```

### API Spring (mesmo teste)

```
POST /produtos
{
  "nome": "XY",
  "preco": -50,
  "estoque": 10,
  "tipoProdutoId": 1
}

Resposta 400:
{
  "timestamp": "2026-04-13T...",
  "status": 400,
  "erro": "Violacao de regra de negocio",
  "mensagem": "Nome do produto deve ter ao menos 3 caracteres."
}
```

**Mesmo erro, apenas formato diferente!**

---

## Roteiro Simplificado (com Reutilizacao)

**Aula 1:** Ambiente

**Aula 2:** Spring Boot bootstrap + Copiar classes de dominio do console

**Aula 3:** JPA + H2 + Repositories

**Aula 4:** Copiar Services do console + DTOs

**Aula 5:** Controllers REST testados em Postman

**Aula 6:** Frontend Angular inicial

**Aula 7:** Integrar Angular com API

**Aula 8:** Entrega + apresentacao

---

## Conclusao

Nao reinventar a roda:

1. Dominio ja existe = COPIAR.
2. Servicos ja testados = COPIAR.
3. Excecoes ja padronizadas = COPIAR.
4. Apenas adaptar para Spring Boot = ANOTACOES.

Resultado:

- Aluno aprende ARQUITETURA (como organizar).
- Aluno aprende MIGRACAO (como evoluir).
- Aluno mantem QUALIDADE (reutiliza codigo testado).
- Aluno nao reescreve REGRAS (economia de tempo).
