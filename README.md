# SG-RSC

![Java](https://img.shields.io/badge/Java-25-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
![Angular](https://img.shields.io/badge/Angular-17-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![MinIO](https://img.shields.io/badge/MinIO-Object_Storage-C72E49)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![Status](https://img.shields.io/badge/Status-Em_Desenvolvimento-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema de Gestão do Reconhecimento de Saberes e Competências (RSC-PCCTAE)

> Sistema web desenvolvido em Java e Angular para gerenciamento do processo de Reconhecimento de Saberes e Competências (RSC), com autenticação via Keycloak, armazenamento de documentos no MinIO e arquitetura Feature-First.

---

## Sobre o Projeto

O **SG-RSC** é uma aplicação web desenvolvida para informatizar todo o processo de solicitação, análise, avaliação e homologação do **Reconhecimento de Saberes e Competências (RSC)** destinado aos servidores Técnico-Administrativos em Educação (PCCTAE).

O projeto está sendo desenvolvido de forma incremental, utilizando uma arquitetura **Feature-First**, com foco em organização por funcionalidades, baixo acoplamento, código limpo e facilidade de manutenção. À medida que evoluir, incorporará princípios de **Domain-Driven Design (DDD)** sempre que agregarem valor à modelagem do domínio.

---

## Índice

- Sobre o Projeto
- Objetivos
- Tecnologias
- Arquitetura
- Estrutura do Projeto
- Funcionalidades Implementadas
- Roadmap
- Como Executar
- API REST
- Documentação
- Status do Projeto
- Histórico de Versões
- Licença

# Objetivos

- Automatizar o fluxo completo do processo de RSC.
- Auxiliar a Diretoria de Gestão de Pessoas (DGP).
- Apoiar as Comissões Avaliadoras.
- Centralizar documentos comprobatórios e memoriais.
- Automatizar o cálculo da pontuação.
- Garantir rastreabilidade e auditoria.
- Parametrizar a legislação vigente.
- Reduzir atividades manuais e retrabalho.

---

# Tecnologias

## Backend

- Java 25 LTS
- Spring Boot 3.5
- Spring Security
- Spring Data JPA
- Maven
- Flyway
- OpenAPI (Swagger)

## Frontend

- Angular 17
- TypeScript
- HTML5
- CSS3

## Banco de Dados

- PostgreSQL 16

## Infraestrutura

- Docker
- Docker Compose
- PostgreSQL 16
- MinIO (Object Storage)
- Git
- GitHub

## Autenticação

- Keycloak
- OAuth2
- OpenID Connect (OIDC)
- JWT

---

# Arquitetura

O sistema será composto pela seguinte arquitetura:

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
      (Metadados do Sistema)   (Documentos)
```

O backend utiliza uma organização **Feature-First**, separando controllers, services, repositories, DTOs e entidades por funcionalidade.

Exemplo:

```text
backend/

config/
features/
security/
shared/

features/
    documento/
    health/
    servidor/
    situacaofuncional/
    solicitacao/
    resultadosolicitacao/

shared/
    storage/
```

---

# Estrutura do Projeto

```text
backend/
frontend/
database/
docker/
docs/
scripts/
```

---

# Funcionalidades Implementadas

## Infraestrutura

## Módulo de Servidores

- CRUD de Servidor
- CRUD de Situação Funcional

## Módulo de Solicitações

- CRUD de Solicitações

## Módulo de Documentos

- Cadastro de Tipos de Documento
- Upload de documentos
- Download de documentos
- Exclusão lógica
- Persistência de metadados
- Armazenamento físico no MinIO

## Infraestrutura

- PostgreSQL
- Flyway
- Spring Security
- OpenAPI
- Docker Compose
- Health Check

---

# Roadmap

## Sprint 1 ✅

- Estrutura inicial
- Backend Spring Boot
- Frontend Angular
- PostgreSQL
- Docker
- Flyway
- Spring Security
- CRUD inicial de Servidor
- Situação Funcional
- Auditoria das entidades

## Sprint 2 ✅

- Integração com Keycloak
- OAuth2 / OpenID Connect
- JWT
- Cadastro de Usuários
- Perfis de Acesso

## Sprint 3 ✅

- CRUD de Solicitações
- Upload de documentos
- Download de documentos
- MinIO
- Swagger

## Sprint 4

- Critérios
- Memorial
- Associação Documento × Critério
- Fluxo de Protocolo

## Sprint 5

- Fluxo da Comissão
- Pareceres
- Pontuação
- Recursos

## Sprint 6

- Dashboard
- Relatórios
- Indicadores
- Auditoria

---

# Como Executar

## Pré-requisitos

- Java 25 LTS
- Docker Desktop
- Node.js
- Angular CLI
- Maven

---

## Banco de Dados

```bash
docker compose up -d
```
Após a execução do comando, serão iniciados os seguintes serviços:

- PostgreSQL 16
- Keycloak
- MinIO
---



## Backend

```bash
cd backend
./mvnw spring-boot:run
```

Health Check:

```
GET http://localhost:8080/api/health
```

---

## Frontend

```bash
cd frontend

npm install

npm start
```

Aplicação:

```
http://localhost:4200
```

---

# API REST

A documentação interativa é gerada automaticamente pelo OpenAPI (Swagger), permitindo visualizar e testar todos os endpoints disponíveis.

```
http://localhost:8080/swagger-ui.html
```

### MinIO Console

```
http://localhost:9001
```

Usuário:

```
sgrsc
```

Senha:

```
********
```

# Documentação

A documentação técnica encontra-se na pasta **docs/**.

Principais documentos:

- Projeto.md
- Arquitetura.md
- ModeloDeDados.md
- API.md
- Roadmap.md
- DecisoesArquiteturais.md
- Deploy.md

---

## Status Atual

Atualmente o SG-RSC possui sua infraestrutura principal concluída, incluindo autenticação baseada em Keycloak, persistência com PostgreSQL, versionamento de banco com Flyway, documentação da API com OpenAPI (Swagger) e armazenamento de documentos utilizando MinIO.

O desenvolvimento segue de forma incremental, com foco na implementação dos módulos de negócio previstos para o processo de Reconhecimento de Saberes e Competências (RSC-PCCTAE).

---

# Próximas Implementações

- Associação de documentos aos critérios de avaliação
- Memorial descritivo
- Motor de cálculo da pontuação
- Fluxo de análise da comissão
- Emissão de pareceres
- Recursos administrativos
- Dashboard gerencial
- Relatórios

# Histórico de Versões

| Versão | Descrição |
|---------|-----------|
| v0.1.0 | Estrutura inicial do projeto |
| v0.2.0 | Arquitetura Feature-First e modelo inicial |
| v0.3.0 | PostgreSQL, Docker, Flyway, auditoria e CRUD inicial de Servidor |
| v0.4.0 | Integração com MinIO, módulo de documentos, upload, download e documentação da arquitetura |

---

# Licença

Este projeto está licenciado sob a licença **MIT**.