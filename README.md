# SG-RSC

![Java](https://img.shields.io/badge/Java-25-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
![Angular](https://img.shields.io/badge/Angular-17-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED)
![Status](https://img.shields.io/badge/Status-Em_Desenvolvimento-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema de Gestão do Reconhecimento de Saberes e Competências (RSC-PCCTAE)

---

## Sobre o Projeto

O **SG-RSC** é uma aplicação web desenvolvida para informatizar todo o processo de solicitação, análise, avaliação e homologação do **Reconhecimento de Saberes e Competências (RSC)** destinado aos servidores Técnico-Administrativos em Educação (PCCTAE).

O projeto está sendo desenvolvido de forma incremental, utilizando uma arquitetura **Feature-First**, com foco em organização por funcionalidades, baixo acoplamento, código limpo e facilidade de manutenção. À medida que evoluir, incorporará princípios de **Domain-Driven Design (DDD)** sempre que agregarem valor à modelagem do domínio.

---

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
- Git
- GitHub

## Autenticação (Próxima Sprint)

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
                     │
                     ▼
             Spring Boot API
                     │
                     ▼
              PostgreSQL 16
```

O backend utiliza uma organização **Feature-First**, separando controllers, services, repositories, DTOs e entidades por funcionalidade.

Exemplo:

```text
backend/

features/
    health/
    servidor/
    solicitacao/
    situacaofuncional/
    resultadosolicitacao/

config/
shared/
security/
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

- Estrutura inicial do projeto
- Backend Spring Boot
- Frontend Angular
- PostgreSQL em Docker
- Docker Compose
- Migrações com Flyway
- Health Check
- Configuração de CORS
- Spring Security
- Auditoria das entidades

## Backend

- CRUD inicial de Servidor
- Cadastro de Situação Funcional
- Relacionamentos JPA
- Persistência com Spring Data JPA

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

## Sprint 2 🚧

- Integração com Keycloak
- OAuth2 / OpenID Connect
- JWT
- Cadastro de Usuários
- Perfis de Acesso

## Sprint 3

- CRUD de Solicitações
- Upload de documentos
- Memorial Descritivo

## Sprint 4

- Fluxo de Avaliação
- Pareceres
- Resultado Final

## Sprint 5

- Dashboard
- Relatórios
- Indicadores

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

# Status do Projeto

🚧 Em desenvolvimento

Atualmente o projeto encontra-se na implementação da infraestrutura de autenticação utilizando **Keycloak**, preparando a base para controle de usuários, perfis de acesso e autorização baseada em papéis (RBAC).

---

# Histórico de Versões

| Versão | Descrição |
|---------|-----------|
| v0.1.0 | Estrutura inicial do projeto |
| v0.2.0 | Arquitetura Feature-First e modelo inicial |
| v0.3.0 | PostgreSQL, Docker, Flyway, auditoria e CRUD inicial de Servidor |

---

# Licença

Este projeto está licenciado sob a licença **MIT**.