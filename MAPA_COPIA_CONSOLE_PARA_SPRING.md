# Mapa de Copia - Qual Arquivo Console Copiar + Como Adaptar para Spring

## Introducao

Para cada arquivo do projeto console, este documento mostra:
1. Caminho no console
2. Caminho no backend Spring
3. Mudancas necessarias
4. Se pode copiar 100% ou precisa adaptar

---

## 1. Dominio - Entidade TipoProduto

### Origem (Console)

```
projeto-console/
  src/feira/graspcrud/domain/TipoProduto.java
```

### Destino (Spring)

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/domain/TipoProduto.java
```

### Mudancas Necessarias

1. Adicionar imports JPA:
   ```java
   import jakarta.persistence.*;
   ```

2. Adicionar anotacoes na classe:
   ```java
   @Entity
   @Table(name = "tipo_produto")
   public class TipoProduto {
   ```

3. Adicionar anotacoes nos campos:
   ```java
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false, length = 100)
   private String nome;
   
   @Column
   private String descricao;
   ```

4. Adicionar construtor vazio (exigencia JPA):
   ```java
   public TipoProduto() {}
   ```

5. Manter metodo validar() EXATAMENTE igual.

### Checklist de Copia

- [ ] Copiar arquivo completo
- [ ] Adicionar imports jakarta.persistence
- [ ] Adicionar anotacoes @Entity, @Table
- [ ] Adicionar anotacoes @Id, @GeneratedValue, @Column
- [ ] Adicionar construtor vazio
- [ ] Testar compilacao

### Tempo Estimado

5 minutos

---

## 2. Dominio - Entidade Produto

### Origem (Console)

```
projeto-console/
  src/feira/graspcrud/domain/Produto.java
```

### Destino (Spring)

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/domain/Produto.java
```

### Mudancas Necessarias

1. Adicionar imports JPA:
   ```java
   import jakarta.persistence.*;
   import br.unifor.produtosapi.exception.RegraNegocioException;
   ```

2. Adicionar anotacoes:
   ```java
   @Entity
   @Table(name = "produto")
   public class Produto {
   ```

3. Adaptar campos:
   ```java
   // ANTES (console):
   private long tipoProdutoId;
   
   // DEPOIS (Spring JPA):
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "tipo_id", nullable = false)
   private TipoProduto tipo;
   ```

4. Adicionar anotacoes de coluna:
   ```java
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false, length = 150)
   private String nome;
   
   @Column(nullable = false)
   private double preco;
   
   @Column(nullable = false)
   private int estoque;
   ```

5. Adicionar construtor vazio:
   ```java
   public Produto() {}
   ```

6. Adaptar construtor que usava tipoProdutoId:
   ```java
   // ANTES:
   public Produto(Long id, String nome, double preco, int estoque, long tipoProdutoId)
   
   // DEPOIS:
   public Produto(Long id, String nome, double preco, int estoque, TipoProduto tipo)
   ```

7. **IMPORTANTE:** Metodo validar() MUDA ligeiramente:
   ```java
   // ANTES (console):
   if (tipoProdutoId <= 0) {
       throw new RegraNegocioException("Tipo de produto invalido.");
   }
   
   // DEPOIS (Spring):
   if (tipo == null || tipo.getId() <= 0) {
       throw new RegraNegocioException("Tipo de produto invalido.");
   }
   ```

8. Atualizar getters/setters:
   ```java
   // ANTES:
   public long getTipoProdutoId() {
       return tipoProdutoId;
   }
   
   // DEPOIS:
   public TipoProduto getTipo() {
       return tipo;
   }
   
   public void setTipo(TipoProduto tipo) {
       this.tipo = tipo;
   }
   ```

### Checklist de Copia

- [ ] Copiar arquivo
- [ ] Adicionar imports jakarta.persistence
- [ ] Adicionar anotacoes @Entity, @Table, @Column, @Id, @GeneratedValue
- [ ] Trocar campo tipoProdutoId por tipo (TipoProduto)
- [ ] Adicionar anotacoes @ManyToOne, @JoinColumn
- [ ] Adicionar construtor vazio
- [ ] Adaptar validar() para usar tipo.getId()
- [ ] Adaptar getters/setters
- [ ] Testar compilacao

### Tempo Estimado

15 minutos

---

## 3. Excecao de Negocio

### Origem (Console)

```
projeto-console/
  src/feira/graspcrud/exception/RegraNegocioException.java
```

### Destino (Spring)

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/exception/RegraNegocioException.java
```

### Mudancas Necessarias

**NENHUMA!** Pode copiar 100%.

A classe nao depende de implementacao de persistencia, e funciona direto no Spring.

### Comando de Copia

```powershell
Copy-Item -Path "src\feira\graspcrud\exception\RegraNegocioException.java" `
          -Destination "backend\produtos-api\src\main\java\br\unifor\produtosapi\exception\"
```

Depois, apenas ajustar package name:

```java
// ANTES:
package feira.graspcrud.exception;

// DEPOIS:
package br.unifor.produtosapi.exception;
```

### Checklist de Copia

- [ ] Copiar arquivo
- [ ] Alterar package name
- [ ] Testar compilacao

### Tempo Estimado

2 minutos

---

## 4. Service - TipoProdutoService

### Origem (Console)

```
projeto-console/
  src/feira/graspcrud/service/TipoProdutoService.java
```

### Destino (Spring)

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/service/TipoProdutoService.java
```

### Mudancas Necessarias

1. Adicionar anotacao @Service:
   ```java
   import org.springframework.stereotype.Service;
   
   @Service
   public class TipoProdutoService {
   ```

2. Manter constructor injection (ja usa em console), apenas adicionar @Service.

3. Adaptar chamadas de repository:

   ```java
   // ANTES (console):
   return repository.buscarPorId(id);
   
   // DEPOIS (Spring JPA):
   return repository.findById(id).orElseThrow(...);
   ```

4. Importante: Metodo listar() e criar() podem ficar parecidos:

   ```java
   // ANTES:
   public List<TipoProduto> listarTodos() {
       return repository.listarTodos();
   }
   
   // DEPOIS:
   public List<TipoProduto> listarTodos() {
       return repository.findAll();
   }
   
   // ANTES:
   public TipoProduto criar(String nome) {
       TipoProduto tipo = new TipoProduto(null, nome, "");
       tipo.validar();
       return repository.salvar(tipo);
   }
   
   // DEPOIS:
   public TipoProduto criar(String nome) {
       TipoProduto tipo = new TipoProduto(null, nome, "");
       tipo.validar();
       return repository.save(tipo);
   }
   ```

### Checklist de Copia

- [ ] Copiar arquivo
- [ ] Adicionar anotacao @Service
- [ ] Adaptar package name
- [ ] Trocar buscarPorId() por findById().orElseThrow()
- [ ] Trocar listarTodos() por findAll()
- [ ] Trocar salvar() por save()
- [ ] Testar compilacao

### Tempo Estimado

10 minutos

---

## 5. Service - ProdutoService

### Origem (Console)

```
projeto-console/
  src/feira/graspcrud/service/ProdutoService.java
```

### Destino (Spring)

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/service/ProdutoService.java
```

### Mudancas Necessarias

1. Adicionar @Service:
   ```java
   import org.springframework.stereotype.Service;
   
   @Service
   public class ProdutoService {
   ```

2. Adaptar constructor (ja existe em console, manter):
   ```java
   public ProdutoService(ProdutoRepository produtoRepository,
                         TipoProdutoRepository tipoRepository) {
       this.produtoRepository = produtoRepository;
       this.tipoRepository = tipoRepository;
   }
   ```

3. IMPORTANTE: Metodo criar() muda:

   ```java
   // ANTES (console):
   public Produto criar(ProdutoRequest request) {
       validarTipoExiste(request.getTipoProdutoId());
       Produto produto = new Produto(null, request.getNome(), 
                                     request.getPreco(), request.getEstoque(), 
                                     request.getTipoProdutoId());
       produto.validar();
       return produtoRepository.salvar(produto);
   }
   
   // DEPOIS (Spring JPA):
   public Produto criar(ProdutoRequest request) {
       TipoProduto tipo = tipoRepository.findById(request.getTipoProdutoId())
           .orElseThrow(() -> new RegraNegocioException("Tipo de produto nao encontrado."));
       
       Produto produto = new Produto(null, request.getNome(), 
                                     request.getPreco(), request.getEstoque(), 
                                     tipo);  // <-- OBJETO, nao ID
       produto.validar();  // <-- MESMA VALIDACAO!
       return produtoRepository.save(produto);
   }
   ```

4. Metodo buscarPorId():
   ```java
   // ANTES:
   public Produto buscarPorId(long id) {
       Produto produto = produtoRepository.buscarPorId(id);
       if (produto == null) {
           throw new RegraNegocioException("Produto nao encontrado.");
       }
       return produto;
   }
   
   // DEPOIS:
   public Produto buscarPorId(long id) {
       return produtoRepository.findById(id)
           .orElseThrow(() -> new RegraNegocioException("Produto nao encontrado."));
   }
   ```

5. Metodo remover():
   ```java
   // ANTES:
   public void remover(long id) {
       if (!produtoRepository.remover(id)) {
           throw new RegraNegocioException("Produto nao encontrado.");
       }
   }
   
   // DEPOIS:
   public void remover(long id) {
       if (!produtoRepository.existsById(id)) {
           throw new RegraNegocioException("Produto nao encontrado.");
       }
       produtoRepository.deleteById(id);
   }
   ```

6. Metodo listarTodos():
   ```java
   // ANTES:
   public List<Produto> listarTodos() {
       return produtoRepository.listarTodos();
   }
   
   // DEPOIS:
   public List<Produto> listarTodos() {
       return produtoRepository.findAll();
   }
   ```

### Checklist de Copia

- [ ] Copiar arquivo
- [ ] Adicionar @Service
- [ ] Adaptar package name e imports
- [ ] Adaptar metodo criar() (trocar tipoProdutoId por TipoProduto)
- [ ] Adaptar buscarPorId() para usar findById().orElseThrow()
- [ ] Adaptar remover() para usar existsById() + deleteById()
- [ ] Adaptar listarTodos() para findAll()
- [ ] Testar compilacao

### Tempo Estimado

15 minutos

---

## 6. DTO - Request

### Origem

Pode nao existem em console. Criar novo baseado em ProdutoRequest do console ou criar novo.

### Destino

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/dto/ProdutoRequest.java
  src/main/java/br/unifor/produtosapi/dto/TipoProdutoRequest.java
```

### Conteudo ProdutoRequest

```java
package br.unifor.produtosapi.dto;

import jakarta.validation.constraints.*;

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
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
    
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    
    public Long getTipoProdutoId() { return tipoProdutoId; }
    public void setTipoProdutoId(Long tipoProdutoId) { this.tipoProdutoId = tipoProdutoId; }
}
```

### Checklist

- [ ] Criar arquivo novo
- [ ] Adicionar validacoes Bean Validation (@NotBlank, @Min, etc)
- [ ] Getters e Setters

### Tempo Estimado

10 minutos

---

## 7. DTO - Response

### Destino

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/dto/ProdutoResponse.java
```

### Conteudo

```java
package br.unifor.produtosapi.dto;

public class ProdutoResponse {
    
    private Long id;
    private String nome;
    private Double preco;
    private Integer estoque;
    private String tipoNome;
    
    public ProdutoResponse(Long id, String nome, Double preco, 
                           Integer estoque, String tipoNome) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.tipoNome = tipoNome;
    }
    
    // Getters (imutavel)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Double getPreco() { return preco; }
    public Integer getEstoque() { return estoque; }
    public String getTipoNome() { return tipoNome; }
}
```

### Checklist

- [ ] Criar arquivo novo
- [ ] Constructor que recebe tudo
- [ ] Apenas getters (nao precisa setters)

### Tempo Estimado

5 minutos

---

## 8. Repository - Interface

### Destino

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/repository/ProdutoRepository.java
  src/main/java/br/unifor/produtosapi/repository/TipoProdutoRepository.java
```

### Conteudo ProdutoRepository

Spring Data JPA cria implementacao automaticamente!

```java
package br.unifor.produtosapi.repository;

import br.unifor.produtosapi.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Spring Data JPA fornece automaticamente:
    // findAll(), findById(), save(), deleteById(), existsById()
}
```

### Conteudo TipoProdutoRepository

```java
package br.unifor.produtosapi.repository;

import br.unifor.produtosapi.domain.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long> {
    // Spring Data JPA fornece automaticamente
}
```

### Checklist

- [ ] Criar interfaces
- [ ] Estender JpaRepository<Entidade, ID>
- [ ] Adicionar @Repository

### Tempo Estimado

5 minutos

---

## 9. Controller

### Destino

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/controller/ProdutoController.java
  src/main/java/br/unifor/produtosapi/controller/TipoProdutoController.java
```

### Caracteristica Principal

Controller CHAMA services cpiados do console.

Exemplo ProdutoController chama ProdutoService (do console):

```java
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    private final ProdutoService service;  // <-- SERVICE DO CONSOLE
    
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {
        return ResponseEntity.ok(
            service.listarTodos().stream()
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getPreco(),
                                              p.getEstoque(), p.getTipo().getNome()))
                .collect(Collectors.toList())
        );
    }
    
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        Produto produto = service.criar(request);  // <-- REGRAS DO CONSOLE
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ProdutoResponse(produto.getId(), produto.getNome(), 
                                      produto.getPreco(), produto.getEstoque(),
                                      produto.getTipo().getNome()));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.remover(id);  // <-- VALIDACAO DO CONSOLE
        return ResponseEntity.noContent().build();
    }
}
```

### Checklist

- [ ] Criar arquivo
- [ ] Injetar Service do console
- [ ] Usar @GetMapping, @PostMapping, @DeleteMapping
- [ ] Retornar DTOs, nao entidades
- [ ] Chamar service para logica

### Tempo Estimado

15 minutos

---

## 10. Configuration - CORS

### Destino

```
backend/produtos-api/
  src/main/java/br/unifor/produtosapi/config/CorsConfig.java
```

### Conteudo

```java
package br.unifor.produtosapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200")  // <-- Angular local
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

### Checklist

- [ ] Criar arquivo
- [ ] Implementar WebMvcConfigurer
- [ ] Configurar CORS para localhost:4200

### Tempo Estimado

5 minutos

---

## Resumo de Tempo Total

| Arquivo | Origem | Destino | Tempo |
|---------|--------|---------|-------|
| TipoProduto | Console | Spring | 5 min |
| Produto | Console | Spring | 15 min |
| RegraNegocioException | Console | Spring | 2 min |
| TipoProdutoService | Console | Spring | 10 min |
| ProdutoService | Console | Spring | 15 min |
| DTOs | Novo | Spring | 15 min |
| Repositories | Novo | Spring | 5 min |
| Controllers | Novo | Spring | 15 min |
| CORS Config | Novo | Spring | 5 min |
| **TOTAL** | | | **87 min** |

---

## Ordem Recomendada de Implementacao

1. **Aula 3:** Copiar entidades (Produto, TipoProduto, RegraNegocioException).
2. **Aula 4:** Copiar services e criar DTOs.
3. **Aula 5:** Criar repositories e controllers.
4. **Aula 5:** Adicionar CORS.
5. **Aula 5:** Testar em Postman.

---

## Perguntas de Aluno Esperadas

**P: Por que copiar o servico de console?**
R: Regras de negocio sao as mesmas. Spring apenas muda como persistir e expor.

**P: O que muda no validar()?**
R: Apenas usar `tipo` em vez de `tipoProdutoId`. Regra continua identica.

**P: Por que DTO?**
R: Entidade tem detalhes de banco (relacionamentos, IDs). DTO é simples e clara.

**P: Se erro de compilacao, onde olhar?**
R: Imports errados (jakarta vs javax) ou package name errado.

---

## Conclusao

Copiar de console nao é "preguiça", é **reutilizacao inteligente**.

Aluno aprende:
1. Que regras nao mudam (apenas persistencia).
2. Alguns padroes acompanham projetos (Service, DTO, Exception).
3. Como evoluir aplicacao sem perder qualidade.
