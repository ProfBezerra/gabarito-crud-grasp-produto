# Unidade: Programação Orientada a Aspectos com Spring

**Carga horária sugerida:** 12 horas-aula
**Foco:** material conceitual para apresentação em sala

# UNIDADE II – Programação Orientada a Aspectos (POA) com Spring Boot

**Carga Horária:** 12 horas-aula

## Objetivos da Unidade

Ao final desta unidade, o aluno deverá ser capaz de:

* Identificar interesses transversais em aplicações orientadas a objetos.
* Compreender os problemas de espalhamento e entrelaçamento de código.
* Utilizar o Spring AOP para implementar aspectos.
* Aplicar aspectos para auditoria, logging, segurança e monitoramento.
* Avaliar quando utilizar Programação Orientada a Aspectos em aplicações Spring Boot.

---

# 1. Introdução à Programação Orientada a Aspectos

## Motivação

A Programação Orientada a Objetos (POO) trouxe importantes avanços para a construção de sistemas por meio dos conceitos de:

* Encapsulamento
* Herança
* Polimorfismo
* Abstração

Entretanto, algumas funcionalidades acabam se espalhando por diversas classes da aplicação, dificultando a manutenção do código.

Exemplos:

* Registro de logs
* Auditoria
* Segurança
* Monitoramento
* Controle transacional
* Tratamento de exceções

Essas funcionalidades são conhecidas como **interesses transversais**.

---

## Exemplo sem POA

```java
@Service
public class ClienteService {

    public Cliente buscar(Long id) {

        System.out.println("Iniciando busca");

        Cliente cliente = repository.findById(id)
                .orElseThrow();

        System.out.println("Busca finalizada");

        return cliente;
    }
}
```

Observe que a lógica de log está misturada com a lógica de negócio.

---

# 2. Interesses Transversais

## Conceito

Interesses transversais são funcionalidades que afetam múltiplas partes do sistema e que normalmente não pertencem diretamente à regra de negócio.

### Exemplos

| Interesse Transversal | Aplicação             |
| --------------------- | ----------------------- |
| Logging               | Registro de operações |
| Segurança            | Controle de acesso      |
| Auditoria             | Rastreabilidade         |
| Monitoramento         | Tempo de resposta       |
| Transações          | Commit e Rollback       |
| Tratamento de Erros   | Captura de exceções   |

---

# 3. Problemas da Programação Orientada a Objetos

## Espalhamento (Scattering)

O mesmo código aparece em diversos locais.

### Exemplo

```java
logger.info("Iniciando operação");
```

Essa instrução pode aparecer em dezenas ou centenas de métodos.

---

## Entrelaçamento (Tangling)

Ocorre quando diferentes responsabilidades são misturadas em um mesmo método.

### Exemplo

```java
public Pedido criarPedido() {

    logger.info("Criando pedido");

    validarPermissao();

    Pedido pedido = processarPedido();

    logger.info("Pedido criado");

    return pedido;
}
```

Nesse caso temos:

* Regra de negócio
* Segurança
* Logging

Tudo misturado em um único método.

---

# 4. O que é Programação Orientada a Aspectos?

A Programação Orientada a Aspectos (POA) é um paradigma que visa separar os interesses transversais da lógica principal do sistema.

## Estrutura Tradicional

```text
PedidoService
 ├─ Regra de negócio
 ├─ Logging
 ├─ Segurança
 └─ Auditoria
```

## Estrutura com POA

```text
PedidoService
 └─ Regra de negócio

LogAspect
SegurancaAspect
AuditoriaAspect
```

---

# 5. Conceitos Fundamentais

## Aspect

Representa um interesse transversal.

### Exemplo

```java
@Aspect
@Component
public class LogAspect {
}
```

---

## Join Point

Representa um ponto durante a execução da aplicação onde um aspecto pode atuar.

Exemplos:

* Execução de métodos
* Retorno de métodos
* Lançamento de exceções

---

## Pointcut

Define quais Join Points serão interceptados.

### Exemplo

```java
execution(* br.com.exemplo.service.*.*(..))
```

Significa:

* Qualquer método
* De qualquer classe
* Dentro do pacote service

---

## Advice

Representa a ação executada quando um Pointcut é atingido.

---

# 6. Spring AOP

## O que é Spring AOP?

Spring AOP é o módulo de Programação Orientada a Aspectos fornecido pelo Spring Framework.

Ele permite implementar aspectos de forma simples utilizando anotações.

---

## Dependência Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

---

# 7. Tipos de Advice

## Before

Executa antes do método.

```java
@Before(
 "execution(* br.com.exemplo.service.*.*(..))")
public void logAntes() {

    System.out.println("Método iniciado");
}
```

---

## After

Executa após o término do método.

```java
@After(
 "execution(* br.com.exemplo.service.*.*(..))")
public void logDepois() {

    System.out.println("Método finalizado");
}
```

---

## AfterReturning

Executa apenas quando não ocorre erro.

```java
@AfterReturning(
 pointcut =
 "execution(* br.com.exemplo.service.*.*(..))",
 returning = "resultado")
public void sucesso(Object resultado) {

    System.out.println(resultado);
}
```

---

## AfterThrowing

Executa quando uma exceção é lançada.

```java
@AfterThrowing(
 pointcut =
 "execution(* br.com.exemplo.service.*.*(..))",
 throwing = "ex")
public void erro(Exception ex) {

    System.out.println(ex.getMessage());
}
```

---

## Around

Executa antes e depois do método.

É o tipo de Advice mais poderoso.

```java
@Around(
 "execution(* br.com.exemplo.service.*.*(..))")
public Object monitorar(
 ProceedingJoinPoint joinPoint)
 throws Throwable {

    long inicio = System.currentTimeMillis();

    Object retorno = joinPoint.proceed();

    long fim = System.currentTimeMillis();

    System.out.println(
       "Tempo: " + (fim - inicio));

    return retorno;
}
```

---

# 8. Exemplo de Logging com Spring AOP

## Serviço

```java
@Service
public class ProdutoService {

    public void cadastrar() {

        System.out.println("Produto cadastrado");
    }
}
```

---

## Aspecto

```java
@Aspect
@Component
public class LogAspect {

    @Before(
      "execution(* br.com.exemplo.service.*.*(..))")
    public void registrarLog() {

        System.out.println(
            "Executando método...");
    }
}
```

---

## Resultado

```text
Executando método...
Produto cadastrado
```

---

# 9. Aspectos Baseados em Anotações

## Criando uma Anotação

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditavel {
}
```

---

## Utilizando a Anotação

```java
@Service
public class ClienteService {

    @Auditavel
    public void excluir(Long id) {

        System.out.println("Cliente excluído");
    }
}
```

---

## Aspecto de Auditoria

```java
@Aspect
@Component
public class AuditoriaAspect {

    @Before("@annotation(Auditavel)")
    public void auditar() {

        System.out.println(
            "Operação auditada");
    }
}
```

---

# 10. Estudo de Caso

## Sistema de Pedidos

### Requisitos

* Registrar logs.
* Auditar operações.
* Medir tempo de execução.

---

## Classe de Negócio

```java
@Service
public class PedidoService {

    public void gerarPedido() {

        System.out.println(
            "Pedido gerado");
    }
}
```

---

## Aspecto de Log

```java
@Aspect
@Component
public class LogAspect {

    @Before(
      "execution(* *..PedidoService.*(..))")
    public void log() {

        System.out.println("Início");
    }
}
```

---

## Aspecto de Monitoramento

```java
@Aspect
@Component
public class MonitoramentoAspect {

    @Around(
      "execution(* *..PedidoService.*(..))")
    public Object medirTempo(
      ProceedingJoinPoint pjp)
      throws Throwable {

        long inicio = System.currentTimeMillis();

        Object retorno = pjp.proceed();

        long fim = System.currentTimeMillis();

        System.out.println(
          "Tempo: " + (fim - inicio));

        return retorno;
    }
}
```

---

# 11. Boas Práticas

## Utilize Aspectos Para

* Logging
* Auditoria
* Segurança
* Monitoramento
* Métricas
* Transações

---

## Evite Aspectos Para

* Regras de negócio
* Cálculos de domínio
* Validações de domínio
* Processamento principal da aplicação

---

# 12. Limitações do Spring AOP

O Spring AOP trabalha apenas com Beans gerenciados pelo Spring.

### Funciona com

```java
@Service
@Repository
@Component
@RestController
```

### Não funciona com

```java
new Cliente();
```

Também não intercepta:

* Métodos privados
* Construtores
* Campos/Atributos

Para esses cenários utiliza-se AspectJ completo.

---

# 13. Exercício Prático

Desenvolva uma API Spring Boot contendo:

## Serviços

* UsuarioService
* ProdutoService
* PedidoService

## Aspectos

* LogAspect
* AuditoriaAspect
* TempoExecucaoAspect

## Requisitos

1. Registrar entrada em todos os métodos.
2. Registrar saída dos métodos.
3. Medir tempo de execução.
4. Auditar métodos anotados com `@Auditavel`.
5. Não incluir código de log dentro dos serviços.

---

# 14. Questões para Fixação

1. O que são interesses transversais?
2. Explique espalhamento e entrelaçamento.
3. O que é um Aspect?
4. O que é um Join Point?
5. O que é um Pointcut?
6. O que é um Advice?
7. Quais são os tipos de Advice existentes?
8. Qual a diferença entre Before e Around?
9. Quando utilizar Spring AOP?
10. Quais são as limitações do Spring AOP?

---

# Resumo da Unidade

Nesta unidade foram estudados:

* Motivação para Programação Orientada a Aspectos.
* Interesses transversais.
* Problemas de espalhamento e entrelaçamento.
* Conceitos fundamentais da POA.
* Spring AOP.
* Tipos de Advice.
* Aspectos baseados em anotações.
* Implementação de logging, auditoria e monitoramento.
* Boas práticas e limitações do Spring AOP.

A Programação Orientada a Aspectos é uma importante técnica para melhorar a modularização, manutenção e reutilização de código em aplicações corporativas desenvolvidas com Spring Boot.

---

## 1. Problema que a AOP resolve

A Programação Orientada a Objetos (POO) organiza bem regras de negócio, mas não resolve de forma elegante funcionalidades que se repetem em vários lugares do sistema.

Exemplos clássicos:

- Logging
- Auditoria
- Monitoramento (tempo, contadores)
- Segurança
- Tratamento padronizado de exceções

Quando essas preocupações entram em vários métodos, surgem dois problemas:

- **Scattering (espalhamento):** o mesmo codigo aparece em varias classes.
- **Tangling (entrelaçamento):** regra de negócio e preocupações técnicas ficam misturadas no mesmo método.

### Explicando de forma direta

- **Scattering (espalhamento):** a mesma preocupação técnica fica distribuída em vários pontos do sistema.
  Exemplo: chamadas de log repetidas em diversos métodos de vários services.
- **Tangling (entrelaçamento):** várias responsabilidades diferentes ficam misturadas dentro do mesmo método.
  Exemplo: no mesmo método há regra de negócio, validação de permissão, logging e tratamento técnico de erro.

Resumo prático:

- **Scattering:** uma preocupação em muitos lugares.
- **Tangling:** muitas preocupações no mesmo lugar.

---

## 2. Ideia central da AOP

A AOP (Aspect-Oriented Programming) separa os **interesses transversais** da lógica principal.

Sem AOP:

- ProdutoService = regra de negócio + log + monitoramento

Com AOP:

- ProdutoService = regra de negócio
- ObservabilidadeAspect = log + tempo + status

Resultado esperado:

- Código de negócio mais limpo
- Menos repetição
- Padronização de comportamento técnico

---

## 3. Conceitos fundamentais

### 3.1 Aspect

Classe que encapsula comportamento transversal.

### 3.2 Join Point

Ponto de execução onde o aspecto pode atuar (no Spring AOP, tipicamente execução de método de bean Spring).

### 3.3 Pointcut

Expressão que seleciona quais join points serão interceptados.

Exemplo de ideia:

- Todos os métodos de classes em `service`
- Todos os métodos de classes em `controller`

### 3.4 Advice

Ação executada no join point selecionado.

Tipos mais usados:

- `@Before`: antes do método
- `@After`: depois do método (com sucesso ou erro)
- `@AfterReturning`: apenas quando termina com sucesso
- `@AfterThrowing`: apenas quando ocorre exceção
- `@Around`: envolve a execução (antes e depois); o mais poderoso

---

## 4. Spring AOP na pratica

### 4.1 O que o Spring oferece

No Spring, você cria aspectos com anotações, sem precisar de configuração complexa.

Dependencia Maven:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aspectj</artifactId>
</dependency>
```

### 4.2 Como o Spring aplica o aspecto

- O Spring cria proxies para beans gerenciados (`@Service`, `@Component`, `@Repository`, `@RestController`).
- Ao chamar o bean pelo container, o proxy executa o advice antes/depois do metodo alvo.

---

## 5. Limites importantes (muito cobrado em prova)

Spring AOP:

- Atua em beans gerenciados pelo container Spring.
- Intercepta execução de métodos (modelo proxy-based).

Não cobre diretamente:

- Objetos criados com `new` fora do container
- Construtores
- Campos
- Métodos `private`

Observação importante:

- **Self-invocation:** chamada de um método para outro dentro da mesma classe normalmente não passa pelo proxy, então o advice pode não disparar.

Quando for necessário interceptar mais cenários (como construtores), usa-se AspectJ completo.

---

## 6. Quando usar e quando evitar

Use AOP para:

- Logging técnico
- Auditoria
- Métricas e tempo de execução
- Políticas transversais de segurança

Evite AOP para:

- Regra de negócio principal
- Cálculo de domínio
- Decisões centrais de caso de uso

Regra pratica:

- Se for transversal e repetitivo, AOP ajuda.
- Se for regra do domínio, mantenha no service/domain.

---

## 7. Roteiro conceitual para apresentação (sugestão 35-45 min)

1. Problema real: código repetido (5 min)
2. Conceitos: Aspect, Join Point, Pointcut, Advice (10 min)
3. Spring AOP e proxies (8 min)
4. Limitações e armadilhas (7 min)
5. Exemplo no projeto da turma (10-15 min)

---

## 8. Exemplo prático no backend deste projeto

### 8.1 O que foi adicionado

1. Dependência AOP no Maven:

- arquivo: `backend/produtos-api/pom.xml`

2. Aspecto de observabilidade:

- arquivo: `backend/produtos-api/src/main/java/br/unifor/produtosapi/aspect/ObservabilidadeAspect.java`
- intercepta métodos de `controller` e `service`
- registra início/fim, tempo em ms e status de sucesso/erro

### 8.2 Código do aspecto (resumo)

```java
@Aspect
@Component
public class ObservabilidadeAspect {

    @Pointcut("within(br.unifor.produtosapi.controller..*) || within(br.unifor.produtosapi.service..*)")
    public void camadasApi() {}

    @Around("camadasApi()")
    public Object registrarTempoEStatus(ProceedingJoinPoint joinPoint) throws Throwable {
        // log de início
        // executa método alvo
        // log de fim com tempo e status
    }
}
```

### 8.3 Como demonstrar em sala

1. Subir backend:

```bash
cd backend/produtos-api
./mvnw spring-boot:run
```

2. Chamar endpoint (exemplo):

```bash
curl http://localhost:8080/tipos
```

3. Mostrar logs no console:

- `INICIO metodo=...`
- `FIM metodo=... tempoMs=... sucesso=true`

4. Forçar erro (id inexistente, por exemplo em update/delete) para mostrar:

- `sucesso=false`
- tipo da exceção e mensagem

### 8.4 Objetivo didático da demo

- Provar que o service/controller nao precisou receber codigo de log.
- Mostrar separação de responsabilidades na prática.
- Evidenciar ganho de padronização e rastreabilidade.

---

## 9. Perguntas de fixação

1. O que caracteriza um interesse transversal?
2. Diferencie scattering e tangling.
3. Qual a diferença entre pointcut e advice?
4. Por que `@Around` e tao usado em observabilidade?
5. Por que self-invocation pode impedir a interceptação?
6. Quando você não deveria usar AOP?

---

## 10. Fechamento

A AOP com Spring não substitui modelagem de domínio: ela complementa a arquitetura separando preocupações transversais. Em projetos reais, essa separação reduz duplicação, melhora manutenção e facilita evolução do sistema.
