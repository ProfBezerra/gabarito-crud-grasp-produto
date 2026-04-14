# Indice - Materiais de Ensino Para Migracao De Console Para Web

## Para o Professor

### 1. Visao Geral (Comece por aqui)

**Arquivo:** [MATERIAL_TUTORIAL_MIGRACAO_WEB.md](MATERIAL_TUTORIAL_MIGRACAO_WEB.md)

- O que e o trabalho
- Objetivos de aprendizagem
- Cronograma de 6 encontros
- Rubrica de avaliacao
- Modelo de entrega do aluno

**Melhor para:** Entender estrutura geral e planejar semestres.

---

### 2. Passo a Passo Letivo (Detalhado com Tecnologias)

**Arquivo:** [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md)

- 8 aulas de 90 minutos (leves)
- Instalacao de ferramentas (com links)
- Conceitos de cada tecnologia (ORM, REST, Angular, etc)
- Codigo pronto para digitar/copiar
- Comandos exatos para PowerShell
- Checkpoints de validacao

**Melhor para:** Preparar aulas e material distribuivel aos alunos.

---

### 3. Laboratorio com Blocos de 15 Minutos (Ritmo Intenso)

**Arquivo:** [ROTEIRO_LABORATORIO_6_AULAS.md](ROTEIRO_LABORATORIO_6_AULAS.md)

- 6 aulas de 2 horas
- Blocos de 15 minutos com checkpoint
- Comandos prontos para laboratorio
- Rubrica simples
- Plano de contingencia

**Melhor para:** Turmas de intensivo ou presencial semanal.

---

## Para o Aluno

### 4. Como Aproveitar Codigo Existente Do Console (Reutilizacao Inteligente)

**Arquivo:** [GUIA_APROVEITAR_CODIGO_EXISTENTE.md](GUIA_APROVEITAR_CODIGO_EXISTENTE.md)

- Por que reutilizar (nao reinventar roda)
- Regras de negocio que vem do console
- Exemplos antes x depois
- Tabela de comparacao console vs web
- Discussao pedagogica

**Melhor para:** Leitura antes de comcar aula 3, para motivar aluno.

---

### 5. Checklist Literal - Qual Arquivo Copiar

**Arquivo:** [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md)

- Arquivo por arquivo (origem e destino)
- Mudancas exatas necessarias
- Codigo pronto para copiar
- Tempo por arquivo
- Ordem recomendada

**Melhor para:** Aluno durante implementacao (consultable).

---

## Fluxo Recomendado de Prep do Professor

### Semana 1 (Preparacao)

1. Ler [MATERIAL_TUTORIAL_MIGRACAO_WEB.md](MATERIAL_TUTORIAL_MIGRACAO_WEB.md) - entender escopo.
2. Escolher entre:
   - [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) (recomendado para iniciantes)
   - [ROTEIRO_LABORATORIO_6_AULAS.md](ROTEIRO_LABORATORIO_6_AULAS.md) (alternativa para intensivo)
3. Testar pessoalmente um ciclo completo (5 horas).

### Semana 2 (Antes da Aula 1)

1. Preparar apresentacao de ambiente.
2. Testar links de download (Java, Maven, Node, Angular).
3. Preparar lista de troubleshooting comum.

### Antes da Aula 3

1. Reler [GUIA_APROVEITAR_CODIGO_EXISTENTE.md](GUIA_APROVEITAR_CODIGO_EXISTENTE.md).
2. Preparar apresentacao sobre "por que copiar codigo console".
3. Ter [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) impresso ou projetado.

---

## Fluxo Recomendado de Estudo do Aluno

### Aula 1 (Ambiente)

- Seguir [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 1

### Aula 2 (Conceitos de Arquitetura)

- Seguir [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 2

### Aula 3 (Dominio - COPIAR DO CONSOLE)

- Ler [GUIA_APROVEITAR_CODIGO_EXISTENTE.md](GUIA_APROVEITAR_CODIGO_EXISTENTE.md) intro
- Seguir [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 3
- Usar [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) como consulta

### Aulas 4-5 (Services, DTOs, APIs)

- Seguir [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 4-5
- Consultar [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) nas secoes de Service e DTO

### Aulas 6-7 (Angular Basico)

- Seguir [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 6-7

### Aula 8 (Entrega)

- Seguir [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 8

---

## Estrutura Rapida dos Materiais

```
MATERIAL_TUTORIAL_MIGRACAO_WEB.md
├── Presentacao
├── Objetivos
├── Cronograma 6 encontros
├── Roteiro tecnico
├── Rubrica 100 pontos
└── Extensoes opcionais

ROTEIRO_8_AULAS_DETALHADO.md
├── Aula 1 - Ambiente (90 min)
├── Aula 2 - ORM/JPA (90 min)
├── Aula 3 - Banco de Dados (90 min)
├── Aula 4 - Services e DTOs (90 min)
├── Aula 5 - REST APIs (90 min)
├── Aula 6 - Frontend Angular (90 min)
├── Aula 7 - CRUD Completo (90 min)
├── Aula 8 - Integracao e Entrega (90 min)
└── Rubrica

ROTEIRO_LABORATORIO_6_AULAS.md
├── Aula 1 - Ambiente + Spring Boot (120 min)
├── Aula 2 - Dominio + JPA (120 min)
├── Aula 3 - CRUD REST (120 min)
├── Aula 4 - Tratamento de Erros (120 min)
├── Aula 5 - Angular Basico (120 min)
├── Aula 6 - Integracao e Entrega (120 min)
└── Rubrica

GUIA_APROVEITAR_CODIGO_EXISTENTE.md
├── Visao geral
├── Regras de negocio existentes
├── Passo a passo de migracao
├── Tabela resume console vs web
├── Checkpoint por fase
├── Discussao pedagogica
└── Conclusao

MAPA_COPIA_CONSOLE_PARA_SPRING.md
├── TipoProduto (5 min)
├── Produto (15 min)
├── RegraNegocioException (2 min)
├── TipoProdutoService (10 min)
├── ProdutoService (15 min)
├── DTOs (15 min)
├── Repositories (5 min)
├── Controllers (15 min)
├── CORS Config (5 min)
├── Total: 87 min
└── Perguntas de aluno esperadas
```

---

## Dicas Praticas

### Para Aula Presencial

1. Projetar [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) na tela.
2. Ter [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) impresso para consulta rapida.
3. Anotar duvidas comuns de aluno em [GUIA_APROVEITAR_CODIGO_EXISTENTE.md](GUIA_APROVEITAR_CODIGO_EXISTENTE.md) "Perguntas esperadas".

### Para EAD ou Autoestudo

1. Distribuir [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) por email.
2. Deixar [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) em repositorio Git.
3. Criar forum/chat para duvidas com base em "Perguntas esperadas".

### Para Turma com Tempo Curto

Usar [ROTEIRO_LABORATORIO_6_AULAS.md](ROTEIRO_LABORATORIO_6_AULAS.md) (6 aulas x 2h = 12h).

### Para Turma com Tempo Longo

Usar [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) (8 aulas x 1.5h = 12h + discussao).

---

## Links Importantes Citados

- Spring Initializr: https://start.spring.io
- Java 17/21: https://www.oracle.com/java/technologies/downloads/
- Maven: https://maven.apache.org/download.cgi
- Node.js: https://nodejs.org
- Angular CLI: npm install -g @angular/cli

---

## Estrutura de Pastas Esperada Apos Trabalho

```
projeto/
├── README.md (original)
├── MATERIAL_TUTORIAL_MIGRACAO_WEB.md (NOVO)
├── ROTEIRO_8_AULAS_DETALHADO.md (NOVO)
├── ROTEIRO_LABORATORIO_6_AULAS.md (NOVO)
├── GUIA_APROVEITAR_CODIGO_EXISTENTE.md (NOVO)
├── MAPA_COPIA_CONSOLE_PARA_SPRING.md (NOVO)
├── INDICE_MATERIAIS.md (ESTE ARQUIVO)
├── src/ (original - console)
├── backend/ (NOVO - Spring Boot)
│   └── produtos-api/
│       ├── src/main/java/br/unifor/produtosapi/
│       │   ├── domain/ (copiado + adaptado do console)
│       │   ├── dto/ (novo)
│       │   ├── repository/ (novo)
│       │   ├── service/ (copiado + adaptado do console)
│       │   ├── controller/ (novo)
│       │   ├── exception/ (copiado do console)
│       │   └── config/ (novo)
│       └── ...
└── frontend/ (NOVO - Angular)
    └── produtos-web/
        ├── src/app/
        │   ├── components/ (novo)
        │   ├── services/ (novo)
        │   └── ...
        └── ...
```

---

## Troubleshooting Rapido por Arquivo

### Problema na Aula 1 (Ambiente)

→ Rever links de download em [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 1, secao "Instalacao".

### Problema na Aula 3 (Copiar Console)

→ Consultar [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) arquivo por arquivo.

### Erro "Entity Manager not found"

→ Verificar [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) secao Dominio - anotacoes @Entity.

### API nao responde

→ Verificar CORS em [MAPA_COPIA_CONSOLE_PARA_SPRING.md](MAPA_COPIA_CONSOLE_PARA_SPRING.md) secao 10.

### Angular nao conecta API

→ Verificar URL de backend em [ROTEIRO_8_AULAS_DETALHADO.md](ROTEIRO_8_AULAS_DETALHADO.md) Aula 6 secao "Criar servico HTTP".

---

## Contato/Feedback

Se aluno ou professor tiverem duvidas:

1. Consultar secao "Perguntas esperadas" em [GUIA_APROVEITAR_CODIGO_EXISTENTE.md](GUIA_APROVEITAR_CODIGO_EXISTENTE.md).
2. Consultar secao "Plano de contingencia" em [ROTEIRO_LABORATORIO_6_AULAS.md](ROTEIRO_LABORATORIO_6_AULAS.md).
3. Criar issue no repositorio do projeto.

---

## Resumo Final

- **MATERIAL_TUTORIAL_MIGRACAO_WEB.md**: Entender estrutura do trabalho.
- **ROTEIRO_8_AULAS_DETALHADO.md**: Seguir passo a passo em sala/EAD.
- **ROTEIRO_LABORATORIO_6_AULAS.md**: Alternativa 6 aulas intensivas.
- **GUIA_APROVEITAR_CODIGO_EXISTENTE.md**: Motivacao para reutilizar codigo.
- **MAPA_COPIA_CONSOLE_PARA_SPRING.md**: Checklist pratico durante implementacao.

Todos os materiais sao complementares. Recomenda-se ter os 5 disponives para referencia.

Bom trabalho!
