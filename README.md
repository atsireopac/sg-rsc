# SG-RSC

![Java](https://img.shields.io/badge/Java-25-blue) ![Spring
Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
![Angular](https://img.shields.io/badge/Angular-17-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![MinIO](https://img.shields.io/badge/MinIO-Object_Storage-C72E49)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![Status](https://img.shields.io/badge/Status-Sprint_4_Concluída-brightgreen)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema de Gestão do Reconhecimento de Saberes e Competências
(RSC-PCCTAE)

> Sistema web desenvolvido em Java 25, Spring Boot 3.5 e Angular 17 para
> gerenciamento do processo de Reconhecimento de Saberes e Competências
> (RSC-PCCTAE), utilizando arquitetura Feature-First, autenticação via
> Keycloak, PostgreSQL e armazenamento de documentos no MinIO.

------------------------------------------------------------------------

# Sobre o Projeto

O **SG-RSC** tem como objetivo informatizar todo o processo de
solicitação, análise, avaliação e homologação do Reconhecimento de
Saberes e Competências (RSC) destinado aos servidores
Técnico-Administrativos em Educação (PCCTAE).

O projeto é desenvolvido de forma incremental, priorizando organização
por funcionalidades, baixo acoplamento, facilidade de manutenção e
escalabilidade.

------------------------------------------------------------------------

# Objetivos

-   Automatizar o processo de concessão do RSC.
-   Apoiar a DGP e as Comissões Avaliadoras.
-   Parametrizar a legislação vigente.
-   Centralizar documentos comprobatórios.
-   Automatizar o cálculo de pontuação.
-   Garantir rastreabilidade e auditoria.

------------------------------------------------------------------------

# Tecnologias

## Backend

-   Java 25 LTS
-   Spring Boot 3.5
-   Spring Security
-   Spring Data JPA
-   Flyway
-   Maven
-   OpenAPI (Swagger)

## Frontend

-   Angular 17
-   TypeScript

## Banco de Dados

-   PostgreSQL 16

## Infraestrutura

-   Docker
-   Docker Compose
-   MinIO
-   Git
-   GitHub

## Autenticação

-   Keycloak
-   OAuth2
-   OpenID Connect (OIDC)
-   JWT

------------------------------------------------------------------------

# Arquitetura

``` text
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

``` text
backend/
├── config
├── features
│   ├── documento
│   ├── health
│   ├── legislacao
│   │   ├── controller
│   │   ├── dto
│   │   ├── entity
│   │   ├── mapper
│   │   ├── repository
│   │   └── service
│   ├── servidor
│   ├── situacaofuncional
│   └── solicitacao
├── security
└── shared
```

------------------------------------------------------------------------

# Funcionalidades Implementadas

## Infraestrutura

-   Java 25
-   Spring Boot 3.5
-   PostgreSQL 16
-   Docker
-   Flyway
-   Spring Security
-   OpenAPI (Swagger)
-   Arquitetura Feature-First

## Base Legal

-   CRUD de Legislação
-   CRUD de Requisito
-   CRUD de Critério
-   DTOs Request/Response
-   Mapper Pattern
-   Soft Delete
-   Testes dos endpoints REST

## Documentos

-   Upload de documentos
-   Download de documentos
-   Persistência de metadados
-   Armazenamento no MinIO
-   Exclusão lógica

## Monitoramento

-   Endpoint `/api/health`

------------------------------------------------------------------------

# Roadmap

## ✅ Sprint 1

-   Estrutura inicial
-   Spring Boot
-   PostgreSQL
-   Docker
-   Flyway
-   Arquitetura Feature-First

## ✅ Sprint 2

-   Spring Security
-   Estrutura para Keycloak
-   OAuth2 / OIDC
-   JWT
-   Health Check

## ✅ Sprint 3

-   Módulo de Documentos
-   Integração com MinIO
-   Upload e Download
-   Swagger

## ✅ Sprint 4

-   Módulo Base Legal
-   CRUD de Legislação
-   CRUD de Requisito
-   CRUD de Critério
-   DTOs
-   Mappers
-   Soft Delete
-   Testes REST

## 🚧 Sprint 5

-   Solicitação de RSC
-   Associação Documento × Critério
-   Memorial
-   Fluxo de Protocolo

## Sprint 6

-   Fluxo da Comissão
-   Pareceres
-   Motor de Pontuação
-   Recursos

## Sprint 7

-   Dashboard
-   Relatórios
-   Indicadores
-   Auditoria

------------------------------------------------------------------------

# Como Executar

## Pré-requisitos

-   Java 25
-   Maven
-   Docker Desktop
-   Node.js
-   Angular CLI

## Infraestrutura

``` bash
docker compose up -d
```

Serviços: - PostgreSQL - Keycloak - MinIO

## Backend

``` bash
cd backend
./mvnw spring-boot:run
```

Health:

    GET http://localhost:8080/api/health

## Frontend

``` bash
cd frontend
npm install
npm start
```

Acesso:

    http://localhost:4200

------------------------------------------------------------------------

# API REST

Swagger:

    http://localhost:8080/swagger-ui.html

## Principais Endpoints

-   GET /api/health
-   CRUD /api/legislacoes
-   CRUD /api/requisitos
-   CRUD /api/criterios

------------------------------------------------------------------------

# Status Atual

O backend possui sua primeira funcionalidade de negócio completamente
implementada.

Concluído:

-   Infraestrutura da aplicação;
-   Módulo Base Legal;
-   CRUD de Legislação;
-   CRUD de Requisito;
-   CRUD de Critério;
-   Integração com MinIO;
-   OpenAPI (Swagger);
-   Testes manuais dos endpoints REST.

Próxima etapa:

-   Implementação do módulo de Solicitação de RSC.

------------------------------------------------------------------------

# Histórico de Versões

  -----------------------------------------------------------------------
  Versão                          Descrição
  ------------------------------- ---------------------------------------
  v0.1.0                          Estrutura inicial do projeto

  v0.2.0                          Arquitetura Feature-First

  v0.3.0                          PostgreSQL, Docker, Flyway e
                                  infraestrutura

  v0.4.0                          Módulo de Documentos e integração com
                                  MinIO

  v0.5.0                          Módulo Base Legal (Legislação,
                                  Requisito e Critério), DTOs, Mappers,
                                  Soft Delete e testes REST
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# Licença

Este projeto está licenciado sob a licença **MIT**.
