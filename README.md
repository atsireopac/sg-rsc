# SG-RSC

![Java](https://img.shields.io/badge/Java-25-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
![Angular](https://img.shields.io/badge/Angular-17-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![MinIO](https://img.shields.io/badge/MinIO-Object_Storage-C72E49)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![Status](https://img.shields.io/badge/Status-Sprint_8_Concluída-brightgreen)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema de Gestão do Reconhecimento de Saberes e Competências (RSC-PCCTAE)

> Sistema web desenvolvido utilizando **Java 25**, **Spring Boot 3.5** e **Angular 17** para informatizar todo o processo de Reconhecimento de Saberes e Competências (RSC-PCCTAE), seguindo arquitetura **Feature-First**, autenticação via **Keycloak**, banco de dados **PostgreSQL** e armazenamento de documentos no **MinIO**.

---

# Sobre o Projeto

O **SG-RSC** é uma aplicação web desenvolvida para informatizar todo o processo de solicitação, análise, avaliação, homologação e acompanhamento do **Reconhecimento de Saberes e Competências (RSC)** destinado aos servidores Técnico-Administrativos em Educação (PCCTAE).

O sistema foi concebido seguindo princípios de arquitetura moderna, organização por funcionalidades (**Feature-First**), baixo acoplamento, alta coesão, escalabilidade e facilidade de manutenção.

O desenvolvimento ocorre de forma incremental, utilizando metodologia baseada em sprints, permitindo evolução contínua da aplicação.

---

# Objetivos

- Automatizar todo o processo de concessão do RSC.
- Parametrizar a legislação vigente.
- Gerenciar solicitações de RSC.
- Gerenciar memoriais descritivos.
- Gerenciar atividades declaradas.
- Centralizar documentos comprobatórios.
- Automatizar o cálculo de pontuação.
- Apoiar as Comissões Avaliadoras.
- Garantir rastreabilidade, auditoria e transparência.

---

# Tecnologias

## Backend

- Java 25 LTS
- Spring Boot 3.5
- Spring Security
- Spring Data JPA
- Flyway
- Maven
- OpenAPI (Swagger)

## Frontend

- Angular 17
- TypeScript

## Banco de Dados

- PostgreSQL 16

## Armazenamento

- MinIO

## Infraestrutura

- Docker
- Docker Compose
- Git
- GitHub

## Autenticação

- Keycloak
- OAuth2
- OpenID Connect (OIDC)
- JWT

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
           PostgreSQL 16            MinIO
      (Metadados)             (Documentos)
```

## Organização do Backend

```text
backend/
├── config
├── features
│   ├── atividade
│   ├── criterio
│   ├── documento
│   ├── health
│   ├── legislacao
│   ├── memorial
│   ├── requisito
│   ├── servidor
│   ├── situacaofuncional
│   ├── solicitacao
│   └── statusavaliacao
├── security
└── shared
```

---

# Funcionalidades Implementadas

## Infraestrutura

- Java 25
- Spring Boot 3.5
- PostgreSQL 16
- Docker
- Docker Compose
- Flyway
- Spring Security
- OpenAPI (Swagger)
- Arquitetura Feature-First

## Segurança

- Integração com Keycloak
- OAuth2 / OpenID Connect
- JWT
- Configuração de autorização por endpoints

## Base Legal

- CRUD de Legislação
- CRUD de Requisitos
- CRUD de Critérios
- Carga inicial da Base Legal (Flyway V10)
- DTOs Request/Response
- Mapper Pattern
- Soft Delete
- Testes REST

## Solicitações

- CRUD de Solicitações
- Geração automática de protocolo
- Controle de status
- Histórico de protocolização
- Integração com documentos

## Memorial

- CRUD completo
- Associação à Solicitação
- Controle de edição
- Versionamento
- Exclusão lógica

## Atividades Declaradas

- CRUD completo
- Associação opcional ao Critério Pretendido
- Consulta por Solicitação
- Exclusão lógica
- DTOs específicos
- Mapper Pattern

## Status da Avaliação

- CRUD completo
- Parametrização dos status de avaliação
- Associação com a entidade Avaliação
- DTOs Request/Response/Summary
- Mapper Pattern
- Service Layer
- Controller REST
- Migração Flyway V11
- Testes completos dos endpoints REST via curl

## Documentos

- Upload de documentos
- Download de documentos
- Persistência dos metadados
- Armazenamento físico no MinIO
- Exclusão lógica
- Associação N:N entre Atividades Declaradas e Documentos
- Reutilização de documentos em diferentes atividades

## Monitoramento

- Endpoint `/api/health`

# Módulos Implementados

- ✅ Health Check
- ✅ Documentos
- ✅ Base Legal
- ✅ Solicitações
- ✅ Memorial
- ✅ Atividades Declaradas
- ✅ Status da Avaliação
- 🚧 Avaliação
- 🚧 Motor de Pontuação
- 🚧 Pareceres
- 🚧 Recursos

---

# Roadmap

## ✅ Sprint 1

- Estrutura inicial do backend
- Spring Boot
- PostgreSQL
- Docker
- Flyway
- Arquitetura Feature-First

## ✅ Sprint 2

- Spring Security
- Keycloak
- OAuth2 / OIDC
- JWT
- Endpoint Health

## ✅ Sprint 3

- Módulo de Documentos
- Integração com MinIO
- Upload
- Download
- Soft Delete

## ✅ Sprint 4

- Módulo Base Legal
- CRUD de Legislação
- CRUD de Requisitos
- CRUD de Critérios
- DTOs
- Mapper Pattern
- Soft Delete
- Testes REST

## ✅ Sprint 5

- Módulo de Solicitações
- CRUD de Solicitações
- Geração de protocolo
- Histórico
- Integração com Documentos

## ✅ Sprint 6

- Módulo Memorial
- CRUD completo
- Associação à Solicitação
- Versionamento
- Validações
- Soft Delete

## ✅ Sprint 7

- Módulo Atividades Declaradas
- CRUD completo
- Associação opcional ao Critério
- Associação N:N entre Atividades e Documentos
- Migração Flyway V9
- Carga inicial da Base Legal (V10)
- Ajustes de Segurança
- Testes completos dos endpoints REST

## ✅ Sprint 8

- Módulo Status da Avaliação
- CRUD completo
- Entidade StatusAvaliacao
- Associação com Avaliação
- DTOs Request/Response/Summary
- Mapper Pattern
- Service Layer
- Controller REST
- Flyway V11
- Ajustes de segurança
- Testes completos via curl

## 🚧 Próxima Sprint

- Tratamento global de exceções (GlobalExceptionHandler)
- Padronização das respostas de erro da API
- Paginação e filtros dos endpoints REST
- Evolução do módulo Avaliação
- Fluxo da Comissão Avaliadora

---

# Como Executar

## Pré-requisitos

- Java 25
- Maven
- Docker Desktop
- Node.js
- Angular CLI

## Infraestrutura

```bash
docker compose up -d
```

Serviços disponíveis:

- PostgreSQL
- Keycloak
- MinIO

## Backend

```bash
cd backend
./mvnw spring-boot:run
```

Health Check:

```
GET http://localhost:8080/api/health
```

Swagger:

```
http://localhost:8080/swagger-ui.html
```

## Frontend

```bash
cd frontend
npm install
npm start
```

Acesso:

```
http://localhost:4200
```

---

# API REST

## Monitoramento

- GET `/api/health`

## Base Legal

- CRUD `/api/legislacoes`
- CRUD `/api/requisitos`
- CRUD `/api/criterios`

## Solicitações

- CRUD `/api/solicitacoes`

## Memorial

- CRUD `/api/memoriais`

## Documentos

- POST `/api/documentos`
- GET `/api/documentos/{id}`
- GET `/api/documentos/solicitacao/{id}`

## Atividades Declaradas

- POST `/api/atividades`
- GET `/api/atividades/{id}`
- GET `/api/atividades/solicitacao/{id}`
- PUT `/api/atividades/{id}`
- DELETE `/api/atividades/{id}`

### Associação Documento × Atividade

- POST `/api/atividades/{atividadeId}/documentos`
- DELETE `/api/atividades/{atividadeId}/documentos/{documentoId}`

## Status da Avaliação

- POST `/api/status-avaliacoes`
- GET `/api/status-avaliacoes`
- GET `/api/status-avaliacoes/{id}`
- PUT `/api/status-avaliacoes/{id}`
- DELETE `/api/status-avaliacoes/{id}`

---

# Status Atual

## Concluído

- Infraestrutura completa do projeto;
- Arquitetura Feature-First;
- Integração com PostgreSQL;
- Integração com MinIO;
- Spring Security;
- Keycloak;
- OpenAPI (Swagger);
- Módulo Base Legal;
- Módulo Solicitações;
- Módulo Memorial;
- Módulo Atividades Declaradas;
- Associação entre Atividades e Documentos;
- Migrações Flyway V1 a V11;
- Testes funcionais completos dos endpoints REST.
- Módulo Status da Avaliação;
- Migração Flyway V11;
- Parametrização dos status de avaliação;
- Testes completos dos endpoints REST do módulo.

## Em Desenvolvimento

- Motor de cálculo da pontuação.
- Fluxo de avaliação da Comissão.
- Pareceres.
- Recursos administrativos.
- Dashboard gerencial.

---

# Histórico de Versões

| Versão | Descrição |
|---------|-----------|
| **v0.1.0** | Estrutura inicial do projeto |
| **v0.2.0** | Arquitetura Feature-First |
| **v0.3.0** | Infraestrutura (PostgreSQL, Docker e Flyway) |
| **v0.4.0** | Módulo de Documentos e integração com MinIO |
| **v0.5.0** | Módulo Base Legal |
| **v0.6.0** | Módulo de Solicitações |
| **v0.7.0** | Módulo Memorial |
| **v0.8.0** | Módulo Atividades Declaradas, associação entre atividades e documentos, Flyway V9/V10, carga inicial da Base Legal e validação completa dos endpoints REST |
| **v0.9.0** | Módulo Status da Avaliação, CRUD completo, associação com Avaliação, Flyway V11, DTOs Request/Response/Summary, ajustes no Spring Security e validação completa dos endpoints REST |

---

# Licença

Este projeto está licenciado sob a licença **MIT**.

