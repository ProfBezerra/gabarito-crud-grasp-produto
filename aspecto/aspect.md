# UNIDADE II – Programação Orientada a Aspectos (POA) com Spring Boot

**Carga Horária:** 12 horas-aula
**Foco:** material conceitual para apresentação em sala

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

Resultado esperado:

- Código de negócio mais limpo
- Menos repetição
- Padronização de comportamento técnico

---

# 5. Conceitos Fundamentais

## Aspect

Representa um interesse transversal. É uma classe que encapsula comportamento transversal.

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
No Spring AOP, tipicamente é a execução de um método de um bean Spring.

Exemplos:

* Execução de métodos
* Retorno de métodos
* Lançamento de exceções

---

## Pointcut

Define quais Join Points serão interceptados. É a expressão que seleciona onde o aspecto vai atuar.

### Exemplo

```java
execution(* br.com.exemplo.service.*.*(..))
```

Significa:

* Qualquer método
* De qualquer classe
* Dentro do pacote service

Outro exemplo usando `within`:

```java
within(br.unifor.produtosapi.service..)
```

---

## Advice

Representa a ação executada quando um Pointcut é atingido.

Tipos principais:

- `@Before`: antes do método
- `@After`: depois do método (com sucesso ou erro)
- `@AfterReturning`: apenas quando termina com sucesso
- `@AfterThrowing`: apenas quando ocorre exceção
- `@Around`: envolve a execução (antes e depois); o mais poderoso

---

# 6. Spring AOP

## O que é Spring AOP?

Spring AOP é o módulo de Programação Orientada a Aspectos fornecido pelo Spring Framework.

Ele permite implementar aspectos de forma simples utilizando anotações, sem precisar de configuração complexa.

---

## Dependência Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aspectj</artifactId>
</dependency>
```

---

## Como o Spring aplica o aspecto

- O Spring cria proxies para beans gerenciados (`@Service`, `@Component`, `@Repository`, `@RestController`).
- Ao chamar o bean pelo container, o proxy executa o advice antes/depois do método alvo.

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

É o tipo de Advice mais poderoso e mais usado em observabilidade.

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

Regra prática:

- Se for transversal e repetitivo, AOP ajuda.
- Se for regra do domínio, mantenha no service/domain.

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

### Self-invocation

Chamada de um método para outro dentro da mesma classe normalmente não passa pelo proxy, então o advice pode não disparar.

```java
@Service
public class PedidoService {
    public void processar() {
        validar(); // NÃO passa pelo proxy — advice não dispara
    }
    public void validar() { ... }
}
```

Para cenários que exigem interceptar construtores ou self-invocations, utiliza-se AspectJ completo.

---

# 13. Exemplo Prático no Projeto da Turma

## O que foi adicionado

1. Dependência AOP no Maven:

- arquivo: `backend/produtos-api/pom.xml`

2. Aspecto de observabilidade:

- arquivo: `backend/produtos-api/src/main/java/br/unifor/produtosapi/aspect/ObservabilidadeAspect.java`
- intercepta métodos de `controller` e `service`
- registra início/fim, tempo em ms e status de sucesso/erro

## Código do aspecto

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

## Como demonstrar em sala

1. Subir backend:

```bash
cd backend/produtos-api
./mvnw spring-boot:run
```

2. Chamar endpoint:

```bash
curl http://localhost:8080/tipos
```

3. Mostrar logs no console:

- `INICIO metodo=...`
- `FIM metodo=... tempoMs=... sucesso=true`

4. Forçar erro (id inexistente em update/delete) para mostrar:

- `sucesso=false`
- tipo da exceção e mensagem

## Objetivo didático da demo

- Provar que o service/controller não precisou receber código de log.
- Mostrar separação de responsabilidades na prática.
- Evidenciar ganho de padronização e rastreabilidade.

---

# 14. Roteiro para Apresentação em Sala (35–45 min)

1. Problema real: código repetido (5 min)
2. Conceitos: Aspect, Join Point, Pointcut, Advice (10 min)
3. Spring AOP e proxies (8 min)
4. Limitações e armadilhas, incluindo self-invocation (7 min)
5. Exemplo no projeto da turma — demo ao vivo (10–15 min)

---

# 15. Exercício Prático

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

# 16. Questões para Fixação

1. O que são interesses transversais? O que os caracteriza?
2. Explique com exemplos a diferença entre espalhamento (scattering) e entrelaçamento (tangling).
3. O que é um Aspect?
4. O que é um Join Point?
5. O que é um Pointcut?
6. O que é um Advice? Quais são os tipos existentes?
7. Qual a diferença entre `@Before` e `@Around`?
8. Por que `@Around` é tão usado em observabilidade?
9. O que é self-invocation e por que pode impedir a interceptação?
10. Quando utilizar Spring AOP? Quando evitar?
11. Quais são as limitações do Spring AOP?

---

# Resumo da Unidade

Nesta unidade foram estudados:

* Motivação para Programação Orientada a Aspectos.
* Interesses transversais.
* Problemas de espalhamento (scattering) e entrelaçamento (tangling).
* Conceitos fundamentais da POA: Aspect, Join Point, Pointcut, Advice.
* Spring AOP e o modelo baseado em proxies.
* Tipos de Advice: Before, After, AfterReturning, AfterThrowing, Around.
* Aspectos baseados em anotações.
* Implementação de logging, auditoria e monitoramento.
* Boas práticas e limitações do Spring AOP.
* Exemplo prático aplicado ao projeto da turma.

A Programação Orientada a Aspectos é uma importante técnica para melhorar a modularização, manutenção e reutilização de código em aplicações corporativas desenvolvidas com Spring Boot.
