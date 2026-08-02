# SG-RSC

![Java](https://img.shields.io/badge/Java-25-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
![Angular](https://img.shields.io/badge/Angular-17-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![MinIO](https://img.shields.io/badge/MinIO-Object_Storage-C72E49)
![Keycloak](https://img.shields.io/badge/Keycloak-26.7.0-4D4D4D)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![Status](https://img.shields.io/badge/Status-Sprint_10_Em_Andamento-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema de Gestão do Reconhecimento de Saberes e Competências (RSC-PCCTAE)

> Sistema web desenvolvido com **Java 25**, **Spring Boot 3.5** e **Angular 17** para informatizar o processo de Reconhecimento de Saberes e Competências (RSC-PCCTAE), utilizando arquitetura **Feature-First**, autenticação via **Keycloak**, banco de dados **PostgreSQL** e armazenamento de documentos no **MinIO**.

---

# Sobre o Projeto

O **SG-RSC** é uma aplicação web desenvolvida para informatizar o processo de solicitação, análise, avaliação, homologação e acompanhamento do **Reconhecimento de Saberes e Competências (RSC)** destinado aos servidores Técnico-Administrativos em Educação integrantes do PCCTAE.

O sistema foi concebido seguindo princípios de arquitetura moderna, organização por funcionalidades (**Feature-First**), baixo acoplamento, alta coesão, escalabilidade, rastreabilidade e facilidade de manutenção.

O desenvolvimento ocorre de forma incremental, por meio de sprints, permitindo que cada módulo seja implementado, testado, documentado e versionado antes do início da próxima etapa.

---

# Objetivos

* Automatizar o processo de concessão do RSC.
* Parametrizar a legislação e os critérios de avaliação.
* Gerenciar solicitações de RSC.
* Gerenciar memoriais descritivos.
* Gerenciar atividades declaradas.
* Centralizar documentos comprobatórios.
* Automatizar o cálculo da pontuação.
* Apoiar as Comissões Avaliadoras.
* Gerenciar comissões e seus membros.
* Padronizar as respostas da API REST.
* Garantir rastreabilidade, auditoria e transparência.
* Facilitar futuras integrações com sistemas institucionais.

---

# Tecnologias

## Backend

* Java 25 LTS
* Spring Boot 3.5
* Spring Security
* Spring Data JPA
* Hibernate
* Bean Validation
* Flyway
* Maven
* OpenAPI (Swagger)

## Frontend

* Angular 17
* TypeScript
* HTML5
* CSS3

## Banco de Dados

* PostgreSQL 16

## Armazenamento

* MinIO
* SDK Java do MinIO
* API compatível com Amazon S3

## Infraestrutura

* Docker
* Docker Compose
* Git
* GitHub

## Autenticação e Autorização

* Keycloak
* OAuth2
* OpenID Connect (OIDC)
* JWT
* Spring Security OAuth2 Resource Server

---

# Arquitetura

```text
                         Angular 17
                              │
                              ▼
                         Keycloak
                    (OAuth2 / OIDC)
                              │
                         JWT Token
                              │
                              ▼
                    Spring Boot 3.5 API
                     /                 \
                    ▼                   ▼
           PostgreSQL 16             MinIO
      (Dados e metadados)          (Arquivos)
```

O backend utiliza arquitetura **Feature-First**, na qual cada funcionalidade concentra seus próprios componentes, como controllers, DTOs, mappers, serviços, repositórios e entidades.

## Organização do Backend

```text
backend/
└── src/main/java/br/gov/ife/sgrsc
    ├── config
    ├── features
    │   ├── atividade
    │   ├── avaliacao
    │   ├── comissao
    │   ├── documento
    │   ├── health
    │   ├── historico
    │   ├── legislacao
    │   ├── memorial
    │   ├── nivelrsc
    │   ├── notificacao
    │   ├── parecer
    │   ├── recurso
    │   ├── resultadosolicitacao
    │   ├── servidor
    │   ├── situacaofuncional
    │   ├── solicitacao
    │   ├── statussolicitacao
    │   ├── statusavaliacao
    │   ├── tipodocumento
    │   └── usuario
    └── shared
        ├── domain
        ├── dto
        ├── exception
        ├── storage
        └── util
```

---

# Funcionalidades Implementadas

## Infraestrutura

* Java 25.
* Spring Boot 3.5.
* PostgreSQL 16.
* Docker e Docker Compose.
* Flyway.
* Spring Security.
* OpenAPI (Swagger).
* Arquitetura Feature-First.
* Auditoria básica das entidades.
* Exclusão lógica por meio de Soft Delete.

## Segurança

* Integração com Keycloak.
* OAuth2 e OpenID Connect.
* Tokens JWT.
* Spring Boot configurado como OAuth2 Resource Server.
* Configuração de autorização por endpoints.
* Preparação para controle de acesso baseado em papéis.

## Tratamento Global de Exceções

* Implementação do `GlobalExceptionHandler`.
* Padronização das respostas de erro da API.
* Criação do `ApiErrorResponse`.
* Criação da `BusinessException`.
* Criação da `ResourceNotFoundException`.
* Tratamento de erros do Bean Validation.
* Respostas padronizadas para erros HTTP 400, 404 e 500.
* Mensagens de validação em português.

Exemplo de resposta:

```json
{
  "timestamp": "2026-08-02T07:54:26.937320968",
  "status": 400,
  "error": "Bad Request",
  "message": "A comissão já possui um presidente ativo.",
  "path": "/api/membros-comissao/2"
}
```

## Paginação e Filtros

* Implementação da classe genérica `PageResponse`.
* Paginação utilizando Spring Data `Pageable`.
* Ordenação dinâmica.
* Pesquisa por termo.
* Filtros por situação.
* Aplicação no módulo Status da Avaliação.
* Aplicação no módulo Comissão.

Estrutura da resposta paginada:

```json
{
  "content": [],
  "page": 0,
  "size": 20,
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "last": true
}
```

## Base Legal

* CRUD de Legislação.
* CRUD de Requisitos.
* CRUD de Critérios.
* Carga inicial da Base Legal por meio da migração Flyway V10.
* DTOs Request e Response.
* Mapper Pattern.
* Exclusão lógica.
* Testes funcionais dos endpoints REST.
* Parametrização dos critérios e pontuações.

## Solicitações

* CRUD de Solicitações.
* Associação ao servidor.
* Associação ao nível de RSC pretendido.
* Controle de status.
* Geração automática de protocolo.
* Validação de documentos obrigatórios.
* Validação de memorial obrigatório.
* Integração com documentos.
* Histórico da protocolização.
* Exclusão lógica.

## Memorial

* CRUD completo.
* Associação à Solicitação.
* Controle de edição.
* Versionamento.
* Consulta por identificador.
* Consulta por solicitação.
* Exclusão lógica.
* Validação de edição apenas para solicitações em rascunho.

## Atividades Declaradas

* CRUD completo.
* Associação opcional ao Critério Pretendido.
* Consulta por Solicitação.
* Exclusão lógica.
* DTOs específicos.
* Mapper Pattern.
* Associação N:N entre Atividades Declaradas e Documentos.
* Remoção do vínculo sem exclusão do documento armazenado.
* Reutilização de documentos comprobatórios.

## Documentos

* Upload de documentos.
* Download de documentos.
* Persistência dos metadados no PostgreSQL.
* Armazenamento físico dos arquivos no MinIO.
* Exclusão lógica.
* Associação dos documentos às solicitações.
* Associação N:N entre Atividades Declaradas e Documentos.
* Reutilização dos documentos em diferentes atividades.
* Cálculo e armazenamento de hash dos arquivos.

## Status da Avaliação

* CRUD completo.
* Parametrização dos status de avaliação.
* Associação com a entidade `Avaliacao`.
* Status iniciais:

  * `EM_ANDAMENTO`;
  * `CONCLUIDA`;
  * `CANCELADA`.
* DTOs Request, Response e Summary.
* Mapper Pattern.
* Service Layer.
* Controller REST.
* Paginação.
* Filtros por termo e situação.
* Migração Flyway V11.
* Testes completos dos endpoints REST via `curl`.

## Comissões de Avaliação

* CRUD completo de Comissões.
* Criação, consulta, atualização e exclusão lógica.
* Paginação e filtros.
* Controle do período de vigência.
* Controle da situação ativa ou inativa.
* Validação da data inicial e final.
* Integração com o tratamento global de exceções.

## Membros da Comissão

* Associação entre Comissão e Servidor.
* Cadastro de membros.
* Consulta dos membros por Comissão.
* Atualização do vínculo.
* Exclusão lógica.
* Controle do período de atuação.
* Enum `PapelMembroComissao`.
* Papéis disponíveis:

  * `PRESIDENTE`;
  * `MEMBRO`;
  * `SECRETARIO`.
* Validação de servidor duplicado na mesma comissão.
* Validação de apenas um presidente ativo por comissão.
* Validação do período do membro em relação à vigência da comissão.
* Integração com o cadastro de Servidores.

## Monitoramento

* Endpoint `/api/health`.
* Verificação da disponibilidade do backend.

---

# Módulos Implementados

* ✅ Health Check
* ✅ Servidores
* ✅ Documentos
* ✅ Base Legal
* ✅ Solicitações
* ✅ Histórico de Solicitações
* ✅ Memorial
* ✅ Atividades Declaradas
* ✅ Status da Avaliação
* ✅ Tratamento Global de Exceções
* ✅ Paginação e Filtros
* ✅ Comissões
* ✅ Membros da Comissão
* 🚧 Avaliação
* 🚧 Motor de Pontuação
* 🚧 Pareceres
* 🚧 Complementações
* 🚧 Recursos
* 🚧 Dashboard Gerencial
* 🚧 Integração com o Frontend Angular

---

# Roadmap

## ✅ Sprint 1 — Estrutura Inicial

* Estrutura inicial do backend.
* Spring Boot.
* PostgreSQL.
* Docker.
* Flyway.
* Arquitetura Feature-First.

## ✅ Sprint 2 — Segurança

* Spring Security.
* Keycloak.
* OAuth2.
* OpenID Connect.
* JWT.
* Endpoint Health.

## ✅ Sprint 3 — Documentos

* Módulo de Documentos.
* Integração com MinIO.
* Upload.
* Download.
* Persistência de metadados.
* Exclusão lógica.

## ✅ Sprint 4 — Base Legal

* CRUD de Legislação.
* CRUD de Requisitos.
* CRUD de Critérios.
* DTOs.
* Mapper Pattern.
* Exclusão lógica.
* Testes REST.

## ✅ Sprint 5 — Solicitações

* CRUD de Solicitações.
* Geração automática de protocolo.
* Histórico.
* Validação de documentos.
* Integração com Documentos.

## ✅ Sprint 6 — Memorial

* CRUD completo.
* Associação à Solicitação.
* Versionamento.
* Validações.
* Exclusão lógica.

## ✅ Sprint 7 — Atividades Declaradas

* CRUD completo.
* Associação opcional ao Critério.
* Associação N:N entre Atividades e Documentos.
* Migração Flyway V9.
* Carga inicial da Base Legal pela V10.
* Ajustes de Segurança.
* Testes completos dos endpoints REST.

## ✅ Sprint 8 — Status da Avaliação

* Entidade `StatusAvaliacao`.
* CRUD completo.
* Associação com Avaliação.
* DTOs Request, Response e Summary.
* Mapper Pattern.
* Service Layer.
* Controller REST.
* Migração Flyway V11.
* Ajustes de segurança.
* Testes completos via `curl`.

## ✅ Sprint 9 — Consolidação da API REST

* Tratamento global de exceções.
* Criação do `GlobalExceptionHandler`.
* Padronização das respostas de erro.
* Tratamento das validações.
* Exceções específicas de negócio e recursos.
* Paginação com Spring Data `Pageable`.
* Filtros por termo e situação.
* Criação do `PageResponse`.
* Aplicação inicial no módulo Status da Avaliação.

## 🚧 Sprint 10 — Fluxo da Comissão Avaliadora

### Concluído

* CRUD completo de Comissões.
* Paginação e filtros de Comissões.
* Controle de vigência.
* CRUD dos Membros da Comissão.
* Integração com Servidores.
* Enum de papéis dos membros.
* Validação de presidente único.
* Validação do período de atuação.
* Testes funcionais via `curl`.

### Próximas etapas

* Evolução do módulo Avaliação.
* Início da avaliação de uma solicitação protocolada.
* Vinculação da avaliação à Comissão.
* Alteração da solicitação para o status `EM_ANALISE`.
* Registro da movimentação no histórico.
* Criação das pontuações por critério.
* Consolidação da pontuação.
* Emissão de parecer.
* Deferimento ou indeferimento.

---

# Como Executar

## Pré-requisitos

* Java 25.
* Maven ou Maven Wrapper.
* Docker Desktop ou Docker Engine.
* Docker Compose.
* Node.js.
* Angular CLI.

## Infraestrutura

Na raiz do projeto:

```bash
docker compose up -d
```

Serviços disponíveis:

| Serviço       | Endereço                |
| ------------- | ----------------------- |
| PostgreSQL    | `localhost:5432`        |
| Keycloak      | `http://localhost:8081` |
| MinIO API     | `http://localhost:9000` |
| MinIO Console | `http://localhost:9001` |

## Backend

```bash
cd backend
./mvnw spring-boot:run
```

Health Check:

```text
GET http://localhost:8080/api/health
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## Frontend

```bash
cd frontend
npm install
npm start
```

Acesso:

```text
http://localhost:4200
```

---

# API REST

## Monitoramento

| Método | Endpoint      | Descrição                             |
| ------ | ------------- | ------------------------------------- |
| GET    | `/api/health` | Verifica a disponibilidade do backend |

## Servidores

| Método | Endpoint               | Descrição                 |
| ------ | ---------------------- | ------------------------- |
| POST   | `/api/servidores`      | Cria um servidor          |
| GET    | `/api/servidores`      | Lista os servidores       |
| GET    | `/api/servidores/{id}` | Consulta um servidor      |
| PUT    | `/api/servidores/{id}` | Atualiza um servidor      |
| DELETE | `/api/servidores/{id}` | Realiza a exclusão lógica |

## Base Legal

| Recurso     | Endpoint           |
| ----------- | ------------------ |
| Legislações | `/api/legislacoes` |
| Requisitos  | `/api/requisitos`  |
| Critérios   | `/api/criterios`   |

Os recursos da Base Legal possuem operações de criação, consulta, atualização, listagem e exclusão lógica.

## Solicitações

| Método | Endpoint                            | Descrição                 |
| ------ | ----------------------------------- | ------------------------- |
| POST   | `/api/solicitacoes`                 | Cria uma solicitação      |
| GET    | `/api/solicitacoes`                 | Lista as solicitações     |
| GET    | `/api/solicitacoes/{id}`            | Consulta uma solicitação  |
| PUT    | `/api/solicitacoes/{id}`            | Atualiza uma solicitação  |
| DELETE | `/api/solicitacoes/{id}`            | Realiza a exclusão lógica |
| POST   | `/api/solicitacoes/{id}/protocolar` | Protocoliza a solicitação |

## Memorial

| Método | Endpoint                          | Descrição                          |
| ------ | --------------------------------- | ---------------------------------- |
| POST   | `/api/memoriais`                  | Cria um memorial                   |
| GET    | `/api/memoriais/{id}`             | Consulta um memorial               |
| GET    | `/api/memoriais/solicitacao/{id}` | Consulta o memorial da solicitação |
| PUT    | `/api/memoriais/{id}`             | Atualiza um memorial               |
| DELETE | `/api/memoriais/{id}`             | Realiza a exclusão lógica          |

## Documentos

| Método | Endpoint                           | Descrição                 |
| ------ | ---------------------------------- | ------------------------- |
| POST   | `/api/documentos`                  | Realiza o upload          |
| GET    | `/api/documentos/{id}`             | Consulta os metadados     |
| GET    | `/api/documentos/solicitacao/{id}` | Lista por solicitação     |
| GET    | `/api/documentos/{id}/download`    | Realiza o download        |
| DELETE | `/api/documentos/{id}`             | Realiza a exclusão lógica |

## Atividades Declaradas

| Método | Endpoint                           | Descrição                 |
| ------ | ---------------------------------- | ------------------------- |
| POST   | `/api/atividades`                  | Cria uma atividade        |
| GET    | `/api/atividades/{id}`             | Consulta uma atividade    |
| GET    | `/api/atividades/solicitacao/{id}` | Lista por solicitação     |
| PUT    | `/api/atividades/{id}`             | Atualiza uma atividade    |
| DELETE | `/api/atividades/{id}`             | Realiza a exclusão lógica |

### Associação Documento × Atividade

| Método | Endpoint                                                 | Descrição            |
| ------ | -------------------------------------------------------- | -------------------- |
| POST   | `/api/atividades/{atividadeId}/documentos`               | Vincula um documento |
| DELETE | `/api/atividades/{atividadeId}/documentos/{documentoId}` | Remove o vínculo     |

## Status da Avaliação

| Método | Endpoint                      | Descrição                     |
| ------ | ----------------------------- | ----------------------------- |
| POST   | `/api/status-avaliacoes`      | Cria um status                |
| GET    | `/api/status-avaliacoes`      | Lista com paginação e filtros |
| GET    | `/api/status-avaliacoes/{id}` | Consulta um status            |
| PUT    | `/api/status-avaliacoes/{id}` | Atualiza um status            |
| DELETE | `/api/status-avaliacoes/{id}` | Remove um status              |

Exemplos:

```text
GET /api/status-avaliacoes?page=0&size=10&sort=nome,asc
GET /api/status-avaliacoes?termo=andamento
GET /api/status-avaliacoes?ativo=true
```

## Comissões

| Método | Endpoint              | Descrição                     |
| ------ | --------------------- | ----------------------------- |
| POST   | `/api/comissoes`      | Cria uma comissão             |
| GET    | `/api/comissoes`      | Lista com paginação e filtros |
| GET    | `/api/comissoes/{id}` | Consulta uma comissão         |
| PUT    | `/api/comissoes/{id}` | Atualiza uma comissão         |
| DELETE | `/api/comissoes/{id}` | Realiza a exclusão lógica     |

Exemplos:

```text
GET /api/comissoes?page=0&size=10
GET /api/comissoes?termo=RSC
GET /api/comissoes?ativa=true
GET /api/comissoes?termo=RSC&ativa=true
```

## Membros da Comissão

| Método | Endpoint                              | Descrição                 |
| ------ | ------------------------------------- | ------------------------- |
| POST   | `/api/comissoes/{comissaoId}/membros` | Adiciona um membro        |
| GET    | `/api/comissoes/{comissaoId}/membros` | Lista os membros          |
| GET    | `/api/membros-comissao/{id}`          | Consulta um membro        |
| PUT    | `/api/membros-comissao/{id}`          | Atualiza um membro        |
| DELETE | `/api/membros-comissao/{id}`          | Realiza a exclusão lógica |

Papéis aceitos:

```text
PRESIDENTE
MEMBRO
SECRETARIO
```

---

# Status Atual

## Concluído

* Infraestrutura completa do projeto.
* Arquitetura Feature-First.
* Integração com PostgreSQL.
* Integração com MinIO.
* Integração com Keycloak.
* Spring Security.
* OpenAPI (Swagger).
* Tratamento global de exceções.
* Padronização das respostas de erro.
* Paginação e filtros.
* Módulo Base Legal.
* Módulo Solicitações.
* Módulo Histórico.
* Módulo Memorial.
* Módulo Atividades Declaradas.
* Associação entre Atividades e Documentos.
* Módulo Status da Avaliação.
* Módulo Comissões.
* Módulo Membros da Comissão.
* Integração entre membros e servidores.
* Regra de presidente único.
* Migrações Flyway V1 a V11.
* Testes funcionais dos endpoints REST.

## Em Desenvolvimento

* Módulo Avaliação.
* Início do fluxo de análise da Comissão.
* Motor de cálculo da pontuação.
* Pareceres.
* Complementações.
* Recursos administrativos.
* Dashboard gerencial.
* Integração das funcionalidades com o Angular.

---

# Histórico de Versões

| Versão      | Descrição                                                                          |
| ----------- | ---------------------------------------------------------------------------------- |
| **v0.1.0**  | Estrutura inicial do projeto                                                       |
| **v0.2.0**  | Arquitetura Feature-First                                                          |
| **v0.3.0**  | Infraestrutura com PostgreSQL, Docker e Flyway                                     |
| **v0.4.0**  | Módulo de Documentos e integração com MinIO                                        |
| **v0.5.0**  | Módulo Base Legal                                                                  |
| **v0.6.0**  | Módulo de Solicitações                                                             |
| **v0.7.0**  | Módulo Memorial                                                                    |
| **v0.8.0**  | Atividades Declaradas, associação entre atividades e documentos e migrações V9/V10 |
| **v0.9.0**  | Status da Avaliação, CRUD completo, associação com Avaliação e migração V11        |
| **v0.10.0** | Tratamento global de exceções, respostas padronizadas, paginação e filtros         |
| **v0.11.0** | Módulo de Comissões, gestão dos membros, papéis e regras de composição             |

---

# Documentação

A documentação detalhada do projeto está disponível no arquivo:

```text
PROJETO.md
```

O documento contém:

* visão geral;
* regras de negócio;
* casos de uso;
* matriz de rastreabilidade;
* modelo de domínio;
* modelo de dados;
* motor de avaliação;
* arquitetura;
* decisões arquiteturais;
* status das sprints.

---

# Licença

Este projeto está licenciado sob a licença **MIT**.
