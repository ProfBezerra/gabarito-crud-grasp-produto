# Roteiro de Laboratorio - Migracao para Spring Boot + Angular (Iniciantes)

## Como usar este roteiro

- Formato: 6 aulas de 2 horas.
- Ritmo: blocos de 15 minutos.
- Perfil: alunos sem experiencia com Spring ou Angular.
- Estrategia: cada bloco termina com um checkpoint rapido.

## Estrutura de pastas do trabalho

Dentro da pasta do repositorio atual, usar:

- backend
- frontend

Sugestao de organizacao:

- projeto atual (console): apenas referencia de regra de negocio
- backend: nova API Spring Boot
- frontend: nova app Angular

---

## Aula 1 (120 min) - Ambiente + Primeiro Spring Boot

### 00-15 min - Verificacao de ambiente

No PowerShell, executar:

java -version
mvn -version
node -v
npm -v
ng version

Checkpoint:

- Todos os comandos retornam versao.

### 15-30 min - Criar pastas de trabalho

No PowerShell, na raiz do repositorio:

New-Item -ItemType Directory -Force backend
New-Item -ItemType Directory -Force frontend
Get-ChildItem

Checkpoint:

- Pastas backend e frontend existem.

### 30-45 min - Criar projeto Spring Boot no Initializr

Passos:

1. Abrir https://start.spring.io
2. Escolher Maven, Java 17.
3. Group: br.unifor
4. Artifact: produtos-api
5. Dependencias: Spring Web, Spring Data JPA, Validation, H2 Database.
6. Gerar e baixar zip.
7. Extrair dentro de backend.

Checkpoint:

- Existe a pasta backend/produtos-api com pom.xml.

### 45-60 min - Rodar backend pela primeira vez

No PowerShell:

cd backend/produtos-api
mvn spring-boot:run

Checkpoint:

- Aplicacao sobe na porta 8080 sem erro.

### 60-75 min - Criar estrutura de pacotes

Criar pacotes em src/main/java/...:

- domain
- dto
- repository
- service
- controller
- exception

Checkpoint:

- Pacotes criados no projeto.

### 75-90 min - Endpoint de teste

Criar controller simples com rota:

GET /health -> retorna texto ou JSON de status.

Checkpoint:

- Acessar http://localhost:8080/health e receber resposta.

### 90-105 min - Commit da aula

No PowerShell:

git add .
git commit -m "aula1: bootstrap spring boot e endpoint health"

Checkpoint:

- Commit criado.

### 105-120 min - Fechamento

Revisao guiada:

- O que e API REST?
- O que e Controller?
- O que muda de console para web?

---

## Aula 2 (120 min) - Dominio + JPA + Repository

### 00-15 min - Retomada e execucao

No PowerShell:

cd backend/produtos-api
mvn spring-boot:run

Checkpoint:

- Projeto inicia sem ajustes extras.

### 15-30 min - Criar entidades

Criar classes:

- TipoProduto (id, nome)
- Produto (id, nome, preco, tipoProduto)

Adicionar anotacoes JPA:

- Entity
- Id
- GeneratedValue
- ManyToOne (em Produto)

Checkpoint:

- Projeto compila com entidades.

### 30-45 min - Criar repositories

Criar interfaces:

- TipoProdutoRepository extends JpaRepository
- ProdutoRepository extends JpaRepository

Checkpoint:

- Contexto Spring sobe sem erro de bean.

### 45-60 min - Configurar H2

No arquivo application.properties:

- spring.datasource.url=jdbc:h2:mem:produtosdb
- spring.datasource.driverClassName=org.h2.Driver
- spring.jpa.hibernate.ddl-auto=update
- spring.h2.console.enabled=true

Checkpoint:

- Console H2 abre em /h2-console.

### 60-75 min - Inserir dados iniciais (opcional)

Criar data.sql com inserts basicos de tipo_produto.

Checkpoint:

- Tipos iniciais aparecem no banco.

### 75-90 min - Teste de persistencia

Criar endpoint temporario para listar tipos.

Checkpoint:

- GET retorna tipos do banco.

### 90-105 min - Commit da aula

No PowerShell:

git add .
git commit -m "aula2: entidades jpa e repositories"

Checkpoint:

- Commit criado.

### 105-120 min - Fechamento

Revisao guiada:

- O que e Entity?
- O que e Repository?
- Qual ganho de trocar JSON por JPA?

---

## Aula 3 (120 min) - Service + DTO + CRUD REST

### 00-15 min - Retomada

No PowerShell:

cd backend/produtos-api
mvn spring-boot:run

### 15-30 min - Criar camada Service

Criar:

- TipoProdutoService
- ProdutoService

Regras minimas:

- nome obrigatorio
- preco > 0
- tipo existente para cadastrar produto

Checkpoint:

- Regras no service, nao no controller.

### 30-45 min - Criar DTOs

Criar DTOs de request/response para Produto e TipoProduto.

Checkpoint:

- Controller nao expoe entidade diretamente.

### 45-60 min - Criar endpoints de TipoProduto

- GET /tipos
- POST /tipos

Checkpoint:

- Cadastro e listagem de tipos funcionando.

### 60-75 min - Criar endpoints de Produto

- GET /produtos
- POST /produtos
- DELETE /produtos/{id}

Checkpoint:

- CRUD basico de produto funcionando.

### 75-90 min - Testes com Insomnia/Postman

Cenarios:

- cadastrar produto valido
- cadastrar com preco invalido
- excluir id existente

Checkpoint:

- Fluxo principal validado.

### 90-105 min - Commit da aula

No PowerShell:

git add .
git commit -m "aula3: service dto e crud rest"

### 105-120 min - Fechamento

Revisao guiada:

- Por que usar DTO?
- Onde a regra deve ficar?

---

## Aula 4 (120 min) - Tratamento de Erros + CORS

### 00-15 min - Retomada

No PowerShell:

cd backend/produtos-api
mvn spring-boot:run

### 15-30 min - Excecao de negocio

Criar classe RegraNegocioException.

Checkpoint:

- Services lancam excecao em regra invalida.

### 30-45 min - Handler global

Criar ControllerAdvice para tratar:

- MethodArgumentNotValidException
- RegraNegocioException
- Exception generica

Checkpoint:

- API responde erro padrao em JSON.

### 45-60 min - Definir formato padrao de erro

Campos sugeridos:

- timestamp
- status
- erro
- mensagem
- path

Checkpoint:

- Todos os erros seguem o mesmo formato.

### 60-75 min - Configurar CORS

Permitir origem do Angular local (padrao 4200).

Checkpoint:

- Backend pronto para receber chamadas do frontend.

### 75-90 min - Revalidar cenarios

Testar novamente cenarios de sucesso e erro.

Checkpoint:

- Mensagens de erro claras para usuario.

### 90-105 min - Commit da aula

No PowerShell:

git add .
git commit -m "aula4: tratamento global de erros e cors"

### 105-120 min - Fechamento

Revisao guiada:

- Diferenca entre erro tecnico e erro de negocio.
- Beneficio de padronizar payload de erro.

---

## Aula 5 (120 min) - Angular Basico + Integracao Inicial

### 00-15 min - Criar app Angular

No PowerShell, na raiz do repositorio:

cd frontend
ng new produtos-web --routing --style=css
cd produtos-web
ng serve

Checkpoint:

- App abre em http://localhost:4200.

### 15-30 min - Criar componentes

No PowerShell:

ng generate component pages/lista-produtos
ng generate component pages/form-produto

Checkpoint:

- Componentes criados.

### 30-45 min - Criar servico HTTP

No PowerShell:

ng generate service services/produto-api

No app, configurar HttpClient.

Checkpoint:

- Servico pronto para chamar backend.

### 45-60 min - Implementar listagem

- Chamar GET /produtos no servico.
- Exibir tabela simples na tela.

Checkpoint:

- Lista carrega dados do backend.

### 60-75 min - Implementar cadastro

- Criar formulario simples.
- Chamar POST /produtos.

Checkpoint:

- Novo produto aparece apos cadastro.

### 75-90 min - Mostrar erros e sucesso

- Exibir mensagens de retorno na tela.

Checkpoint:

- Usuario sabe quando falhou e quando salvou.

### 90-105 min - Commit da aula

No PowerShell:

git add .
git commit -m "aula5: angular basico com listagem e cadastro"

### 105-120 min - Fechamento

Revisao guiada:

- O que e um service no Angular?
- Como o frontend consome API REST?

---

## Aula 6 (120 min) - Integracao Final + Apresentacao

### 00-15 min - Retomada geral

Subir backend e frontend em dois terminais.

Backend:

cd backend/produtos-api
mvn spring-boot:run

Frontend:

cd frontend/produtos-web
ng serve

Checkpoint:

- Dois projetos rodando em paralelo.

### 15-30 min - Implementar exclusao

- Botao excluir na lista.
- Chamar DELETE /produtos/{id}.

Checkpoint:

- Exclusao refletida na tela sem recarregar pagina manualmente.

### 30-45 min - Ajustes finais de UX

- Validar campos obrigatorios no formulario.
- Melhorar mensagens de erro.

Checkpoint:

- Fluxo de uso compreensivel para usuario final.

### 45-60 min - Revisao de padroes no codigo

Cada grupo deve apontar no proprio codigo:

- MVC
- Repository
- Service Layer
- DTO
- Observer (RxJS)

Checkpoint:

- Grupo consegue explicar onde cada padrao aparece.

### 60-75 min - Preparar README de entrega

README deve conter:

- requisitos
- como rodar backend
- como rodar frontend
- principais endpoints
- padroes aplicados

Checkpoint:

- Outro colega consegue rodar seguindo o README.

### 75-90 min - Teste de apresentacao

Tempo de 5 minutos por grupo para ensaio.

Checkpoint:

- Demo completa do fluxo: listar, cadastrar, excluir.

### 90-105 min - Commit final

No PowerShell:

git add .
git commit -m "aula6: integracao final e entrega"

### 105-120 min - Apresentacao e feedback

Professor aplica rubrica e devolve feedback curto.

---

## Rubrica objetiva (100 pontos)

- CRUD funcional ponta a ponta: 35
- Organizacao em camadas: 20
- Validacoes e tratamento de erros: 15
- Aplicacao dos padroes: 15
- Documentacao e apresentacao: 15

---

## Lista de verificacao final do professor

- Backend sobe sem erro
- Frontend sobe sem erro
- API responde GET, POST, DELETE
- Angular consome API de verdade
- Erros aparecem com mensagem clara
- Grupo explica o motivo das decisoes

---

## Plano de contingencia (quando algo trava em aula)

Se o backend nao subir:

1. Rodar mvn clean
2. Rodar mvn spring-boot:run
3. Conferir porta 8080 livre

Se o frontend nao subir:

1. Rodar npm install
2. Rodar ng serve
3. Conferir Node e Angular CLI

Se Angular nao acessar API:

1. Verificar URL no service
2. Verificar CORS no backend
3. Verificar se backend esta ativo na 8080

---

## Entrega sugerida para os alunos

- Link do repositorio
- README atualizado
- Relatorio curto (1-2 paginas)
- Apresentacao de 5-10 minutos
