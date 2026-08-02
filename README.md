# SG-RSC

Sistema de Gestão do Reconhecimento de Saberes e Competências
(RSC-PCCTAE)

**Stack:** Java 25 • Spring Boot 3.5 • Angular 17 • PostgreSQL •
Keycloak • MinIO • Docker

------------------------------------------------------------------------

# Índice

1.  Visão Geral
2.  Funcionalidades
3.  Arquitetura
4.  Tecnologias
5.  Estrutura do Projeto
6.  Modelo do Domínio
7.  Fluxo do Processo
8.  Roadmap
9.  Como Executar
10. API REST
11. Banco de Dados
12. Próximas Funcionalidades
13. Diferenciais
14. Licença

------------------------------------------------------------------------

# Visão Geral

O SG-RSC informatiza todo o fluxo do Reconhecimento de Saberes e
Competências (RSC-PCCTAE), desde a solicitação do servidor até a
homologação final.

# Status do Projeto

  Módulo               Status
  -------------------- --------
  Backend              ✅
  Frontend             🚧
  Base Legal Oficial   ✅
  Motor de Regras      ✅
  Motor de Pontuação   🚧
  Avaliação            🚧

# Arquitetura

``` text
Angular 17
    │
Keycloak
    │
Spring Boot
   ├── PostgreSQL
   └── MinIO
```

# Estrutura do Projeto

``` text
features/
├── atividade
├── avaliacao
├── comissao
├── documento
├── legislacao
├── memorial
├── nivelrsc
├── solicitacao
└── servidor
```

# Modelo do Domínio

``` text
Legislação
   │
Requisito
   │
GrupoCritério
   │
Critério
   │
Pontuação
   │
Avaliação
```

# Fluxo

Servidor → Solicitação → Memorial → Atividades → Documentos → Comissão →
Avaliação → Motor de Pontuação → Parecer → Resultado

# Tecnologias

-   Java 25
-   Spring Boot 3.5
-   Angular 17
-   PostgreSQL
-   Flyway
-   Keycloak
-   MinIO
-   Docker

# Roadmap

## Concluído

-   Sprint 1 a Sprint 11

## Próximas

-   Motor de Pontuação
-   Parecer
-   Recursos
-   Dashboard
-   Frontend Angular

# Banco de Dados

Migrações Flyway V01--V15.

# API REST

-   Health
-   Legislação
-   Requisitos
-   Critérios
-   Solicitações
-   Memorial
-   Documentos
-   Avaliação

# Diferenciais

-   Feature-First
-   Base Legal parametrizada
-   Motor de Regras
-   OAuth2/OIDC
-   Soft Delete
-   Auditoria
-   DTO Pattern
-   Mapper Pattern

# Licença

MIT
