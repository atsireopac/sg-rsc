# SG-RSC

Sistema de Gestão do Reconhecimento de Saberes e Competências (RSC-PCCTAE)

---

## Sobre o Projeto

O SG-RSC é uma aplicação web desenvolvida para automatizar o processo de solicitação, análise, avaliação e concessão do Reconhecimento de Saberes e Competências (RSC) aos servidores Técnico-Administrativos em Educação (PCCTAE).

O projeto foi concebido seguindo princípios de Arquitetura Limpa, Domain-Driven Design (DDD) e desenvolvimento incremental, priorizando organização, rastreabilidade e facilidade de manutenção.

---

## Objetivos

- Automatizar o fluxo de solicitação do RSC.
- Auxiliar a Diretoria de Gestão de Pessoas (DGP).
- Apoiar as Comissões Avaliadoras.
- Centralizar documentos e memorial.
- Automatizar o cálculo da pontuação.
- Garantir rastreabilidade e auditoria.
- Parametrizar a legislação vigente.

---

## Tecnologias

### Backend

- Java 25
- Spring Boot 3
- Spring Security
- Maven
- Flyway (planejado)
- PostgreSQL

### Frontend

- Angular 17
- TypeScript
- HTML5
- CSS3

### Infraestrutura

- Docker
- Git
- GitHub

---

## Arquitetura

O projeto utiliza arquitetura **Feature-First**, organizada por funcionalidades.

Exemplo:

```text
backend/

features/
    health/
    solicitacao/
    servidor/
    avaliacao/
    legislacao/

config/
shared/
security/
```

---

## Estrutura do Projeto

```text
backend/
frontend/
database/
docker/
docs/
scripts/
```

---

## Documentação

A documentação do projeto está organizada em:

- PROJETO.md
- Modelo de Domínio
- ADRs (Architecture Decision Records)
- Diagramas
- Requisitos
- Arquitetura

---

## Status do Projeto

Sprint 1 ✅

- Estrutura inicial
- Backend Spring Boot
- Frontend Angular
- Comunicação Frontend ↔ Backend
- Health Check
- Spring Security
- CORS

Sprint 2 🚧

- Arquitetura Feature-First
- Modelo de Domínio
- Modelagem do Banco de Dados (em andamento)

---

## Próximos Passos

- Modelagem do banco de dados
- Migrações Flyway
- Entidades Java
- APIs REST
- CRUD de Solicitações
- Upload de documentos
- Dashboard
- Autenticação completa

---
## Histórico de Versões

| Versão | Descrição |
|---------|-----------|
| v0.1.0 | Estrutura inicial do projeto |
| v0.2.0 | Arquitetura Feature-First e Modelo de Domínio |

## Licença

MIT