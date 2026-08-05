# Sistema de Gestão do Reconhecimento de Saberes e Competências (RSC-PCCTAE) SG-RSC

# Controle de Versões

| Versão | Data | Autor | Alterações |
| ------- | ---- | ----- | ---------- |
| 1.0 | 03/07/2026 | Erik Barbosa | Criação do documento. |
| **1.1** | **20/07/2026** | **Erik Barbosa** | **Atualização da arquitetura, definição do Keycloak como provedor de identidade, atualização do roadmap e adequação da arquitetura Feature-First.** |
| **1.2** | **29/07/2026** | **Erik Barbosa** | **Implementação do módulo de documentos, integração com MinIO para armazenamento de arquivos, inclusão dos fluxos de upload e download, atualização da arquitetura da solução e documentação da estratégia de armazenamento.** |
| **1.3** | **29/07/2026** | **Erik Barbosa** | **Implementação do módulo Base Legal, CRUD completo de Legislação, Requisito e Critério, DTOs Request/Response, Mappers, Soft Delete, consolidação da arquitetura Feature-First e validação dos endpoints REST do backend.** |
| **1.4** | **29/07/2026** | **Erik Barbosa** | **Implementação do módulo de Solicitações, geração automática de protocolo, integração com documentos, histórico de movimentações (auditoria funcional), validação da protocolização e evolução do fluxo administrativo do RSC.** |
| **1.5** | **30/07/2026** | **Erik Barbosa** | **Implementação do módulo Memorial, CRUD completo, controle de versão, validação de edição apenas em solicitações em rascunho, DTOs específicos para criação e atualização, consultas por identificador e por solicitação, exclusão lógica e validação dos endpoints REST.** |
| **1.6** | **30/07/2026** | **Erik Barbosa** | **Implementação do módulo Atividades Declaradas, associação de atividades aos critérios pretendidos, vínculo entre atividades e documentos, migração V9, carga inicial da Base Legal por meio da migração V10, testes completos dos endpoints REST e validação da integração com o armazenamento de documentos.** |
| **1.7** | **30/07/2026** | **Erik Barbosa** | **Implementação do módulo Status da Avaliação, CRUD completo, migração Flyway V11, entidade StatusAvaliacao, integração com Avaliação, DTOs Request/Response/Summary, Mapper Pattern, Service Layer, Controller REST, ajustes no Spring Security e validação completa dos endpoints REST via curl.** |
| **1.8** | **31/07/2026** | **Erik Barbosa** | **Implementação da infraestrutura de tratamento global de exceções da API REST, criação do GlobalExceptionHandler, padronização das respostas de erro, tratamento das validações Bean Validation e criação das exceções de domínio BusinessException e ResourceNotFoundException.** |
| **1.8** | **31/07/2026** | **Erik Barbosa** | **Implementação da padronização global de tratamento de exceções da API REST (GlobalExceptionHandler), criação das exceções de negócio e recurso não encontrado, padronização das respostas de erro, implementação de paginação e filtros nos endpoints REST utilizando Spring Data Pageable e criação do PageResponse para respostas paginadas.** |
| **1.9** | **02/08/2026** | **Erik Barbosa** | **Implementação do módulo Comissão, CRUD completo de Comissões e Membros da Comissão, validação de presidente único por comissão, paginação e filtros dos endpoints REST, enum PapelMembroComissao, integração com Servidor e preparação da infraestrutura para o módulo Avaliação.** |
| **1.10** | **02/08/2026** | **Erik Barbosa** | **Implementação do módulo Avaliação, incluindo criação da entidade Avaliação, integração com Comissões, Status da Avaliação e Solicitações, início do fluxo administrativo de análise, registro automático do histórico da solicitação, migração Flyway V12, paginação e filtros dos endpoints REST, validações das regras de negócio e testes completos da API via curl.** |
| **1.11** | **02/08/2026** | **Erik Barbosa** | **Conclusão da modelagem oficial da Base Legal do RSC-PCCTAE, implementação dos Grupos de Critérios, Regras de Complexidade por Nível de RSC, carga oficial dos 59 critérios previstos no Decreto nº 13.048/2026 por meio das migrações Flyway V13, V14 e V15, parametrização do motor de regras e atualização das entidades, DTOs, serviços e repositórios para suportar o novo modelo de domínio.** |
| **1.12** | **02/08/2026** | **Erik Barbosa** | **Implementação da primeira versão do Motor de Pontuação do RSC, incluindo migração Flyway V16, cálculo automático da pontuação parametrizado pela Base Legal, homologação integral e parcial pela Comissão, validações de regras de negócio, novos endpoints REST de cálculo e homologação, expansão do modelo de domínio da entidade Pontuação e testes funcionais completos do fluxo de avaliação via curl.** |
| **1.13** | **03/08/2026** | **Erik Barbosa** | Implementação do **Motor de Complexidade e Elegibilidade do RSC**, incluindo consolidação automática das pontuações homologadas por Grupo de Critérios, cálculo dos totais da avaliação, validação das regras de complexidade parametrizadas por nível de RSC, criação da **ComplexidadeEngine**, implementação do **ComplexidadeService** e **ComplexidadeController**, novos endpoints REST para consulta da elegibilidade, testes unitários (JUnit 5) e validação funcional completa via `curl`. |
| **1.14** | **05/08/2026** | **Erik Barbosa** | **Implementação do módulo Parecer Técnico, incluindo Motor de Parecer, geração automática de fundamentação baseada no Motor de Complexidade, emissão de pareceres, controle de versões, edição antes da assinatura, assinatura eletrônica lógica, bloqueio de alterações após assinatura, consultas individuais e por avaliação, testes unitários e validação funcional completa dos endpoints REST.** |



# Glossário

| Sigla | Descrição |
|--------|-----------|
| RSC | Reconhecimento de Saberes e Competências |
| PCCTAE | Plano de Carreira dos Cargos Técnico-Administrativos em Educação |
| DGP | Diretoria de Gestão de Pessoas |
| CRSC | Comissão de Reconhecimento de Saberes e Competências |
| IFE | Instituição Federal de Ensino |
| MEC | Ministério da Educação |
| IQ | Incentivo à Qualificação |

# Stakeholders

## Primários

- Servidor Técnico-Administrativo
- Comissão de RSC
- DGP
- Gestão Superior

## Secundários

- STI
- Auditoria Interna
- CGU
- MEC

# Objetivos Estratégicos

O SG-RSC deverá contribuir para:

- reduzir o tempo médio de análise;
- padronizar as avaliações;
- diminuir o retrabalho;
- aumentar a transparência;
- facilitar auditorias;
- melhorar a experiência do servidor;
- apoiar a tomada de decisão da DGP.

# Premissas

Este projeto considera que:

- todas as solicitações serão eletrônicas;
- os documentos serão digitais;
- os usuários possuirão vínculo institucional;
- as regras do Decreto nº 13.048 serão a base inicial da aplicação;
- alterações legislativas poderão exigir evolução do sistema.

# Restrições

- O sistema deverá seguir a LGPD.
- O sistema deverá registrar auditoria.
- O sistema deverá funcionar nos principais navegadores.
- O sistema deverá utilizar software livre sempre que possível.
- O sistema deverá permitir futura integração com sistemas institucionais.

# Missão

Disponibilizar uma solução moderna, segura e transparente para gestão do Reconhecimento de Saberes e Competências nas Instituições Federais de Ensino, simplificando o trabalho dos servidores e da administração pública.

# Capítulo 1 - Visão Geral do Projeto

# Resumo Executivo

O SG-RSC é uma plataforma web destinada à gestão do processo de Reconhecimento de Saberes e Competências (RSC-PCCTAE). O sistema automatiza as etapas de solicitação, análise, validação documental, cálculo de pontuação e emissão de pareceres, proporcionando maior eficiência administrativa, transparência, segurança e conformidade com a legislação vigente.

A solução foi concebida para atender às Instituições Federais de Ensino, podendo ser adaptada às particularidades de cada instituição.

# Princípios do Projeto

O desenvolvimento do SG-RSC seguirá os seguintes princípios:

- Simplicidade.
- Clareza das regras de negócio.
- Facilidade de manutenção.
- Segurança da informação.
- Transparência administrativa.
- Escalabilidade.
- Modularidade.
- Alto desempenho.
- Conformidade com a legislação.
- Excelente experiência do usuário.

# Visão Arquitetural

O SG-RSC será desenvolvido utilizando arquitetura em camadas, separando claramente as responsabilidades da aplicação.

A solução será composta por:

- Front-end Angular 17;
- Keycloak como Identity Provider;
- API REST em Spring Boot 3.5;
- Banco de dados PostgreSQL 16;
- MinIO como serviço de armazenamento de documentos;
- Serviço de geração de relatórios.

Essa separação permite que cada componente evolua de forma independente, favorecendo a escalabilidade, a manutenibilidade e a reutilização da solução.

# Decisões Arquiteturais

Este projeto adotará:

- Java 25;
- Spring Boot 3;
- Angular 17;
- PostgreSQL;
- MinIO;
- SDK Java do MinIO;
- Maven;
- Git;
- Docker;
- API REST;
- JSON;
- Keycloak como Identity Provider;
- OAuth2;
- OpenID Connect (OIDC);
- JWT emitido pelo Keycloak;
- Spring Security OAuth2 Resource Server;
- OpenAPI (Swagger);
- JUnit 5;
- Mockito.

# Objetivo de Qualidade

O objetivo deste projeto não é apenas atender aos requisitos funcionais, mas servir como referência de boas práticas de desenvolvimento de software, empregando princípios como SOLID, Clean Code, arquitetura em camadas, documentação adequada, testes automatizados e evolução incremental.


---

# 1. Apresentação

O Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC) é uma aplicação web desenvolvida para apoiar a gestão dos processos de concessão do Reconhecimento de Saberes e Competências (RSC-PCCTAE), instituído pela Lei nº 11.091/2005 e regulamentado pelo Decreto nº 13.048, de 3 de julho de 2026.

O sistema tem como objetivo informatizar, padronizar e otimizar todo o fluxo de solicitação, análise e concessão do RSC, proporcionando maior eficiência administrativa, transparência, rastreabilidade e segurança das informações.

O projeto foi concebido para atender às necessidades das Instituições Federais de Ensino (IFEs), podendo ser adaptado à realidade de cada instituição.

---

# 2. Contexto

Com a regulamentação do RSC-PCCTAE, as Instituições Federais de Ensino passaram a analisar solicitações de reconhecimento baseadas na trajetória profissional dos servidores técnico-administrativos.

Esse processo envolve grande volume documental, análise de requisitos legais, conferência de pontuação, emissão de pareceres e registro das decisões administrativas.

Na maioria das instituições, essas atividades tendem a ser realizadas de forma predominantemente manual, exigindo elevado esforço operacional das equipes responsáveis.

Além disso, o Decreto nº 13.048 estabelece critérios objetivos de avaliação, exigindo análise detalhada da documentação apresentada pelo servidor, bem como verificação do atendimento às regras de pontuação, requisitos específicos e documentação comprobatória.

Diante desse cenário, torna-se necessária a implementação de uma solução tecnológica capaz de apoiar todas as etapas do processo.

---

# 3. Problema

O processo tradicional apresenta diversos desafios:

- preenchimento manual das solicitações;
- ausência de validação automática das regras do Decreto;
- cálculo manual da pontuação;
- conferência individual da documentação;
- dificuldade na organização dos documentos;
- ausência de indicadores gerenciais;
- risco de erros humanos;
- retrabalho decorrente de documentação incompleta;
- baixa rastreabilidade das análises realizadas;
- dificuldade para auditoria dos processos.

Esses fatores aumentam significativamente o tempo médio de análise das solicitações.

---

# 4. Proposta

O SG-RSC propõe uma plataforma única para gerenciamento de todo o processo administrativo de concessão do RSC-PCCTAE.

A solução será responsável por apoiar tanto o servidor requerente quanto a comissão responsável pela análise, automatizando atividades repetitivas e reduzindo significativamente o esforço operacional.

Entre as funcionalidades previstas destacam-se:

- abertura eletrônica das solicitações;
- envio de documentação digital;
- cálculo automático da pontuação;
- validação preliminar dos requisitos legais;
- geração automática do protocolo;
- acompanhamento do andamento do processo;
- emissão de pareceres;
- controle das decisões da comissão;
- geração de relatórios gerenciais;
- trilha completa de auditoria.

---

# 5. Objetivos

O projeto possui como principais objetivos:

- informatizar integralmente o processo de concessão do RSC;
- reduzir o tempo de análise das solicitações;
- aumentar a transparência do processo;
- minimizar erros de cálculo de pontuação;
- garantir conformidade com a legislação vigente;
- facilitar o trabalho da Comissão de RSC;
- disponibilizar informações gerenciais para a DGP;
- oferecer melhor experiência ao servidor requerente.

---

# 6. Público-Alvo

O sistema será utilizado por diferentes perfis de usuários.

## Servidor

Responsável pela abertura da solicitação e envio da documentação.

## Comissão de RSC

Responsável pela análise técnica da solicitação.

## DGP

Responsável pela gestão administrativa do processo.

## Administrador do Sistema

Responsável pela administração da aplicação.

---

# 7. Benefícios Esperados

A implantação do SG-RSC proporcionará:

- redução do tempo de análise dos processos;
- diminuição do retrabalho;
- padronização das avaliações;
- maior transparência;
- segurança das informações;
- rastreabilidade completa;
- apoio à tomada de decisão;
- geração automática de indicadores;
- conformidade com o Decreto nº 13.048/2026.

---

# 8. Escopo Inicial

A primeira versão do sistema contemplará:

- cadastro de usuários;
- autenticação;
- abertura de solicitações;
- upload de documentos;
- cálculo automático da pontuação;
- painel da comissão;
- emissão de parecer;
- histórico das movimentações;
- geração de protocolo;
- relatórios básicos.

As funcionalidades serão evoluídas de forma incremental ao longo do projeto.

---

# 9. Visão de Futuro

O SG-RSC foi concebido para ser uma solução escalável, podendo futuramente integrar-se a sistemas institucionais como SIGRH, SIPAC, SEI ou outras plataformas utilizadas pelas Instituições Federais de Ensino.

Também poderão ser incorporados recursos de inteligência artificial para apoio à análise documental, identificação automática de inconsistências, classificação de documentos e auxílio à comissão avaliadora.

---

Fim do Capítulo 1.

# Capítulo 2 - Regras de Negócio

# Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC)

---

# 2.1 Objetivo

Este capítulo descreve as regras de negócio que deverão ser implementadas pelo SG-RSC.

As regras foram definidas com base na Lei nº 11.091/2005, no Decreto nº 13.048/2026 e em boas práticas de gestão de processos administrativos.

Todas as funcionalidades do sistema deverão respeitar as regras aqui estabelecidas.

---

# 2.2 Conceito de Regra de Negócio

Entende-se por regra de negócio toda condição, restrição, validação ou comportamento obrigatório que deverá ser observado pelo sistema durante o processamento das solicitações de RSC.

Sempre que houver conflito entre uma funcionalidade do sistema e a legislação vigente, prevalecerá a legislação.

---

# 2.3 Princípios Gerais

O sistema deverá observar os seguintes princípios:

- Legalidade
- Impessoalidade
- Moralidade
- Publicidade
- Eficiência
- Transparência
- Segurança da Informação
- Rastreabilidade
- Auditabilidade

---

# 2.4 Regras Gerais

## RN001 – Somente servidores ativos poderão solicitar RSC.

Descrição:

O sistema somente permitirá abertura de solicitação para servidores ativos pertencentes ao PCCTAE.

Validação:

Caso o servidor não esteja ativo, a solicitação deverá ser bloqueada.

---

## RN002 – Um servidor poderá possuir apenas uma solicitação em andamento.

Descrição:

Não será permitida abertura de nova solicitação enquanto existir outra em análise.

---

## RN003 – Toda solicitação deverá possuir número único.

Descrição:

Cada solicitação receberá um identificador único gerado automaticamente.

---

## RN004 – Toda solicitação deverá possuir data de abertura.

---

## RN005 – Todas as ações deverão ser registradas em auditoria.

Exemplos:

- criação
- edição
- envio
- parecer
- recurso
- deferimento
- indeferimento

---

# 2.5 Regras relacionadas ao Decreto

## RN100 – Estágio Probatório

Base Legal:

Art. 12.

Regra:

O servidor em estágio probatório não poderá solicitar RSC.

Comportamento esperado:

O sistema impedirá a abertura da solicitação.

Mensagem:

"Servidor em estágio probatório não pode solicitar o Reconhecimento de Saberes e Competências."

Observação:

As atividades realizadas durante o estágio poderão ser consideradas futuramente.

---

## RN101 – Memorial

Base Legal:

Art. 13.

Regra:

O memorial é obrigatório.

Sem memorial não será possível finalizar a solicitação.

---

## RN102 – Documentação

Todo critério informado deverá possuir documentação comprobatória.

Caso contrário, o critério será considerado pendente.

---

## RN103 – Pontuação

A pontuação será calculada automaticamente pelo sistema.

O cálculo será realizado exclusivamente com base nas regras cadastradas.

Não será permitido cálculo manual.

A comissão poderá homologar ou ajustar a pontuação mediante justificativa.

---

## RN104 – Critérios Obrigatórios

Cada nível de RSC possui critérios mínimos.

O sistema deverá validar:

- pontuação mínima;
- quantidade mínima de critérios;
- requisitos obrigatórios.

---

## RN105 – Reutilização

Uma atividade somente poderá ser utilizada uma única vez.

Caso seja reutilizada:

O sistema deverá emitir alerta.

---

## RN106 – Documentos

Cada documento poderá comprovar um ou mais critérios.

O sistema deverá permitir o vínculo entre documentos e critérios.

---

## RN107 – Prazo

O sistema deverá controlar automaticamente o prazo de análise da comissão.

Quando próximo ao vencimento:

Emitir alerta.

---

# 2.6 Fluxo da Solicitação

Estados possíveis:

Rascunho

↓

Documentação Incompleta

↓

Aguardando Envio

↓

Protocolada

↓

Em Análise

↓

Aguardando Complementação

↓

Complementação Recebida

↓

Em Reanálise

↓

Deferida

ou

Indeferida

↓

Recurso

↓

Decisão Final

↓

Concluída

---

# 2.7 Regras da Comissão

A comissão poderá:

✔ analisar documentos

✔ solicitar complementação

✔ ajustar pontuação

✔ registrar parecer

✔ deferir

✔ indeferir

✔ responder recurso

Toda decisão deverá possuir fundamentação.

---

# 2.8 Regras de Auditoria

O sistema deverá registrar:

quem

quando

o que

IP

data

hora

ação executada

valor anterior

valor novo

---

# 2.9 Regras de Segurança

Nenhum documento poderá ser excluído definitivamente.

Somente administradores poderão parametrizar critérios.

A comissão não poderá alterar documentos enviados pelo servidor.

O servidor não poderá alterar documentos após o protocolo.

---

# 2.10 Regras de Notificação

O sistema enviará notificações quando:

Solicitação protocolada

Complementação solicitada

Prazo próximo do vencimento

Parecer emitido

Recurso recebido

Decisão publicada

---

# 2.11 Regras Futuras

O sistema deverá permitir inclusão de novos critérios sem necessidade de alteração significativa do código-fonte.

As regras de pontuação deverão ser parametrizáveis.

O sistema deverá permitir adaptação para futuras alterações legislativas.

---

Fim do Capítulo 2.

# Capítulo 3 – Casos de Uso

# Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC)

---

# 3.1 Objetivo

Este capítulo descreve todas as funcionalidades disponíveis no SG-RSC sob a perspectiva dos usuários do sistema.

Cada caso de uso representa uma interação entre um ator e o sistema, descrevendo objetivos, pré-condições, fluxo principal, fluxos alternativos, exceções e regras de negócio envolvidas.

---

# 3.2 Atores do Sistema

O SG-RSC possui os seguintes atores:

## Servidor

Responsável por solicitar o RSC, anexar documentos, acompanhar o andamento do processo e interpor recurso quando cabível.

---

## Comissão de RSC

Responsável por analisar as solicitações, validar documentos, calcular a pontuação definitiva e emitir pareceres.

---

## DGP

Responsável pelo acompanhamento administrativo do processo, gestão dos usuários e emissão de relatórios institucionais.

---

## Administrador do Sistema

Responsável pelas configurações técnicas do sistema, parametrizações e manutenção administrativa.

---

# 3.3 Diagrama Geral dos Casos de Uso

Servidor
│
├── Fazer Login
├── Atualizar Dados
├── Criar Solicitação
├── Editar Solicitação
├── Anexar Documentos
├── Gerar Memorial
├── Protocolar Solicitação
├── Consultar Andamento
├── Responder Complementação
├── Interpor Recurso
└── Consultar Histórico

Comissão
│
├── Receber Solicitação
├── Validar Documentos
├── Solicitar Complementação
├── Analisar Critérios
├── Calcular Pontuação
├── Emitir Parecer
├── Deferir
├── Indeferir
└── Julgar Recurso

DGP
│
├── Gerenciar Usuários
├── Emitir Relatórios
├── Acompanhar Processos
├── Configurar Sistema
└── Consultar Estatísticas

Administrador
│
├── Gerenciar Perfis
├── Parametrizar Critérios
├── Gerenciar Permissões
├── Auditoria
└── Configurações Gerais

---

# 3.4 Casos de Uso

## UC001 – Autenticar Usuário

### Objetivo

Permitir acesso ao sistema.

### Ator

Todos.

### Pré-condições

Usuário cadastrado.

### Fluxo Principal

1. Informar login.
2. Informar senha.
3. Sistema valida credenciais.
4. Sistema identifica perfil.
5. Sistema direciona para o painel correspondente.

### Fluxos Alternativos

Credenciais inválidas.

Usuário bloqueado.

Senha expirada.

### Regras

RN001

---

## UC002 – Criar Solicitação

### Objetivo

Permitir abertura de nova solicitação.

### Ator

Servidor.

### Pré-condições

Servidor ativo.

Não possuir solicitação em andamento.

Não estar em estágio probatório.

### Fluxo Principal

1. Selecionar "Nova Solicitação".
2. Sistema cria rascunho.
3. Informar dados.
4. Salvar.

### Regras

RN001

RN002

RN100

---

## UC003 – Anexar Documentos

### Objetivo

Permitir que o servidor envie documentos comprobatórios para uma solicitação de RSC.

### Ator

Servidor.

### Pré-condições

- O usuário deve estar autenticado.
- A solicitação deve existir.
- O tipo de documento deve estar cadastrado.

### Fluxo Principal

1. O servidor seleciona a solicitação.
2. O servidor informa o tipo de documento.
3. O servidor seleciona o arquivo.
4. O sistema valida os dados informados.
5. O arquivo é armazenado no MinIO.
6. Os metadados do documento são persistidos no PostgreSQL.
7. O documento é associado à solicitação.
8. O sistema disponibiliza o documento para consulta e download.

### Fluxos Alternativos

- Arquivo inválido.
- Solicitação inexistente.
- Tipo de documento inexistente.
- Falha na comunicação com o serviço de armazenamento.

### Pós-condições

- O documento permanece armazenado no MinIO.
- Os metadados permanecem registrados no PostgreSQL.
- O documento pode ser listado, baixado ou removido logicamente.

### Regras de Negócio

RN102 – Documentação.

## UC003A – Gerenciar Atividades Declaradas

### Objetivo

Permitir que o servidor cadastre, consulte, altere e remova as atividades desenvolvidas que serão utilizadas para comprovação dos critérios do Reconhecimento de Saberes e Competências (RSC).

### Ator

Servidor.

### Pré-condições

- O usuário deve estar autenticado.
- Deve existir uma solicitação em andamento.
- A solicitação deve permitir edição.

### Fluxo Principal

1. O servidor acessa a solicitação.
2. Seleciona a opção **Adicionar Atividade**.
3. Informa o título, a descrição e o período de realização da atividade.
4. Seleciona, opcionalmente, o critério pretendido relacionado à atividade.
5. Salva a atividade.
6. O sistema registra a atividade na solicitação.
7. O servidor poderá vincular um ou mais documentos comprobatórios à atividade.

### Fluxos Alternativos

- Solicitação inexistente.
- Solicitação não permite edição.
- Critério informado inexistente.
- Documento inexistente.

### Pós-condições

- A atividade permanece vinculada à solicitação.
- Os documentos vinculados permanecem armazenados no MinIO.
- Os vínculos entre atividade e documentos poderão ser alterados posteriormente sem excluir os documentos da solicitação.

### Regras de Negócio

- RN102 – Documentação.
- RN105 – Reutilização de atividades.

## UC004 – Gerar Memorial

Objetivo

Auxiliar o servidor na elaboração do memorial.

Fluxo

Selecionar experiências.

Selecionar critérios.

Sistema gera estrutura.

Servidor complementa.

Salvar.

Exportar PDF.

---

## UC005 – Protocolar Solicitação

### Objetivo

Permitir que o servidor finalize oficialmente uma solicitação de RSC, gerando seu número de protocolo e iniciando o fluxo administrativo de análise.

### Ator

Servidor.

### Pré-condições

- A solicitação deve existir.
- A solicitação deve estar em status **Rascunho**.
- Deve existir pelo menos um documento anexado.
- O Memorial Descritivo deve ter sido enviado.

### Fluxo Principal

1. O servidor solicita a protocolização.
2. O sistema valida as regras de negócio.
3. O sistema gera automaticamente um número único de protocolo.
4. O status da solicitação é alterado para **Protocolada**.
5. A data de protocolização é registrada.
6. O sistema registra automaticamente o evento no histórico da solicitação.
7. A solicitação torna-se pronta para análise pela Comissão.

### Fluxos Alternativos

- Solicitação inexistente.
- Solicitação já protocolada.
- Solicitação sem documentos anexados.
- Memorial obrigatório não enviado.

### Pós-condições

- Número de protocolo gerado.
- Data de protocolização registrada.
- Histórico atualizado.
- Solicitação disponível para análise.

### Regras de Negócio

- RN101 – Memorial obrigatório.
- RN102 – Documentação obrigatória.
- RN005 – Auditoria das ações.
---

## UC006 – Consultar Processo

Servidor poderá consultar:

Status

Pontuação

Documentos

Histórico

Pendências

Notificações

---

## UC007 – Solicitar Complementação

Ator

Comissão.

Fluxo

Abrir processo.

Selecionar pendências.

Informar justificativa.

Definir prazo.

Enviar.

Sistema altera status.

---

## UC008 – Responder Complementação

Ator

Servidor.

Fluxo

Visualizar pendências.

Enviar novos documentos.

Protocolar resposta.

---

## UC009 – Calcular Pontuação

Ator

Sistema.

Descrição

O sistema realizará o cálculo automático da pontuação.

A comissão poderá:

Confirmar.

Alterar.

Justificar alterações.

---

## UC010 – Emitir Parecer Técnico

### Objetivo

Permitir que a Comissão registre, revise, assine e consulte pareceres técnicos produzidos durante a avaliação de uma solicitação de RSC.

### Ator

Comissão de RSC.

### Pré-condições

- Deve existir uma Avaliação.
- A Avaliação deve possuir resultado calculado pelo Motor de Complexidade.
- Deve existir um Tipo de Parecer ativo.

### Fluxo Principal

1. A Comissão solicita a geração da sugestão de parecer.
2. O sistema consulta o Motor de Complexidade.
3. O Motor de Parecer gera automaticamente uma fundamentação técnica.
4. A Comissão poderá alterar o texto sugerido.
5. A Comissão informa o tipo de parecer.
6. O parecer é emitido.
7. O parecer permanece editável enquanto não estiver assinado.
8. Após revisão, o parecer é assinado.
9. O sistema bloqueia novas alterações.

### Fluxos Alternativos

- Avaliação inexistente.
- Tipo de parecer inexistente.
- Parecer já assinado.
- Parecer inexistente.

### Pós-condições

- Parecer registrado.
- Histórico preservado.
- Parecer bloqueado após assinatura.

### Regras de Negócio

- RN103 – Utilização do resultado oficial da avaliação.
- RN107 – Controle do fluxo de análise.

---

## UC011 – Interpor Recurso

Ator

Servidor.

Fluxo

Abrir recurso.

Escrever fundamentação.

Anexar documentos.

Protocolar.

---

## UC012 – Julgar Recurso

Ator

Comissão.

Fluxo

Analisar recurso.

Registrar decisão.

Encerrar processo.

---

## UC013 – Gerenciar Critérios

Administrador.

Cadastrar novos critérios.

Editar.

Inativar.

Controlar versões.

---

## UC014 – Emitir Relatórios

DGP.

Relatórios:

Solicitações.

Pontuação.

Pendências.

Tempo médio.

Usuários.

Indicadores.

---

## UC015 – Consultar Auditoria

Administrador.

Visualizar:

Usuário.

Data.

Hora.

IP.

Ação.

Objeto.

Valor anterior.

Valor novo.

---

## UC016 – Gerenciar Comissões

### Objetivo

Permitir o cadastro, alteração, consulta, listagem e inativação das Comissões responsáveis pela avaliação das solicitações de RSC.

### Ator

Administrador.

### Pré-condições

- Usuário autenticado.
- Perfil com permissão administrativa.

### Fluxo Principal

1. Cadastrar comissão.
2. Informar período de vigência.
3. Informar descrição.
4. Ativar comissão.
5. Salvar.

### Fluxos Alternativos

- Data final anterior à data inicial.
- Comissão inexistente.

### Pós-condições

A comissão ficará disponível para vinculação de membros e futuras avaliações.

---

## UC017 – Gerenciar Membros da Comissão

### Objetivo

Permitir a composição da comissão por servidores da instituição.

### Ator

Administrador.

### Pré-condições

- Comissão cadastrada.
- Servidor cadastrado.

### Fluxo Principal

1. Selecionar comissão.
2. Selecionar servidor.
3. Informar papel.
4. Informar período de atuação.
5. Salvar.

### Fluxos Alternativos

- Comissão já possui presidente ativo.
- Período inválido.
- Servidor inexistente.

### Regras de Negócio

- Apenas um PRESIDENTE ativo por comissão.
- Todo membro deve estar vinculado a um servidor.

---

## UC018 – Iniciar Avaliação

### Objetivo

Permitir que a Comissão inicie oficialmente a análise de uma solicitação protocolada.

### Ator

Comissão de RSC.

### Pré-condições

- Solicitação existente.
- Solicitação em status **Protocolada**.
- Comissão cadastrada.
- Status da Avaliação **Em Andamento** cadastrado.
- Não existir avaliação ativa para a solicitação.

### Fluxo Principal

1. A Comissão seleciona uma solicitação protocolada.
2. O sistema valida todas as regras de negócio.
3. O sistema cria uma Avaliação.
4. O sistema associa a Comissão responsável.
5. O sistema define automaticamente o status da Avaliação como **Em Andamento**.
6. O sistema altera o status da Solicitação para **Em Análise**.
7. O sistema registra a data de início da avaliação.
8. O sistema cria automaticamente um registro no Histórico da Solicitação.
9. A avaliação passa a ficar disponível para consulta e acompanhamento.

### Fluxos Alternativos

- Solicitação inexistente.
- Comissão inexistente.
- Solicitação não protocolada.
- Avaliação já iniciada para a solicitação.

### Pós-condições

- Avaliação criada.
- Solicitação em análise.
- Histórico atualizado.
- Comissão vinculada à solicitação.

### Regras de Negócio

- RN005 – Auditoria.
- RN107 – Controle do fluxo de avaliação.

---

# 3.5 Matriz de Permissões

| Funcionalidade | Servidor | Comissão | DGP | Administrador |
|----------------|----------|-----------|------|---------------|
| Criar Solicitação | ✔ | | | |
| Anexar Documento | ✔ | | | |
| Protocolar | ✔ | | | |
| Consultar Processo | ✔ | ✔ | ✔ | ✔ |
| Emitir Parecer | | ✔ | | |
| Solicitar Complementação | | ✔ | | |
| Julgar Recurso | | ✔ | | |
| Relatórios | | | ✔ | ✔ |
| Parametrizar Sistema | | | | ✔ |
| Auditoria | | | | ✔ |
| Gerenciar Comissões | | | ✔ | ✔ |
| Gerenciar Membros da Comissão | | | ✔ | ✔ |

---

# 3.6 Evolução dos Casos de Uso

Os casos de uso apresentados representam a primeira versão funcional do sistema e poderão ser ampliados conforme evolução da legislação, necessidades institucionais e novas funcionalidades identificadas durante o desenvolvimento.

---
---

# 3.7 Matriz de Rastreabilidade

## Objetivo

A Matriz de Rastreabilidade tem como finalidade estabelecer o relacionamento entre os Casos de Uso, as Regras de Negócio, os módulos da aplicação, as entidades do domínio, as interfaces do usuário, as APIs e os testes automatizados.

Essa matriz permitirá identificar rapidamente os impactos decorrentes de alterações nos requisitos, garantindo maior controle sobre a evolução do sistema e facilitando as atividades de desenvolvimento, testes, manutenção e auditoria.

Ao longo do desenvolvimento do SG-RSC, esta matriz será continuamente atualizada para refletir a implementação das funcionalidades.

---

## Legenda

| Sigla | Descrição |
|--------|-----------|
| UC | Caso de Uso (Use Case) |
| RN | Regra de Negócio |
| API | Endpoint REST responsável pela funcionalidade |
| UI | Interface do Usuário (Tela) |
| ENT | Entidade do domínio |
| TS | Caso de Teste (Test Scenario) |

---

## Matriz de Rastreabilidade

| UC | Funcionalidade | RN | Módulo | Entidade Principal | API | Tela | Teste | Status |
|----|----------------|----|---------|--------------------|------|-------|--------|--------|
| UC001 | Autenticar Usuário | RN001 | Autenticação | Usuário | POST /auth/login | Login | TS001 | Planejado |
| UC002 | Criar Solicitação | RN001, RN002, RN100 | Solicitações | Solicitação | POST /solicitacoes | Nova Solicitação | TS002 | Planejado |
| UC003 | Anexar Documentos | RN102 | Documentos | Documento | POST /documentos | Upload de Documentos | TS003 | Implementado |
| UC003A | Gerenciar Atividades Declaradas | RN102, RN105 | Atividades Declaradas | Atividade Declarada | `/api/atividades` | Atividades Declaradas | TS003A | **Implementado** |
| UC004 | Gerar Memorial | RN101 | Memorial | Memorial | `/api/memoriais` | Memorial | TS004 | Implementado |
| UC005 | Protocolar Solicitação | RN101, RN102, RN103, RN104 | Solicitações | Solicitação | POST /solicitacoes/protocolar | Protocolar Solicitação | TS005 | Implementado |
| UC006 | Consultar Processo | RN005 | Solicitações | Solicitação | GET /solicitacoes/{id} | Consulta da Solicitação | TS006 | Planejado |
| UC007 | Solicitar Complementação | RN107 | Comissão | Complementação | POST /complementacoes | Solicitar Complementação | TS007 | Planejado |
| UC008 | Responder Complementação | RN107 | Comissão | Complementação | POST /complementacoes/responder | Responder Complementação | TS008 | Planejado |
| UC009 | Calcular Pontuação | RN103, RN104, RN105 | Pontuação | Pontuação | **POST /api/pontuacoes/calcular** | Cálculo da Pontuação | TS009 | **Implementado** |
| UC010 | Emitir Parecer Técnico | RN103, RN107 | Parecer Técnico | Parecer | POST /api/pareceres/avaliacao/{id}/emitir | Parecer Técnico | TS010 | Implementado |
| UC011 | Interpor Recurso | RN005 | Recursos | Recurso | POST /recursos | Recurso Administrativo | TS011 | Planejado |
| UC012 | Julgar Recurso | RN005 | Recursos | Recurso | POST /recursos/julgar | Julgamento do Recurso | TS012 | Planejado |
| UC013 | Gerenciar Critérios | RN104 | Administração | Critério | POST /criterios | Cadastro de Critérios | TS013 | Planejado |
| UC014 | Emitir Relatórios | RN005 | Relatórios | Relatório | GET /relatorios | Relatórios Gerenciais | TS014 | Planejado |
| UC015 | Consultar Auditoria | RN005 | Auditoria | Auditoria | GET /auditoria | Auditoria | TS015 | Planejado |
| UC016 | Gerenciar Comissões | RN107 | Comissão | Comissão | /api/comissoes | Cadastro de Comissão | TS016 | Implementado |
| UC017 | Gerenciar Membros da Comissão | RN107 | Comissão | MembroComissao | /api/comissoes/{id}/membros | Composição da Comissão | TS017 | Implementado |
| UC018 | Iniciar Avaliação | RN005, RN107 | Avaliação | Avaliação | POST /api/avaliacoes/iniciar | Painel da Comissão | TS018 | Implementado |

---

## Benefícios da Matriz de Rastreabilidade

A utilização desta matriz proporciona diversos benefícios para o projeto:

- Garantir que todas as regras de negócio estejam implementadas.
- Facilitar a identificação do impacto de mudanças na legislação.
- Permitir rastrear cada funcionalidade desde sua concepção até sua implementação.
- Auxiliar na elaboração de testes automatizados.
- Facilitar auditorias internas e externas.
- Servir como referência para novos desenvolvedores da equipe.
- Aumentar a qualidade e a manutenibilidade do sistema.

---

## Processo de Atualização

A Matriz de Rastreabilidade deverá ser revisada sempre que ocorrer qualquer uma das seguintes situações:

- Inclusão de um novo Caso de Uso;
- Alteração de uma Regra de Negócio;
- Criação de novas APIs;
- Inclusão de novas telas;
- Criação de novas entidades de domínio;
- Alteração da legislação aplicável ao RSC-PCCTAE;
- Inclusão de novos cenários de teste.

A atualização desta matriz é obrigatória antes da conclusão de cada sprint de desenvolvimento, garantindo sua aderência ao estado atual do sistema.

---
Fim do Capítulo 3.

# Capítulo 4 – Modelo de Domínio

# Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC)

---

# 4.1 Objetivo

O Modelo de Domínio representa os principais objetos de negócio existentes no SG-RSC e os relacionamentos entre eles.

Seu objetivo é organizar as informações manipuladas pelo sistema de forma clara, facilitando o desenvolvimento, a manutenção e a evolução da aplicação.

As entidades apresentadas neste capítulo representam o domínio do problema e não necessariamente a estrutura física do banco de dados.

---

# 4.2 Conceito

No SG-RSC, uma entidade representa um objeto do mundo real que possui identidade própria e participa do processo de concessão do Reconhecimento de Saberes e Competências.

Cada entidade possuirá atributos, relacionamentos e regras de negócio específicas.

---

# 4.3 Principais Entidades

O sistema será composto, inicialmente, pelas seguintes entidades:

- Usuário
- Servidor
- Solicitação
- Memorial
- Legislação
- Requisito
- Critério
- Atividade Declarada
- Documento
- Pontuação
- Parecer
- Comissão
- Complementação
- Recurso
- Auditoria
- Notificação
- Status da Avaliação

---

# 4.4 Entidade Usuário

Representa qualquer pessoa autenticada no sistema.

Atributos:

- id
- nome
- email
- senha
- perfil
- status
- dataCadastro
- ultimoAcesso

Relacionamentos:

- pode estar vinculado a um Servidor;
- pode pertencer à Comissão;
- pode ser Administrador.

---

# 4.5 Entidade Servidor

Representa o servidor técnico-administrativo que poderá solicitar o RSC.

Atributos:

- id
- matrícula
- nome
- CPF
- cargo
- nível
- classe
- unidade
- dataPosse
- situaçãoFuncional
- estágioProbatório

Relacionamentos:

- possui um Usuário;
- pode possuir várias Solicitações.

---

# 4.6 Entidade Solicitação

Representa o processo administrativo iniciado pelo servidor.

Atributos:

- id
- número
- dataAbertura
- dataProtocolo
- status
- nívelRSCPretendido
- pontuaçãoCalculada
- pontuaçãoHomologada

Relacionamentos:

- pertence a um Servidor;
- possui um Memorial;
- possui vários Documentos;
- possui vários Pareceres;
- pode possuir Complementações;
- pode possuir Recursos.

---

# 4.7 Entidade Memorial

Representa o memorial descritivo apresentado pelo servidor.

Atributos:

- id
- texto
- dataCriação
- dataAtualização
- versão

Relacionamentos:

- pertence a uma Solicitação.

---

# 4.8 Entidade Atividade Declarada

Representa cada atividade declarada pelo servidor como evidência de sua trajetória profissional e que poderá ser utilizada para comprovação dos critérios previstos na legislação do RSC.

Cada atividade descreve um fato ou realização do servidor e pode estar associada a um critério pretendido e a um ou mais documentos comprobatórios.

### Atributos

- id
- título
- descrição
- dataInício
- dataFim
- dataCriação
- dataAtualização

### Relacionamentos

- pertence a uma Solicitação;
- pode estar associada a um Critério;
- pode possuir um ou mais Documentos;
- participa do processo de avaliação da solicitação.

# 4.9 Entidade Documento

Representa qualquer documento anexado ao processo.

Atributos:

- id
- nomeArquivo
- tipoDocumento
- tamanho
- dataEnvio
- hashArquivo

Relacionamentos:

- pertence a uma Solicitação;
- pode comprovar um ou mais Critérios.

---

# 4.10 Entidade Critério

Representa um critério previsto no Decreto nº 13.048/2026.

Atributos:

- id
- código
- descrição
- requisito
- pontuação
- ativo

### Relacionamentos

- pertence a um Requisito;
- pode estar associado a várias Atividades Declaradas;
- pode ser comprovado por um ou mais Documentos;
- participa do cálculo da Pontuação.

---

### 4.11 Entidade Pontuação

Representa o resultado do cálculo e da homologação da pontuação realizada durante a avaliação de uma solicitação.

Cada registro de pontuação corresponde à avaliação de uma Atividade Declarada em relação a um Critério Oficial da Base Legal.

#### Atributos

- id
- quantidadeDeclarada
- quantidadeHomologada
- pontosUnitarios
- pontosDeclarados
- pontosHomologados
- status
- justificativa
- createdAt
- updatedAt

#### Relacionamentos

- pertence a uma Avaliação;
- referencia uma Atividade Declarada;
- referencia um Critério Oficial;
- participa do cálculo da pontuação total da solicitação.

#### Regras

- a pontuação declarada é calculada automaticamente pelo sistema;
- a comissão poderá homologar integral ou parcialmente a pontuação;
- não poderá existir mais de uma pontuação ativa para a mesma atividade dentro de uma avaliação;
- a quantidade homologada nunca poderá ser superior à quantidade declarada;
- toda alteração realizada pela comissão deverá possuir justificativa quando houver divergência da pontuação calculada.
---

# 4.12 Entidade Status da Avaliação

Representa os possíveis estados de uma avaliação realizada pela Comissão de RSC.

Essa entidade foi criada para parametrizar os estados do processo de avaliação, evitando valores fixos no código-fonte e permitindo futura evolução do fluxo administrativo.

### Atributos

- id
- codigo
- nome
- descricao
- ativo

### Relacionamentos

- pode estar associado a várias Avaliações;
- representa o estado atual de cada avaliação;
- permite parametrização dos fluxos de análise da Comissão.

# 4.13 Entidade Parecer

Representa o parecer técnico emitido pela Comissão durante a análise de uma Avaliação.

Cada avaliação poderá possuir diferentes versões de pareceres ao longo do processo, preservando o histórico das alterações.

### Atributos

- id
- texto
- conclusão
- versão
- dataEmissao
- assinado
- createdAt
- updatedAt

### Relacionamentos

- pertence a uma Avaliação;
- pertence a um Tipo de Parecer;
- utiliza o resultado produzido pelo Motor de Complexidade;
- poderá possuir múltiplas versões.

### Regras

- somente pareceres não assinados poderão ser alterados;
- a assinatura torna o parecer imutável;
- cada nova emissão incrementa automaticamente a versão;
- toda emissão registra data de emissão.

---

# 4.14 Entidade Comissão

Representa a comissão responsável pela análise das solicitações de RSC.

### Atributos

- id
- nome
- descrição
- dataInicio
- dataFim
- ativa

### Relacionamentos

- possui vários membros;
- participa de várias avaliações;
- é composta por servidores da instituição.

---

# 4.15 Entidade Membro da Comissão

Representa cada servidor designado para compor uma Comissão de RSC.

### Atributos

- id
- papel
- dataInicio
- dataFim
- ativo

### Relacionamentos

- pertence a uma Comissão;
- referencia um Servidor.

### Regras

- Apenas um PRESIDENTE ativo poderá existir em cada comissão.
- Um servidor poderá participar de diferentes comissões ao longo do tempo.

---

---

# 4.16 Entidade Avaliação

Representa o processo de análise de uma Solicitação realizado por uma Comissão de RSC.

Cada solicitação protocolada poderá originar uma Avaliação, responsável por controlar todo o fluxo administrativo de análise, desde sua abertura até a emissão do parecer final.

### Atributos

- id
- dataInicio
- dataFim
- observacoes
- createdAt
- updatedAt

### Relacionamentos

- pertence a uma Solicitação;
- pertence a uma Comissão;
- possui um Status da Avaliação;
- registra movimentações no Histórico da Solicitação.

### Regras

- somente solicitações protocoladas poderão iniciar uma avaliação;
- uma solicitação não poderá possuir mais de uma avaliação ativa;
- o início da avaliação altera automaticamente o status da solicitação para **Em Análise**;
- o início da avaliação gera automaticamente um evento no Histórico da Solicitação.

---

# 4.17 Entidade Complementação

Representa pedidos de documentos adicionais.

Atributos:

- id
- motivo
- prazoResposta
- dataSolicitação
- dataResposta

Relacionamentos:

- pertence a uma Solicitação.

---

# 4.18 Entidade Recurso

Representa recurso administrativo apresentado pelo servidor.

Atributos:

- id
- fundamentação
- dataInterposição
- decisão

Relacionamentos:

- pertence a uma Solicitação.

---

# 4.19 Entidade Auditoria

Registra todas as ações realizadas no sistema.

Atributos:

- id
- usuário
- ação
- dataHora
- IP
- descrição

Relacionamentos:

- registra ações de qualquer entidade.

---

# 4.20 Entidade Notificação

Representa mensagens enviadas pelo sistema.

Atributos:

- id
- título
- mensagem
- dataEnvio
- lida

Relacionamentos:

- pertence a um Usuário.

---

# 4.21 Relacionamentos

Servidor

↓

Solicitação

├── Memorial

├── Atividades Declaradas

│     ├── Critério Pretendido

│     └── Documentos

├── Histórico

├── Pontuação

├── Parecer

└── Recurso

Durante todo o fluxo serão registrados:

- Auditoria
- Notificações
- Complementações

---

# 4.22 Evolução do Modelo

O Modelo de Domínio poderá evoluir conforme novas necessidades forem identificadas durante o desenvolvimento do projeto ou em decorrência de alterações na legislação.

Novas entidades poderão ser incorporadas sem comprometer a arquitetura geral da aplicação.

## 4.23 Entidade Grupo de Critérios

Representa os grupos oficiais definidos pelo Decreto nº 13.048/2026 utilizados para organização dos critérios do RSC.

### Atributos

- id
- código
- número romano
- nome
- descrição
- ordem
- ativo

### Relacionamentos

- pertence a uma Legislação;
- possui diversos Critérios;
- participa das Regras de Complexidade dos níveis de RSC.

---

## 4.24 Entidade Regra de Complexidade do Nível

Representa as regras mínimas exigidas para obtenção de cada nível do RSC.

### Atributos

- id
- nível do RSC
- quantidade mínima de itens
- descrição

### Relacionamentos

- pertence a um Nível de RSC;
- possui diversos Grupos de Critérios obrigatórios.

---

## 4.25 Entidade Regra de Complexidade do Grupo

Representa a associação entre um Grupo de Critérios e uma Regra de Complexidade.

### Atributos

- id

### Relacionamentos

- pertence a uma Regra de Complexidade;
- referencia um Grupo de Critérios.

---

Fim do Capítulo 4.

# Capítulo 5 – Modelo de Dados (Modelo Entidade-Relacionamento)

# Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC)

---

# 5.1 Objetivo

Este capítulo apresenta o Modelo de Dados do SG-RSC, responsável por representar como as informações serão armazenadas e relacionadas no banco de dados.

O modelo foi elaborado com base no Modelo de Domínio apresentado no Capítulo 4 e tem como objetivo garantir consistência, integridade, rastreabilidade e desempenho durante o processamento das solicitações de RSC.

A modelagem foi concebida para atender às necessidades atuais do sistema, permitindo evolução futura sem grandes impactos estruturais.

---

# 5.2 Banco de Dados

O SG-RSC utilizará o PostgreSQL como Sistema Gerenciador de Banco de Dados (SGBD).

Principais características:

- Banco relacional.
- Código aberto.
- Alto desempenho.
- Suporte completo a transações.
- Integridade referencial.
- Compatibilidade com Spring Boot e Hibernate.
- Suporte a índices, views, procedures e JSON.

---

# 5.3 Convenções de Modelagem

Para manter a padronização do banco de dados serão adotadas as seguintes convenções:

- nomes das tabelas no singular;
- nomes em português;
- chave primária denominada "id";
- chave estrangeira iniciando por "id_";
- datas utilizando TIMESTAMP;
- UUID poderá ser utilizado futuramente para integração entre sistemas;
- todas as tabelas possuirão auditoria de criação e atualização.

---

# 5.4 Principais Tabelas

O banco será composto inicialmente pelas seguintes tabelas:

- usuario
- servidor
- solicitacao
- memorial
- legislacao
- requisito
- criterio
- atividade_declarada
- atividade_documento
- documento
- tipo_documento
- historico_solicitacao
- pontuacao
- parecer
- comissao
- membro_comissao
- complemento
- recurso
- notificacao
- auditoria
- status_avaliacao
- grupo_criterio
- regra_complexidade_nivel
- regra_complexidade_grupo

---

# 5.5 Relacionamentos

Servidor

1 ---- N Solicitação

Solicitação

1 ---- 1 Memorial

Solicitação

1 ---- N Atividade Declarada

Solicitação

1 ---- N Documento

Critério

1 ---- N Atividade Declarada

Atividade Declarada

N ---- N Documento

Solicitação

1 ---- N Histórico

Solicitação

1 ---- 1 Pontuação

Solicitação

1 ---- N Parecer

Solicitação

1 ---- N Complementação

Solicitação

1 ---- N Recurso

Usuário

1 ---- N Notificação

Usuário

1 ---- N Auditoria

Status da Avaliação

1 ---- N Avaliação

Legislação

1 ---- N Grupo de Critério

Grupo de Critério

1 ---- N Critério

Nível RSC

1 ---- N Regra de Complexidade

Regra de Complexidade

N ---- N Grupo de Critério

---

# 5.6 Cardinalidades

| Origem | Destino | Cardinalidade |
|---------|----------|---------------|
| Servidor | Solicitação | 1:N |
| Solicitação | Memorial | 1:1 |
| Solicitação | Atividade Declarada | 1:N |
| Solicitação | Documento | 1:N |
| Critério | Atividade Declarada | 1:N |
| Atividade Declarada | Documento | N:N |
| Solicitação | Histórico | 1:N |
| Solicitação | Pontuação | 1:1 |
| Solicitação | Parecer | 1:N |
| Solicitação | Complementação | 1:N |
| Solicitação | Recurso | 1:N |
| Usuário | Auditoria | 1:N |
| Usuário | Notificação | 1:N |

---

# 5.7 Chaves Primárias

Todas as tabelas possuirão chave primária numérica gerada automaticamente.

Exemplo:

id BIGSERIAL

ou

id BIGINT GENERATED ALWAYS AS IDENTITY

A definição será realizada conforme as melhores práticas do PostgreSQL.

---

# 5.8 Integridade Referencial

Todas as relações utilizarão Foreign Keys.

Exemplos:

- Solicitação somente poderá existir se houver Servidor.
- Memorial somente poderá existir se houver Solicitação.
- Atividade Declarada somente poderá existir se houver Solicitação.
- Documento somente poderá existir se houver Solicitação.
- Uma Atividade Declarada poderá estar associada a um Critério existente.
- O vínculo entre Atividade Declarada e Documento somente poderá ser criado para registros válidos.
- Parecer somente poderá existir se houver Solicitação.
- Pontuação somente poderá existir se houver Solicitação.
- Todo Membro da Comissão deverá estar vinculado a uma Comissão existente.
- Todo Membro da Comissão deverá referenciar um Servidor existente.
- Cada Comissão poderá possuir apenas um PRESIDENTE ativo.

---

# 5.9 Exclusão de Registros

Nenhum registro será removido fisicamente do banco de dados, salvo situações excepcionais previstas em procedimentos administrativos.

Será adotado o conceito de exclusão lógica (Soft Delete), utilizando campo de controle.

Exemplo:

ativo

ou

data_exclusao

Essa abordagem preserva a rastreabilidade e facilita auditorias.

---

# 5.10 Auditoria

As principais tabelas possuirão os seguintes campos:

- data_criacao
- usuario_criacao
- data_atualizacao
- usuario_atualizacao

Além disso, todas as operações relevantes serão registradas na tabela de auditoria.

---

# 5.11 Índices

Serão criados índices para melhorar o desempenho das consultas.

Exemplos:

- matrícula do servidor;
- CPF;
- número da solicitação;
- status da solicitação;
- data de protocolo;
- tipo de documento.

---

# 5.12 Versionamento

Documentos como Memorial, Parecer e Recurso poderão possuir controle de versão.

O objetivo é manter histórico completo das alterações realizadas.

---

# 5.13 Escalabilidade

O modelo foi concebido para permitir expansão futura, possibilitando:

- novos tipos de critérios;
- novos fluxos de aprovação;
- novos perfis de usuários;
- integração com sistemas institucionais;
- suporte a múltiplas Instituições Federais de Ensino.

---

# 5.14 Modelo Conceitual

Fluxo simplificado das principais entidades:

Usuário
│
├── Servidor
│      │
│      ├── Solicitação
│      │        │
│      │        ├── Memorial
│      │        ├── Atividade Declarada
│      │        │        ├── Critério Pretendido
│      │        │        └── Documento(s)
│      │        ├── Documento
│      │        ├── Histórico
│      │        ├── Pontuação
│      │        ├── Parecer
│      │        ├── Complementação
│      │        └── Recurso
│
├── Notificação
│
└── Auditoria

---

# 5.15 Considerações Finais

O Modelo de Dados foi projetado para oferecer flexibilidade, integridade e facilidade de manutenção.

As entidades apresentadas neste capítulo servirão como base para a implementação das entidades JPA, repositórios, serviços e APIs do backend.

Alterações futuras deverão preservar a compatibilidade com as regras de negócio definidas nos capítulos anteriores.

---

Fim do Capítulo 5.

# Capítulo 6 – Modelo de Avaliação e Motor de Regras do RSC

# Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC)

---

# 6.1 Objetivo

Este capítulo define o modelo utilizado pelo SG-RSC para representar, calcular e validar os critérios do Reconhecimento de Saberes e Competências (RSC-PCCTAE).

O objetivo é permitir que as regras estabelecidas pela legislação sejam configuráveis, reduzindo a necessidade de alterações no código-fonte em caso de mudanças futuras.

---

# 6.2 Princípios

O modelo de avaliação foi concebido seguindo os seguintes princípios:

- parametrização;
- flexibilidade;
- rastreabilidade;
- transparência;
- auditabilidade;
- independência da legislação.

---

# 6.3 Estrutura do Modelo

A avaliação do Reconhecimento de Saberes e Competências será organizada em cinco níveis:

Nível 1

Requisito

↓

Nível 2

Critério

↓

Nível 3

Atividade Declarada

↓

Nível 4

Documento Comprobatório

↓

Nível 5

Avaliação
---

# 6.4 Requisito

Representa cada grupo previsto no Decreto.

Exemplos:

Requisito I

Participação em grupos de trabalho.

Requisito II

Projetos institucionais.

...

Requisito VI

Produção científica.

Cada requisito possuirá:

- código;
- nome;
- descrição;
- ativo.

---

# 6.5 Critério

Representa cada item dos anexos.

Exemplo:

Item 2

Coordenação de grupo de trabalho.

Pontuação

4,5 pontos.

Cada critério possuirá:

- código;
- requisito;
- descrição;
- pontuação;
- unidade de medida;
- ativo.

---

# 6.6 Documento Comprobatório

Os documentos representam as evidências utilizadas para comprovar as atividades declaradas pelo servidor.

Cada documento é armazenado fisicamente no MinIO e possui seus metadados persistidos no PostgreSQL.

Um documento poderá ser associado a uma ou mais Atividades Declaradas, permitindo sua reutilização quando representar evidência para diferentes atividades relacionadas ao processo de avaliação.

Cada documento armazenará, entre outras informações:

- nome original;
- nome do arquivo armazenado;
- tipo MIME;
- tamanho do arquivo;
- data de envio;
- tipo de documento;
- status do documento.

A associação entre Atividade Declarada e Documento é realizada por meio de uma entidade de vínculo, preservando a integridade referencial e permitindo a gestão independente dos documentos e das atividades.

---

# 6.6.1 Atividade Declarada

A Atividade Declarada representa cada experiência profissional, acadêmica, administrativa ou técnica informada pelo servidor como evidência para obtenção do Reconhecimento de Saberes e Competências.

Cada atividade poderá ser associada, opcionalmente, a um Critério Pretendido da Base Legal, permitindo orientar a análise da Comissão durante o processo de avaliação.

Além disso, uma atividade poderá possuir um ou mais documentos comprobatórios vinculados, demonstrando as evidências apresentadas pelo servidor.

Essa modelagem permite separar claramente a descrição da atividade das evidências documentais, proporcionando maior flexibilidade e rastreabilidade durante o processo de avaliação.

# 6.7 Avaliação

Representa a análise realizada pela Comissão.

Para cada critério será registrado:

- situação;
- pontuação concedida;
- justificativa;
- avaliador;
- data da avaliação.

---

# 6.8 Motor de Cálculo

O cálculo da pontuação será realizado automaticamente pelo sistema com base nas atividades declaradas, nos critérios parametrizados e nas evidências documentais apresentadas pelo servidor.

O motor de avaliação deverá:

- identificar os critérios associados às atividades declaradas;
- verificar a existência de documentação comprobatória;
- calcular automaticamente a pontuação obtida;
- identificar critérios pendentes;
- validar requisitos obrigatórios;
- calcular a pontuação total da solicitação;
- verificar a elegibilidade ao nível de RSC pretendido.

Embora o cálculo automático ainda não esteja implementado, toda a estrutura necessária para sua parametrização já foi incorporada ao modelo de domínio e ao banco de dados.
---

# 6.9 Parametrização

Todos os critérios deverão ser cadastrados pelo administrador.

Não haverá pontuação fixa no código Java.

Toda pontuação será obtida do banco de dados.

---

# 6.10 Benefícios

Esse modelo permitirá:

- adaptação a novas legislações;
- alteração de pontuações sem recompilar o sistema;
- criação de novos critérios;
- reutilização por outras instituições.

---
---

# 6.11 Base Legal

O SG-RSC deverá possuir um módulo de gestão da Base Legal, permitindo o cadastro e a manutenção das normas que fundamentam o processo de concessão do Reconhecimento de Saberes e Competências (RSC-PCCTAE).

A modelagem da Base Legal tem como objetivo garantir que todos os critérios de avaliação estejam vinculados à legislação correspondente, proporcionando maior transparência, rastreabilidade e facilidade de manutenção quando houver alterações normativas.

Inicialmente, o sistema deverá contemplar as seguintes estruturas:

- Lei;
- Decreto;
- Artigo;
- Parágrafo;
- Inciso;
- Alínea;
- Anexo;
- Requisito;
- Critério;
- Pontuação.

Cada critério cadastrado deverá possuir sua fundamentação legal associada, permitindo que o sistema informe ao servidor e à comissão exatamente qual dispositivo legal embasa a concessão da pontuação.

Exemplo de fundamentação:

> Lei nº 11.091/2005  
> Decreto nº 13.048/2026  
> Anexo VI – Produção, Prospecção e Difusão de Conhecimento Científico ou Técnico  
> Item 10 – Autoria ou coautoria de capítulo de livro, artigo publicado em revista especializada, jornal científico ou periódico relacionado aos interesses institucionais.  
> Pontuação: 7,5 pontos.

A modelagem da Base Legal permitirá que futuras alterações na legislação sejam realizadas por meio de parametrizações no sistema, reduzindo a necessidade de alterações no código-fonte e aumentando a vida útil da aplicação.

Além disso, todas as avaliações realizadas pela Comissão deverão permanecer vinculadas à respectiva fundamentação legal, garantindo rastreabilidade, transparência e segurança jurídica para os processos administrativos.

## Status da Implementação

A primeira versão do módulo **Base Legal** foi implementada no backend do SG-RSC e atualmente está integrada ao módulo de Atividades Declaradas.

Nesta etapa foram desenvolvidas as seguintes entidades de domínio:

- Legislação;
- Requisito;
- Critério;
- Atividade Declarada;
- Vínculo entre Atividade Declarada e Documento.

O módulo foi implementado seguindo a arquitetura Feature-First, contemplando:

- entidades JPA;
- repositórios;
- serviços;
- controllers REST;
- DTOs de requisição e resposta;
- mapeadores (Mapper Pattern);
- exclusão lógica (Soft Delete);
- migrações Flyway para criação das estruturas e carga inicial dos dados;
- associação entre atividades e critérios pretendidos;
- associação entre atividades e documentos comprobatórios;
- testes funcionais completos dos endpoints REST.

### Evolução Prevista

A próxima etapa do desenvolvimento consistirá na implementação do motor de cálculo da pontuação, que utilizará as atividades declaradas, os critérios parametrizados e os documentos comprobatórios para calcular automaticamente a pontuação obtida pelo servidor, respeitando as regras estabelecidas pelo Decreto nº 13.048/2026.

## 6.12 Infraestrutura da Comissão de Avaliação

Foi implementado o módulo responsável pela administração das Comissões de Avaliação do RSC.

Nesta etapa foram desenvolvidas:

- cadastro de Comissões;
- cadastro de Membros da Comissão;
- integração com o cadastro de Servidores;
- validação de presidente único por comissão;
- controle de vigência dos membros;
- consultas paginadas;
- filtros por situação e termo de pesquisa.

Essa estrutura servirá de base para o módulo de Avaliação, no qual cada análise será obrigatoriamente realizada por membros previamente designados em uma comissão.

---

## 6.13 Implementação do Fluxo Inicial de Avaliação

Foi implementada a primeira etapa do fluxo administrativo de avaliação das solicitações de RSC.

Nesta etapa o sistema realiza automaticamente:

- validação do status da solicitação;
- criação da Avaliação;
- associação da Comissão responsável;
- associação do Status da Avaliação;
- alteração automática da Solicitação para o status **Em Análise**;
- registro da data de início da avaliação;
- criação automática do evento **AVALIACAO_INICIADA** no Histórico da Solicitação.

Também foram implementados:

- paginação das avaliações;
- filtros por Comissão;
- filtros por Status da Avaliação;
- consulta por identificador;
- validações para impedir múltiplas avaliações simultâneas para uma mesma solicitação.

Todos os endpoints foram validados por meio de testes funcionais utilizando requisições HTTP via `curl`, contemplando cenários de sucesso e de validação das regras de negócio.

Essa infraestrutura servirá de base para os próximos módulos de:

- cálculo da pontuação;
- parecer técnico;
- homologação da avaliação;
- deferimento e indeferimento;
- recursos administrativos.

## 6.14 Evolução da Base Legal Oficial do RSC-PCCTAE

Com a implementação das migrações **Flyway V13, V14 e V15**, o SG-RSC passou a utilizar integralmente a estrutura oficial prevista no Decreto nº 13.048/2026 para representação dos critérios de avaliação do Reconhecimento de Saberes e Competências.

A Base Legal deixou de utilizar uma estrutura simplificada de critérios e passou a ser composta por entidades específicas capazes de representar fielmente a organização prevista na legislação.

Foram implementadas as seguintes estruturas:

- Grupos de Critérios;
- Critérios Oficiais;
- Regras de Complexidade por Nível de RSC;
- Associação entre Grupos e Níveis de RSC;
- Tipos parametrizados de cálculo da pontuação.

### Grupo de Critérios

Os critérios passaram a ser organizados em seis grupos oficiais:

| Código | Grupo |
|--------|-------|
| GRUPO_I | Participação em Grupos de Trabalho, Comissões, Comitês, Núcleos, Representações ou Similares |
| GRUPO_II | Participação e Atuação em Projetos Institucionais |
| GRUPO_III | Recebimento de Premiação por Projetos Implementados |
| GRUPO_IV | Designação para Responsabilidades Técnico-Administrativas ou Especializadas |
| GRUPO_V | Exercício de Função, Cargo de Direção ou Assessoramento |
| GRUPO_VI | Produção, Prospecção e Difusão de Conhecimento Científico ou Técnico |

### Critérios Oficiais

A migração V15 realizou a carga completa dos critérios previstos na legislação, totalizando:

| Grupo | Quantidade |
|--------|-----------:|
| Grupo I | 10 |
| Grupo II | 11 |
| Grupo III | 3 |
| Grupo IV | 8 |
| Grupo V | 8 |
| Grupo VI | 19 |
| **Total** | **59 critérios** |

Os critérios simplificados utilizados durante o desenvolvimento inicial permaneceram registrados apenas para fins históricos, porém foram automaticamente inativados.

### Regras de Complexidade

Foi criada uma estrutura parametrizável para representar as exigências mínimas de cada nível do RSC.

Essa estrutura é composta pelas entidades:

- RegraComplexidadeNivel;
- RegraComplexidadeGrupo.

Essas entidades permitem que o sistema valide automaticamente:

- quantidade mínima de grupos atendidos;
- quantidade mínima de itens por grupo;
- elegibilidade ao nível pretendido.

Toda a lógica permanece parametrizada no banco de dados, eliminando regras fixas no código-fonte.

### Parametrização do Motor de Regras

Os critérios passaram a armazenar informações adicionais utilizadas pelo futuro motor de cálculo, incluindo:

- ordem de apresentação;
- grupo de critérios;
- tipo de cálculo;
- observações;
- pontuação parametrizada.

Essa estrutura permitirá que futuras alterações legislativas sejam implementadas exclusivamente por parametrização no banco de dados, preservando a arquitetura desacoplada do sistema.

### 6.15 Implementação do Motor de Pontuação

Foi implementada a primeira versão funcional do Motor de Pontuação do SG-RSC.

Nesta etapa o sistema passou a calcular automaticamente a pontuação das atividades declaradas utilizando exclusivamente informações parametrizadas na Base Legal oficial do Decreto nº 13.048/2026.

O cálculo utiliza:

- Critério Oficial;
- Atividade Declarada;
- Quantidade Declarada;
- Pontuação Unitária cadastrada;
- Avaliação da Comissão.

O sistema realiza automaticamente:

- cálculo da pontuação declarada;
- persistência da pontuação calculada;
- impedimento de duplicidade de pontuação para a mesma atividade na mesma avaliação;
- homologação integral;
- homologação parcial;
- validação da quantidade homologada;
- registro da justificativa da comissão.

### Estrutura implementada

Foram desenvolvidos os seguintes componentes:

- Migração Flyway **V16__prepare_scoring_engine.sql**;
- atualização da entidade **AtividadeDeclarada**;
- atualização da entidade **Pontuacao**;
- **PontuacaoRepository**;
- DTOs específicos para cálculo e homologação;
- **PontuacaoMapper**;
- **PontuacaoService**;
- **PontuacaoController**;
- validações das regras de negócio;
- integração com a Base Legal oficial;
- tratamento padronizado de exceções utilizando **GlobalExceptionHandler**.

### Endpoints REST

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| **POST** | `/api/pontuacoes/calcular` | Calcula automaticamente a pontuação de uma atividade declarada. |
| **GET** | `/api/pontuacoes/{id}` | Consulta uma pontuação específica. |
| **GET** | `/api/pontuacoes/avaliacao/{id}` | Lista todas as pontuações de uma avaliação. |
| **PUT** | `/api/pontuacoes/{id}/homologar` | Homologa integral ou parcialmente uma pontuação calculada. |

### Regras de Negócio Implementadas

- cálculo automático utilizando a pontuação parametrizada do Critério;
- validação da existência da Avaliação;
- validação da existência da Atividade Declarada;
- validação da existência do Critério;
- impedimento de duplicidade de pontuação para a mesma atividade;
- homologação integral da pontuação;
- homologação parcial da pontuação;
- validação da quantidade homologada;
- validação da pontuação homologada;
- registro da justificativa da comissão;
- atualização do status da pontuação.

### Testes Realizados

O módulo foi validado por meio de testes funcionais utilizando requisições HTTP via `curl`, contemplando os seguintes cenários:

- criação de Solicitação;
- cadastro de Atividade Declarada;
- envio de Documento;
- criação do Memorial;
- protocolização da Solicitação;
- início da Avaliação;
- cálculo automático da Pontuação;
- consulta individual da Pontuação;
- listagem das Pontuações por Avaliação;
- homologação integral;
- homologação parcial;
- tentativa de criação de pontuação duplicada;
- tentativa de homologação com quantidade superior à declarada;
- validação das regras de negócio e das mensagens de erro retornadas pela API.

A implementação desta etapa representa a primeira versão operacional do Motor de Pontuação do SG-RSC, permitindo que a Comissão realize o cálculo e a homologação das atividades declaradas com base na Base Legal oficial parametrizada no sistema. Essa infraestrutura servirá como fundamento para as próximas funcionalidades relacionadas ao parecer técnico, consolidação da pontuação por grupo de critérios, validação automática das regras de complexidade dos níveis de RSC e decisão final da Comissão.

# 6.16 Implementação do Motor de Complexidade e Elegibilidade

Foi implementada a primeira versão do **Motor de Complexidade** do SG-RSC, responsável por validar automaticamente se uma solicitação atende aos requisitos mínimos definidos para o nível de RSC pretendido.

Diferentemente do Motor de Pontuação, cuja responsabilidade é calcular e homologar a pontuação de cada atividade declarada, o Motor de Complexidade consolida os resultados da avaliação e verifica o cumprimento das regras parametrizadas na Base Legal.

Nesta etapa o sistema passou a realizar automaticamente:

- consolidação das pontuações homologadas por Grupo de Critérios;
- cálculo da pontuação total homologada;
- cálculo da pontuação total declarada;
- consolidação da quantidade de itens homologados;
- consolidação da quantidade de grupos atendidos;
- validação da pontuação mínima exigida para o nível pretendido;
- validação da quantidade mínima de itens exigida;
- validação das regras de complexidade parametrizadas no banco de dados;
- cálculo automático da elegibilidade ao nível de RSC.

## Arquitetura Implementada

Foram desenvolvidos os seguintes componentes:

- `ComplexidadeEngine`;
- `ComplexidadeService`;
- `ComplexidadeController`;
- `PontuacaoRepository` (consultas de consolidação);
- `RegraComplexidadeNivelRepository`;
- `RegraComplexidadeGrupoRepository`;
- DTOs específicos para consolidação e resultado da elegibilidade.

## Endpoints REST

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| **GET** | `/api/complexidade/avaliacao/{id}/resultado` | Calcula e retorna automaticamente o resultado completo da elegibilidade da avaliação. |

## Validações Implementadas

O Motor de Complexidade realiza automaticamente:

- consolidação por Grupo de Critérios;
- consolidação dos totais da avaliação;
- validação da pontuação mínima;
- validação da quantidade mínima de itens;
- validação das regras parametrizadas por nível de RSC;
- cálculo da elegibilidade final;
- identificação dos grupos que atenderam às regras.

## Estrutura Parametrizada

Toda a lógica permanece parametrizada por meio das entidades:

- `RegraComplexidadeNivel`;
- `RegraComplexidadeGrupo`;
- `GrupoCriterio`;
- `NivelRSC`.

Nenhuma regra de elegibilidade permanece fixa no código-fonte.

## Testes Realizados

O módulo foi validado por meio de:

- testes unitários utilizando **JUnit 5**;
- validação funcional dos endpoints REST utilizando `curl`;
- cenários positivos e negativos para diferentes níveis de RSC;
- verificação da consolidação por grupo;
- verificação da consolidação dos totais da avaliação;
- validação automática da elegibilidade.

Foram implementados **11 testes unitários**, todos executados com sucesso.

Essa implementação conclui a infraestrutura responsável pela validação automática da elegibilidade das solicitações de RSC, permitindo que futuras etapas, como emissão de parecer técnico, deferimento, indeferimento e recursos administrativos, utilizem diretamente o resultado consolidado produzido pelo Motor de Complexidade.

# 6.17 Implementação do Motor de Parecer Técnico

Foi implementado o módulo responsável pela emissão dos Pareceres Técnicos da Comissão de RSC.

O Motor de Parecer utiliza diretamente o resultado consolidado produzido pelo Motor de Complexidade para gerar automaticamente uma fundamentação técnica, reduzindo o trabalho manual da Comissão e garantindo padronização das análises.

## Funcionalidades Implementadas

- geração automática da fundamentação;
- geração automática da conclusão sugerida;
- emissão de pareceres;
- controle de versões;
- consulta individual;
- listagem de pareceres por avaliação;
- edição de pareceres não assinados;
- assinatura lógica do parecer;
- bloqueio de alterações após assinatura.

## Arquitetura Implementada

Foram desenvolvidos os seguintes componentes:

- ParecerTecnicoEngine;
- ParecerTecnicoService;
- ParecerTecnicoController;
- ParecerRepository;
- TipoParecerRepository;
- ParecerMapper;
- DTOs de emissão, atualização, resposta e sugestão.

## Endpoints REST

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| GET | `/api/pareceres/avaliacao/{id}/sugestao` | Gera automaticamente uma sugestão de parecer. |
| POST | `/api/pareceres/avaliacao/{id}/emitir` | Emite um novo parecer técnico. |
| PUT | `/api/pareceres/{id}` | Atualiza um parecer ainda não assinado. |
| POST | `/api/pareceres/{id}/assinar` | Realiza a assinatura lógica do parecer. |
| GET | `/api/pareceres/{id}` | Consulta um parecer específico. |
| GET | `/api/pareceres/avaliacao/{id}` | Lista todos os pareceres da avaliação. |

## Regras de Negócio Implementadas

- utilização obrigatória do resultado do Motor de Complexidade;
- geração automática da fundamentação técnica;
- geração automática da conclusão sugerida;
- incremento automático da versão do parecer;
- impedimento de edição após assinatura;
- impedimento de nova assinatura de parecer já assinado.

## Testes Realizados

O módulo foi validado por meio de:

- testes unitários do ParecerTecnicoEngine;
- validação funcional completa utilizando `curl`;
- emissão de parecer;
- atualização do parecer;
- assinatura;
- tentativa de alteração após assinatura;
- tentativa de nova assinatura;
- consulta individual;
- listagem por avaliação.

Foram executados com sucesso todos os testes automatizados do projeto, totalizando 16 testes unitários, além da validação funcional dos endpoints REST.

Esta implementação conclui o fluxo técnico de avaliação, permitindo que a Comissão produza pareceres fundamentados automaticamente a partir do Motor de Complexidade, preservando o histórico de versões e garantindo a integridade das decisões por meio da assinatura lógica.
---

Fim do Capítulo 6.

# Capítulo 7 – Arquitetura do Sistema

# Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC)

---

# 7.1 Objetivo

Este capítulo apresenta a arquitetura geral do SG-RSC, definindo como os componentes da aplicação serão organizados, como se comunicarão entre si e quais tecnologias serão utilizadas.

A arquitetura foi projetada para priorizar simplicidade, escalabilidade, manutenibilidade e reutilização, permitindo a evolução contínua do sistema.

---

# 7.2 Visão Geral

O SG-RSC será desenvolvido como uma aplicação Web utilizando arquitetura cliente-servidor.

A solução será composta pelos seguintes componentes:

- Front-end Web;
- API REST;
- Banco de Dados PostgreSQL;
- Armazenamento de Arquivos (MinIO);
- Serviço de Autenticação;
- Serviço de Auditoria;
- Serviço de Notificações.

---

# 7.3 Arquitetura Geral

                    Navegador

                        │

                Angular 17 SPA

                        │

        Login (OAuth2 / OpenID Connect)

                        │

                   Keycloak

                        │

              Access Token (JWT)

                        │

          Spring Boot 3.5 API

        (OAuth2 Resource Server)

                        │

                 Spring Data JPA

                        │

                  PostgreSQL 16

# 7.4 Tecnologias

## Front-end

- Angular 17
- TypeScript
- HTML5
- CSS3
- Angular Material

---

## Back-end

- Java 25
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

---

## Banco

- PostgreSQL

---

## Documentação

- OpenAPI (Swagger)

---

## Testes

- JUnit 5
- Mockito

---

## Versionamento

- Git
- GitLab

---

## Containerização

- Docker

---
# 7.5 Armazenamento de Documentos

O SG-RSC adota uma estratégia de armazenamento híbrido para os documentos enviados pelos usuários.

Os arquivos físicos são armazenados no **MinIO**, um serviço de Object Storage compatível com a API Amazon S3, enquanto os metadados permanecem armazenados no banco de dados PostgreSQL.

Essa abordagem evita o armazenamento de arquivos binários diretamente no banco de dados relacional, reduzindo seu crescimento e melhorando o desempenho das consultas.

Os metadados registrados no PostgreSQL incluem informações necessárias para localização, auditoria e gerenciamento dos documentos, tais como:

- nome original do arquivo;
- nome do arquivo armazenado;
- tipo MIME;
- tamanho do arquivo;
- data de envio;
- solicitação vinculada;
- tipo de documento.

O armazenamento físico e os metadados são utilizados de forma complementar, permitindo que o sistema localize e disponibilize os arquivos de forma transparente aos usuários.

Servidor

↓

Upload do Documento

↓

DocumentoController

↓

DocumentoService

↓

MinioFileStorageService

├── Armazena o arquivo no MinIO

└── Persiste os metadados no PostgreSQL

↓

Documento disponível para consulta e download

# 7.6 API REST – Módulo de Documentos

O módulo de documentos disponibiliza endpoints REST para upload, consulta, download e exclusão lógica dos documentos vinculados às solicitações.

Os endpoints seguem os princípios REST e utilizam autenticação baseada em OAuth2/OpenID Connect, com validação de tokens JWT realizada pelo Spring Security.

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| **POST** | `/api/documentos` | Realiza o upload de um documento e registra seus metadados no PostgreSQL. |
| **GET** | `/api/documentos/solicitacao/{id}` | Lista os documentos vinculados a uma solicitação. |
| **GET** | `/api/documentos/{id}/download` | Realiza o download do arquivo armazenado no MinIO. |
| **DELETE** | `/api/documentos/{id}` | Realiza a exclusão lógica do documento. |

### Observações

- O upload de documentos utiliza requisições `multipart/form-data`.
- Os arquivos são armazenados fisicamente no MinIO.
- Os metadados dos documentos são persistidos no PostgreSQL.
- A autenticação e autorização são realizadas por meio de tokens JWT emitidos pelo Keycloak.
- A documentação interativa da API está disponível por meio do OpenAPI (Swagger).

# 7.6.1 API REST – Módulo de Solicitações

O módulo de solicitações é responsável pelo gerenciamento do ciclo de vida das solicitações de RSC.

Atualmente disponibiliza operações para:

- criação de solicitações;
- consulta por identificador;
- listagem de solicitações;
- atualização;
- protocolização.

Durante a protocolização o sistema:

- valida a existência de documentos obrigatórios;
- gera automaticamente um número de protocolo;
- altera o status da solicitação;
- registra a data de protocolização;
- cria automaticamente um registro no histórico da solicitação.

Os endpoints retornam DTOs específicos, evitando a exposição direta das entidades JPA e reduzindo o acoplamento entre a API e o modelo de persistência.

# 7.6.2 Histórico das Solicitações

Cada movimentação relevante da solicitação é registrada no módulo de histórico.

Inicialmente foi implementado o evento:

- SOLICITACAO_PROTOCOLADA

Esse registro armazena:

- tipo do evento;
- descrição;
- usuário responsável;
- data do evento;
- solicitação relacionada.

Essa estrutura permitirá registrar futuramente novas movimentações, como:

- envio para análise;
- solicitação de complementação;
- deferimento;
- indeferimento;
- recurso;
- decisão final.

# 7.6.3 Tratamento Global de Exceções

Com o crescimento do número de módulos do SG-RSC, tornou-se necessário padronizar o tratamento das exceções lançadas pela aplicação.

Para evitar duplicação de código nos controllers e garantir respostas consistentes aos consumidores da API, foi adotado um mecanismo centralizado de tratamento de erros utilizando o recurso **`@RestControllerAdvice`** do Spring Framework.

Toda exceção lançada pelas camadas de serviço (**Service Layer**) passa a ser interceptada pelo **GlobalExceptionHandler**, responsável por converter exceções Java em respostas HTTP padronizadas.

Atualmente são tratadas as seguintes categorias de exceções:

- **ResourceNotFoundException**: utilizada quando um recurso solicitado não é encontrado.
- **BusinessException**: utilizada para representar violações de regras de negócio.
- **MethodArgumentNotValidException**: utilizada para tratar erros de validação dos DTOs anotados com Bean Validation.
- **Exception**: tratamento genérico para exceções inesperadas.

Todas as respostas de erro seguem uma estrutura única, representada pela classe **ApiErrorResponse**, contendo as seguintes informações:

| Campo | Descrição |
|--------|-----------|
| `timestamp` | Data e hora da ocorrência do erro. |
| `status` | Código HTTP retornado pela API. |
| `error` | Descrição textual do código HTTP. |
| `message` | Mensagem detalhando o motivo do erro. |
| `path` | Endpoint da requisição que originou a exceção. |

### Exemplo de resposta para recurso inexistente

```json
{
  "timestamp": "2026-07-31T18:41:04.400945253",
  "status": 404,
  "error": "Not Found",
  "message": "Status da avaliação não encontrado.",
  "path": "/api/status-avaliacoes/99999"
}
```

### Exemplo de resposta para erro de validação

```json
{
  "timestamp": "2026-07-31T18:46:25.546376232",
  "status": 400,
  "error": "Bad Request",
  "message": "nome: não pode ficar em branco; codigo: não pode ficar em branco",
  "path": "/api/status-avaliacoes"
}
```

A adoção de um tratamento global de exceções proporciona diversos benefícios para a arquitetura da aplicação:

- padronização das respostas de erro da API;
- redução da duplicação de código nos controllers;
- separação das responsabilidades entre regras de negócio e tratamento de erros;
- maior facilidade de manutenção e evolução da aplicação;
- integração simplificada com o frontend Angular;
- melhoria da experiência do usuário ao fornecer mensagens de erro claras e consistentes.

Essa abordagem também facilita a inclusão de novos tipos de exceções específicas do domínio do SG-RSC, mantendo o comportamento uniforme da API e preservando a arquitetura **Feature-First** adotada pelo projeto.

# 7.6.4 Paginação e Filtros dos Endpoints REST

Com o crescimento do volume de dados manipulados pelo SG-RSC, foi adotada uma estratégia padronizada de paginação, ordenação e filtragem dos endpoints REST utilizando os recursos nativos do Spring Data.

Os endpoints de consulta passaram a aceitar parâmetros de paginação, ordenação e filtros, permitindo consultas mais eficientes e reduzindo a quantidade de dados trafegados entre o backend e o frontend.

## Parâmetros suportados

| Parâmetro | Descrição |
|-----------|-----------|
| `page` | Número da página (iniciando em 0). |
| `size` | Quantidade de registros por página. |
| `sort` | Campo utilizado para ordenação (`campo,direção`). |
| `termo` | Texto utilizado na pesquisa por código ou nome. |
| `ativo` | Filtra registros ativos ou inativos. |

### Exemplo

```http
GET /api/status-avaliacoes?page=0&size=10&sort=nome,asc
```

```http
GET /api/status-avaliacoes?termo=analise
```

```http
GET /api/status-avaliacoes?ativo=true
```

```http
GET /api/status-avaliacoes?termo=analise&ativo=true
```

## Resposta Padronizada

Os endpoints paginados retornam uma estrutura padronizada denominada `PageResponse`, composta pelos seguintes atributos:

- `content`
- `page`
- `size`
- `totalElements`
- `totalPages`
- `first`
- `last`

Exemplo:

```json
{
  "content": [
    {
      "id": 1,
      "codigo": "EM_ANDAMENTO",
      "nome": "Em Andamento"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

A utilização dessa estrutura padroniza o consumo da API pelo frontend Angular e facilita a adoção da mesma estratégia em todos os módulos do sistema.

# 7.6.5 API REST – Módulo Comissão

O módulo Comissão é responsável pelo gerenciamento das comissões avaliadoras e da composição de seus membros.

### Endpoints

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| POST | /api/comissoes | Criar comissão |
| GET | /api/comissoes | Listar comissões |
| GET | /api/comissoes/{id} | Consultar comissão |
| PUT | /api/comissoes/{id} | Atualizar comissão |
| DELETE | /api/comissoes/{id} | Exclusão lógica |
| POST | /api/comissoes/{id}/membros | Adicionar membro |
| GET | /api/comissoes/{id}/membros | Listar membros |
| PUT | /api/membros-comissao/{id} | Atualizar membro |
| DELETE | /api/membros-comissao/{id} | Exclusão lógica do membro |

### Regras Implementadas

- Apenas um presidente ativo por comissão.
- Integração obrigatória com o cadastro de servidores.
- Validação do período de vigência.
- Exclusão lógica.
- Paginação e filtros nas consultas.

# 7.6.6 API REST – Módulo Avaliação

O módulo Avaliação é responsável por iniciar e controlar o fluxo administrativo de análise das solicitações protocoladas.

Durante o início da avaliação o sistema realiza automaticamente:

- criação da Avaliação;
- associação da Comissão responsável;
- definição do Status da Avaliação;
- alteração do status da Solicitação para **Em Análise**;
- registro da data de início;
- criação automática do evento **AVALIACAO_INICIADA** no Histórico da Solicitação.

## Endpoints

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| POST | `/api/avaliacoes/iniciar` | Inicia uma avaliação. |
| GET | `/api/avaliacoes` | Lista avaliações com paginação e filtros. |
| GET | `/api/avaliacoes/{id}` | Consulta uma avaliação pelo identificador. |

## Filtros Disponíveis

- comissão;
- status da avaliação;
- paginação;
- ordenação.

## Regras Implementadas

- somente solicitações protocoladas poderão iniciar avaliação;
- uma solicitação não poderá possuir mais de uma avaliação ativa;
- integração automática com o Histórico da Solicitação;
- alteração automática do status da Solicitação para **Em Análise**;
- validação da existência da Comissão e do Status da Avaliação.

Todos os endpoints do módulo foram validados por meio de testes funcionais utilizando `curl`, confirmando o correto funcionamento do fluxo administrativo e das validações implementadas.

# 7.6.7 Infraestrutura da Base Legal Oficial

A Base Legal do SG-RSC foi evoluída para representar integralmente a estrutura oficial prevista no Decreto nº 13.048/2026.

As migrações Flyway V13, V14 e V15 implementaram:

- modelagem completa da Base Legal;
- cadastro dos seis grupos oficiais;
- parametrização dos seis níveis de RSC;
- regras de complexidade;
- carga oficial dos 59 critérios do Decreto;
- desativação dos critérios simplificados utilizados durante o desenvolvimento inicial.

Essa infraestrutura constitui a base do futuro motor de cálculo da pontuação, permitindo que todas as regras de elegibilidade permaneçam parametrizadas no banco de dados e independentes do código Java.

---

# 7.7 Organização do Backend

```text
src/main/java
└── br.gov.ife.sgrsc
    ├── config
    │   └── MinioConfig
    │
    ├── features
    │   ├── documento
    │   │   ├── controller
    │   │   ├── dto
    │   │   ├── entity
    │   │   ├── repository
    │   │   └── service
    │   ├── health
    │   ├── servidor
    │   ├── situacaofuncional
    │   ├── solicitacao
    │   └── resultadosolicitacao
    │
    ├── security
    │
    └── shared
        └── storage
            ├── FileStorageService
            └── MinioFileStorageService
```
O backend do SG-RSC adota a arquitetura Feature-First, organizando o código por funcionalidades de negócio. Cada módulo concentra seus próprios componentes (controllers, services, repositories, DTOs e entidades), reduzindo o acoplamento entre funcionalidades e facilitando a manutenção. Componentes compartilhados, como configurações e serviços de infraestrutura, permanecem centralizados nos pacotes config e shared.

# 7.8 Organização do Front-end

```

```
src/app

core

shared

features

auth

dashboard

usuarios

solicitacoes

documentos

pontuacao

parecer

recurso

relatorios

auditoria

configuracoes

---

# 7.9 Padrões Arquiteturais

O desenvolvimento seguirá os seguintes padrões:

- Arquitetura em Camadas;
- Feature-First Architecture;
- REST;
- SOLID;
- Clean Code;
- Repository Pattern;
- DTO Pattern;
- Service Layer;
- Dependency Injection;
- Separation of Concerns.

---

# 7.10 Segurança

O SG-RSC utilizará o Keycloak como provedor de identidade e autenticação.

A autenticação será baseada nos padrões OAuth2 e OpenID Connect (OIDC). Após a autenticação do usuário, o Keycloak emitirá um Access Token no formato JWT.

A API Spring Boot atuará como OAuth2 Resource Server, sendo responsável apenas pela validação do token e autorização das requisições.

O controle de acesso será realizado por meio de papéis (RBAC), permitindo restringir funcionalidades conforme o perfil do usuário.

Todos os endpoints protegidos exigirão autenticação.

As permissões serão controladas por perfis de acesso.

---

# 7.11 Perfis

O sistema possuirá inicialmente os seguintes perfis:

- Servidor;
- Comissão;
- DGP;
- Administrador.

Novos perfis poderão ser adicionados futuramente.
Os perfis serão gerenciados centralizadamente pelo Keycloak e sincronizados com a aplicação conforme necessário.

---

# 7.12 Auditoria

Todas as operações críticas deverão ser registradas.

Exemplos:

- Login;
- Criação de Solicitação;
- Alteração de Dados;
- Emissão de Parecer;
- Julgamento de Recurso;
- Parametrizações.

---

# 7.13 Escalabilidade

A arquitetura foi concebida para permitir futuras integrações com:

- SIGRH;
- SIPAC;
- SEI;
- Gov.br;
- Sistemas de autenticação institucional.

A utilização do Keycloak permitirá futura integração com Active Directory (LDAP), autenticação institucional e Single Sign-On (SSO), reduzindo o esforço de manutenção de credenciais e facilitando a integração com outros sistemas corporativos.

---

# 7.14 Princípios Arquiteturais

Durante todo o desenvolvimento serão observados os seguintes princípios:

- simplicidade;
- modularidade;
- reutilização;
- baixo acoplamento;
- alta coesão;
- segurança;
- rastreabilidade;
- conformidade legal;
- facilidade de manutenção;
- escalabilidade.

---

# 7.15 Considerações Finais

A arquitetura apresentada neste capítulo estabelece a base técnica para o desenvolvimento do SG-RSC.

As decisões arquiteturais aqui definidas deverão orientar todas as etapas de implementação, testes, implantação e evolução do sistema, garantindo consistência e qualidade ao longo do ciclo de vida do projeto.

---

Fim do Capítulo 7.

# Capítulo 8 – Decisões Arquiteturais (Architecture Decision Records - ADR)

---

# 8.1 Objetivo

Este capítulo registra as principais decisões arquiteturais adotadas durante o desenvolvimento do SG-RSC.

Cada decisão documenta o contexto em que foi tomada, as alternativas consideradas, a solução escolhida e seus impactos na arquitetura da aplicação.

O objetivo é preservar o histórico das decisões técnicas, facilitar a manutenção do projeto e apoiar futuras evoluções da solução.

As ADRs deverão ser atualizadas sempre que uma decisão arquitetural relevante for tomada.

---

# 8.2 ADR-001 – Adoção da Arquitetura Feature-First

## Status

Aceita.

## Contexto

O SG-RSC possui diversas funcionalidades independentes, como Servidor, Solicitação, Critérios, Pareceres, Documentos e Relatórios. Organizar o projeto apenas por camadas (controller, service, repository, etc.) tende a aumentar o acoplamento entre módulos e dificultar a manutenção conforme a aplicação evolui.

## Decisão

Adotar a arquitetura **Feature-First**, organizando o código por funcionalidades. Cada funcionalidade reúne seus próprios controllers, services, repositories, DTOs e demais componentes.

## Consequências

### Benefícios

- Melhor organização do código.
- Baixo acoplamento entre funcionalidades.
- Facilidade de manutenção.
- Escalabilidade da aplicação.
- Maior produtividade no desenvolvimento.

### Desvantagens

- Exige disciplina na organização dos pacotes.
- Pode gerar duplicação de componentes utilitários quando mal utilizada.

---

# 8.3 ADR-002 – Utilização do PostgreSQL

## Status

Aceita.

## Contexto

O sistema necessita de um banco de dados relacional robusto, confiável e compatível com o ecossistema Spring Boot.

## Decisão

Adotar o PostgreSQL como Sistema Gerenciador de Banco de Dados (SGBD).

## Consequências

### Benefícios

- Software livre.
- Excelente desempenho.
- Suporte completo a transações.
- Alta compatibilidade com Hibernate e Spring Data JPA.
- Grande comunidade.

### Desvantagens

- Exige instalação e administração do banco.
- Curva de aprendizado para recursos avançados.

---

# 8.4 ADR-003 – Utilização do Flyway

## Status

Aceita.

## Contexto

O banco de dados evoluirá continuamente durante o desenvolvimento.

É necessário manter o histórico das alterações estruturais.

## Decisão

Utilizar o Flyway para versionamento e migração automática do banco de dados.

## Consequências

### Benefícios

- Versionamento do banco.
- Reprodutibilidade dos ambientes.
- Facilidade de implantação.
- Histórico completo das alterações.

### Desvantagens

- Necessidade de disciplina na criação das migrações.
- Alterações incorretas podem impactar ambientes compartilhados.

---

# 8.5 ADR-004 – Adoção do Keycloak como Provedor de Identidade

## Status

Aceita.

## Contexto

O SG-RSC necessita autenticar usuários com segurança, evitando implementar um mecanismo próprio para gerenciamento de senhas, sessões e tokens.

## Decisão

Adotar o **Keycloak** como provedor de identidade da aplicação.

A autenticação será baseada nos protocolos OAuth2 e OpenID Connect (OIDC).

O Spring Boot atuará como **OAuth2 Resource Server**, responsável apenas pela validação dos tokens JWT emitidos pelo Keycloak.

## Consequências

### Benefícios

- Centralização da autenticação.
- Single Sign-On (SSO).
- Integração futura com LDAP e Active Directory.
- Gerenciamento de usuários e perfis.
- Recuperação de senha.
- Menor responsabilidade da aplicação sobre credenciais.

### Desvantagens

- Introdução de um novo serviço na infraestrutura.
- Necessidade de configuração e administração do Keycloak.

---

# 8.6 ADR-005 – Exclusão Lógica (Soft Delete)

## Status

Aceita.

## Contexto

O SG-RSC manipula processos administrativos que devem permanecer auditáveis mesmo após sua descontinuação.

## Decisão

Adotar exclusão lógica (Soft Delete), preservando os registros no banco de dados.

## Consequências

### Benefícios

- Preservação do histórico.
- Atendimento às necessidades de auditoria.
- Recuperação de registros.

### Desvantagens

- Consultas precisam considerar registros inativos.
- Crescimento do banco ao longo do tempo.

---

# 8.7 ADR-006 – Auditoria das Entidades

## Status

Aceita.

## Contexto

Toda movimentação do processo administrativo deve ser rastreável.

## Decisão

Implementar auditoria automática nas entidades do sistema, registrando criação, alteração e exclusão lógica dos registros.

## Consequências

### Benefícios

- Rastreabilidade.
- Transparência.
- Atendimento às auditorias.
- Facilidade de investigação.

### Desvantagens

- Pequeno aumento no volume de armazenamento.
- Maior complexidade na implementação.

---

# 8.8 ADR-007 – Utilização do Java 25 LTS

## Status

Aceita.

## Contexto

O projeto será desenvolvido utilizando uma versão moderna da plataforma Java, visando maior desempenho e acesso aos recursos mais recentes da linguagem.

## Decisão

Adotar o Java 25 LTS como linguagem oficial do backend.

## Consequências

### Benefícios

- Longo período de suporte.
- Melhor desempenho.
- Recursos modernos da linguagem.
- Compatibilidade com Spring Boot.

### Desvantagens

- Pode limitar a execução em ambientes legados.
- Exige atualização das ferramentas de desenvolvimento.

---

# 8.9 ADR-008 – Utilização do Docker e Docker Compose

## Status

Aceita.

## Contexto

É necessário padronizar o ambiente de desenvolvimento e facilitar a implantação da aplicação.

## Decisão

Utilizar Docker para containerização dos serviços e Docker Compose para orquestração dos ambientes locais.

## Consequências

### Benefícios

- Ambientes padronizados.
- Facilidade de instalação.
- Reprodutibilidade.
- Redução de problemas de configuração.

### Desvantagens

- Necessidade de conhecimento em Docker.
- Consumo adicional de recursos da máquina.

---

# 8.11 ADR-009 – Utilização do MinIO para Armazenamento de Documentos

## Status

Aceita.

## Contexto

O SG-RSC necessita armazenar documentos enviados pelos usuários durante o processo de solicitação do Reconhecimento de Saberes e Competências (RSC-PCCTAE).

O armazenamento desses arquivos diretamente no banco de dados relacional aumentaria seu volume, impactaria o desempenho das consultas e dificultaria a escalabilidade da aplicação.

Além disso, a solução deve permitir futura migração para ambientes em nuvem e manter compatibilidade com tecnologias amplamente adotadas pelo mercado.

## Decisão

Adotar o **MinIO** como serviço de armazenamento de objetos (Object Storage), compatível com a API Amazon S3.

Os arquivos enviados pelos usuários serão armazenados fisicamente no MinIO, enquanto seus metadados permanecerão persistidos no PostgreSQL.

A comunicação entre a aplicação e o serviço de armazenamento será realizada por meio do SDK oficial Java do MinIO.

## Consequências

### Benefícios

- Separação entre dados relacionais e arquivos binários.
- Redução do crescimento do banco de dados.
- Melhor desempenho para armazenamento e recuperação de documentos.
- Arquitetura compatível com a API Amazon S3.
- Facilidade de substituição futura por outros provedores compatíveis.
- Maior escalabilidade para armazenamento de documentos.

### Desvantagens

- Introdução de um novo serviço na infraestrutura da aplicação.
- Necessidade de gerenciamento do serviço de armazenamento e dos buckets.
- Dependência da disponibilidade do MinIO para operações de upload e download de documentos.

# 8.12 ADR-010 – Implementação da Base Legal Parametrizável

## Status

Aceita.

## Contexto

O Decreto nº 13.048/2026 define critérios e requisitos que podem sofrer alterações ao longo do tempo. Manter essas regras diretamente no código-fonte aumentaria o custo de manutenção e reduziria a flexibilidade da aplicação.

## Decisão

Implementar um módulo denominado **Base Legal**, composto pelas entidades **Legislação**, **Requisito** e **Critério**, permitindo que a fundamentação normativa e os critérios de avaliação sejam parametrizados por meio do banco de dados.

A implementação segue a arquitetura Feature-First, utilizando DTOs específicos para entrada e saída, Mapper Pattern, Spring Data JPA e exclusão lógica (Soft Delete).

## Consequências

### Benefícios

- Parametrização da legislação.
- Maior flexibilidade para alterações normativas.
- Redução da necessidade de mudanças no código.
- Maior rastreabilidade das regras de negócio.
- Reutilização da estrutura em futuras versões do sistema.

### Desvantagens

- Necessidade de manutenção da base legal cadastrada.
- Maior dependência da consistência dos dados parametrizados.

# 8.13 ADR-011 – Modelagem da Comissão de Avaliação

## Status

Aceita.

## Contexto

As Comissões responsáveis pela avaliação do RSC possuem composição variável ao longo do tempo e seus membros são designados por ato administrativo.

Era necessário permitir a manutenção dessas informações sem alterações no código-fonte.

## Decisão

Modelar Comissão e Membro da Comissão como entidades independentes, vinculadas ao cadastro de Servidores.

Foi adotada a validação de apenas um PRESIDENTE ativo por comissão.

## Consequências

### Benefícios

- Maior flexibilidade.
- Histórico das composições.
- Preparação para o módulo Avaliação.
- Reutilização das comissões.
- Melhor rastreabilidade.

### Desvantagens

- Maior número de entidades.
- Necessidade de validações adicionais.

# 8.10 Considerações Finais

As decisões arquiteturais registradas neste capítulo representam o estado atual da arquitetura do SG-RSC.

Novas ADRs poderão ser incorporadas ao longo da evolução do projeto para documentar alterações relevantes na arquitetura, infraestrutura, padrões de desenvolvimento e tecnologias adotadas.

A manutenção deste histórico contribuirá para a rastreabilidade das decisões técnicas, facilitará a integração de novos desenvolvedores ao projeto e servirá como referência para futuras evoluções da aplicação.

# ADR-011 – Modelagem de Atividades Declaradas

## Status

Aceita

## Contexto

O processo de solicitação do RSC exige que o servidor informe as atividades realizadas e apresente documentos comprobatórios.

Uma mesma atividade pode possuir vários documentos e um mesmo documento pode ser utilizado para comprovar mais de uma atividade.

Era necessário definir uma modelagem que permitisse reutilização de documentos sem duplicação de dados.

## Decisão

Foi criada a entidade **Atividade Declarada** como parte do domínio da aplicação.

Os documentos permanecem pertencendo à Solicitação.

A relação entre Atividade Declarada e Documento foi modelada como **N:N**, utilizando a entidade intermediária `atividade_documento`.

A remoção do vínculo entre atividade e documento não remove o documento armazenado, apenas a associação.

## Consequências

### Benefícios

- reutilização de documentos;
- eliminação de duplicidade;
- maior flexibilidade para futuras regras de negócio;
- melhor aderência ao domínio do RSC;
- maior rastreabilidade das evidências.

### Impactos

- criação da tabela `atividade_documento`;
- implementação dos endpoints de associação;
- atualização do modelo de domínio;
- atualização das migrações Flyway.

# ADR-012 – Tratamento Global de Exceções

## Status

Aceita

## Contexto

À medida que novos módulos do SG-RSC foram sendo implementados, cada serviço passou a lançar exceções específicas relacionadas às regras de negócio e à inexistência de recursos.

O tratamento dessas exceções diretamente nos controllers geraria duplicação de código, inconsistência nas respostas da API REST e maior dificuldade de manutenção.

Além disso, o frontend Angular necessita receber respostas padronizadas para facilitar a exibição de mensagens ao usuário.

## Decisão

Centralizar o tratamento de exceções utilizando uma classe anotada com @RestControllerAdvice.

Foi criado um modelo único de resposta (ApiErrorResponse) contendo:

- timestamp;
- status HTTP;
- descrição do erro;
- mensagem;
- caminho da requisição.

Também foram criadas exceções específicas para representar erros de domínio da aplicação:

- BusinessException;
- ResourceNotFoundException.

As validações do Bean Validation passaram a ser tratadas centralizadamente, retornando mensagens amigáveis ao cliente da API.

## Consequências

### Benefícios

- Padronização das respostas da API.
- Redução de código duplicado.
- Facilidade para manutenção.
- Melhor integração com o frontend Angular.
- Centralização das regras de tratamento de erros.
- Maior legibilidade do código.

### Desvantagens

- Necessidade de criação de exceções específicas para novas regras de negócio.
- Pequeno aumento da quantidade de classes compartilhadas.

---

Fim do Capítulo 8.

# Capítulo 9 – Status da Implementação

## Visão Geral

Este capítulo registra a evolução incremental do desenvolvimento do SG-RSC, permitindo acompanhar as funcionalidades implementadas ao longo das sprints do projeto.

## Sprint 1

- Estrutura inicial do backend.
- Configuração do Spring Boot.
- Configuração do PostgreSQL.
- Estrutura Feature-First.

## Sprint 2

- Configuração do Spring Security.
- Integração com Keycloak.
- Endpoint Health.

## Sprint 3

- Implementação do módulo de documentos.
- Integração com MinIO.
- Upload de arquivos.
- Download de arquivos.
- Exclusão lógica de documentos.

## Sprint 4

- Implementação do módulo Base Legal.
- CRUD de Legislação.
- CRUD de Requisito.
- CRUD de Critério.
- DTOs Request/Response.
- Mapper Pattern.
- Soft Delete.
- Testes completos dos endpoints REST.

## Sprint 5

- Implementação do módulo de Solicitações.
- CRUD de Solicitações.
- Geração automática de protocolo.
- Validação de documentos obrigatórios.
- Integração com o módulo de Documentos.
- Registro automático do histórico de protocolização.
- Migração Flyway para tipos de histórico.
- DTOs Request/Response.
- Mapper Pattern.
- Testes completos do fluxo de protocolização.

## Sprint 6

- Implementação do módulo Memorial.
- CRUD completo do Memorial.
- Associação do Memorial à Solicitação.
- Controle de versão do Memorial.
- Validação de edição apenas para solicitações em rascunho.
- Exclusão lógica.
- DTOs específicos para criação e atualização.
- Consulta de memorial por identificador.
- Consulta de memorial por solicitação.
- Testes funcionais dos endpoints REST.

## Sprint 7

- Implementação do módulo de Atividades Declaradas.
- CRUD completo de Atividades Declaradas.
- Associação opcional entre Atividade Declarada e Critério Pretendido.
- Associação N:N entre Atividades Declaradas e Documentos.
- Criação da tabela de vínculo `atividade_documento`.
- Migração Flyway V9 para criação das tabelas do módulo.
- Migração Flyway V10 para carga inicial da Base Legal (Legislação, Requisitos e Critérios).
- Ajustes na configuração de segurança para os novos endpoints REST.
- DTOs Request/Response específicos.
- Mapper Pattern.
- Exclusão lógica (Soft Delete).
- Testes funcionais completos dos endpoints REST.
- Validação do fluxo completo de:
  - criação da atividade;
  - consulta por identificador;
  - consulta por solicitação;
  - atualização da atividade;
  - exclusão lógica;
  - upload de documento;
  - associação entre atividade e documento;
  - remoção do vínculo preservando o documento armazenado.

  ## Sprint 8

- Implementação do módulo Status da Avaliação.
- CRUD completo de Status da Avaliação.
- Criação da entidade StatusAvaliacao.
- Migração Flyway V11 para criação da tabela e carga inicial dos status padrão.
- Associação entre Avaliação e Status da Avaliação.
- Implementação de Repository, DTOs Request/Response/Summary, Mapper, Service e Controller REST.
- Ajustes na configuração do Spring Security para disponibilização dos endpoints.
- Testes funcionais completos utilizando curl (GET, POST, PUT e DELETE).
- Validação da integração entre PostgreSQL, Flyway, JPA, Spring Security e API REST.

## Sprint 9

- Implementação do tratamento global de exceções da API REST.
- Criação do `GlobalExceptionHandler`.
- Criação das exceções `BusinessException` e `ResourceNotFoundException`.
- Padronização das respostas de erro da API.
- Padronização das mensagens de validação dos DTOs.
- Implementação de paginação utilizando Spring Data `Pageable`.
- Implementação de filtros por termo e situação.
- Criação da classe `PageResponse` para respostas paginadas.
- Aplicação da paginação e filtros no módulo **Status da Avaliação**, estabelecendo o padrão para os demais módulos do sistema.