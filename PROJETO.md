# Sistema de Gestão do Reconhecimento de Saberes e Competências (RSC-PCCTAE) SG-RSC

# Controle de Versões

| Versão | Data | Autor | Alterações |
|---------|------|--------|------------|
| 1.0 | 03/07/2026 | Erik Barbosa | Criação do documento |


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

- Front-end Angular;
- API REST em Spring Boot;
- Banco PostgreSQL;
- Serviço de armazenamento de documentos;
- Serviço de autenticação;
- Serviço de geração de relatórios.

Essa separação permitirá evolução independente dos componentes.

# Decisões Arquiteturais

Este projeto adotará:

- Java 25;
- Spring Boot 3;
- Angular 17;
- PostgreSQL;
- Maven;
- Git;
- Docker;
- API REST;
- JSON;
- JWT para autenticação;
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

Enviar documentação comprobatória.

### Fluxo

Selecionar categoria.

Selecionar arquivo.

Enviar.

Sistema valida formato.

Sistema registra auditoria.

---

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

Objetivo

Enviar oficialmente o processo.

Validações

Memorial obrigatório.

Documentos obrigatórios.

Pontuação calculada.

Critérios mínimos.

Ao finalizar

Gerar número.

Gerar protocolo.

Registrar auditoria.

Enviar confirmação.

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

## UC010 – Emitir Parecer

Ator

Comissão.

Fluxo

Abrir solicitação.

Registrar fundamentação.

Informar decisão.

Assinar eletronicamente.

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
| UC003 | Anexar Documentos | RN102 | Documentos | Documento | POST /documentos | Upload de Documentos | TS003 | Planejado |
| UC004 | Gerar Memorial | RN101 | Memorial | Memorial | POST /memorial | Memorial | TS004 | Planejado |
| UC005 | Protocolar Solicitação | RN101, RN102, RN103, RN104 | Solicitações | Solicitação | POST /solicitacoes/protocolar | Protocolar Solicitação | TS005 | Planejado |
| UC006 | Consultar Processo | RN005 | Solicitações | Solicitação | GET /solicitacoes/{id} | Consulta da Solicitação | TS006 | Planejado |
| UC007 | Solicitar Complementação | RN107 | Comissão | Complementação | POST /complementacoes | Solicitar Complementação | TS007 | Planejado |
| UC008 | Responder Complementação | RN107 | Comissão | Complementação | POST /complementacoes/responder | Responder Complementação | TS008 | Planejado |
| UC009 | Calcular Pontuação | RN103, RN104, RN105 | Pontuação | Pontuação | POST /pontuacao/calcular | Cálculo da Pontuação | TS009 | Planejado |
| UC010 | Emitir Parecer | RN107 | Comissão | Parecer | POST /pareceres | Parecer Técnico | TS010 | Planejado |
| UC011 | Interpor Recurso | RN005 | Recursos | Recurso | POST /recursos | Recurso Administrativo | TS011 | Planejado |
| UC012 | Julgar Recurso | RN005 | Recursos | Recurso | POST /recursos/julgar | Julgamento do Recurso | TS012 | Planejado |
| UC013 | Gerenciar Critérios | RN104 | Administração | Critério | POST /criterios | Cadastro de Critérios | TS013 | Planejado |
| UC014 | Emitir Relatórios | RN005 | Relatórios | Relatório | GET /relatorios | Relatórios Gerenciais | TS014 | Planejado |
| UC015 | Consultar Auditoria | RN005 | Auditoria | Auditoria | GET /auditoria | Auditoria | TS015 | Planejado |

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

• Usuário

• Servidor

• Solicitação

• Memorial

• Documento

• Critério

• Pontuação

• Parecer

• Comissão

• Complementação

• Recurso

• Auditoria

• Notificação

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

# 4.8 Entidade Documento

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

# 4.9 Entidade Critério

Representa um critério previsto no Decreto nº 13.048/2026.

Atributos:

- id
- código
- descrição
- requisito
- pontuação
- ativo

Relacionamentos:

- pode estar vinculado a vários Documentos;
- participa do cálculo da Pontuação.

---

# 4.10 Entidade Pontuação

Representa o resultado da avaliação dos critérios.

Atributos:

- id
- pontosCalculados
- pontosHomologados
- dataCálculo

Relacionamentos:

- pertence a uma Solicitação.

---

# 4.11 Entidade Parecer

Representa a decisão emitida pela Comissão.

Atributos:

- id
- parecer
- decisão
- dataParecer

Relacionamentos:

- pertence a uma Solicitação;
- é emitido por um membro da Comissão.

---

# 4.12 Entidade Comissão

Representa a comissão responsável pela análise.

Atributos:

- id
- nome
- dataCriação
- status

Relacionamentos:

- possui vários membros;
- analisa várias Solicitações.

---

# 4.13 Entidade Complementação

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

# 4.14 Entidade Recurso

Representa recurso administrativo apresentado pelo servidor.

Atributos:

- id
- fundamentação
- dataInterposição
- decisão

Relacionamentos:

- pertence a uma Solicitação.

---

# 4.15 Entidade Auditoria

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

# 4.16 Entidade Notificação

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

# 4.17 Relacionamentos

Servidor

↓

Solicitação

↓

Memorial

↓

Documento

↓

Critério

↓

Pontuação

↓

Parecer

↓

Recurso

Durante todo o fluxo serão registrados:

- Auditoria

- Notificações

- Complementações

---

# 4.18 Evolução do Modelo

O Modelo de Domínio poderá evoluir conforme novas necessidades forem identificadas durante o desenvolvimento do projeto ou em decorrência de alterações na legislação.

Novas entidades poderão ser incorporadas sem comprometer a arquitetura geral da aplicação.

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
- documento
- criterio
- documento_criterio
- pontuacao
- parecer
- comissao
- membro_comissao
- complemento
- recurso
- notificacao
- auditoria

---

# 5.5 Relacionamentos

Servidor

1 ---- N Solicitação

Solicitação

1 ---- 1 Memorial

Solicitação

1 ---- N Documento

Documento

N ---- N Critério

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

Comissão

1 ---- N Membros

Comissão

1 ---- N Solicitações

---

# 5.6 Cardinalidades

| Origem | Destino | Cardinalidade |
|---------|----------|---------------|
| Servidor | Solicitação | 1:N |
| Solicitação | Memorial | 1:1 |
| Solicitação | Documento | 1:N |
| Documento | Critério | N:N |
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

Solicitação somente poderá existir se houver Servidor.

Documento somente poderá existir se houver Solicitação.

Parecer somente poderá existir se houver Solicitação.

Pontuação somente poderá existir se houver Solicitação.

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
│      │        ├── Documento
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

A avaliação será organizada em quatro níveis:

Nível 1

Requisito

↓

Nível 2

Critério

↓

Nível 3

Documento

↓

Nível 4

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

Cada critério poderá possuir um ou mais documentos comprobatórios.

Exemplos:

Portaria.

Certificado.

Declaração.

Publicação.

Relatório.

Manual.

---

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

O sistema calculará automaticamente:

- pontuação total;
- critérios atendidos;
- critérios pendentes;
- requisitos obrigatórios;
- elegibilidade ao nível pretendido.

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
- Banco de Dados;
- Armazenamento de Arquivos;
- Serviço de Autenticação;
- Serviço de Auditoria;
- Serviço de Notificações.

---

# 7.3 Arquitetura Geral

```

```
+----------------------------------------------------+
|                    Navegador                       |
|                (Angular 17)                        |
+-------------------------▲--------------------------+
                          │
                     HTTPS / JSON
                          │
+-------------------------▼--------------------------+
|             API REST (Spring Boot 3)              |
|----------------------------------------------------|
| Controllers                                        |
| Services                                           |
| Regras de Negócio                                  |
| Motor de Avaliação do RSC                          |
| Auditoria                                          |
+-------------------------▲--------------------------+
                          │
                          │ JPA / Hibernate
                          ▼
+----------------------------------------------------+
|                  PostgreSQL                        |
+----------------------------------------------------+

                          │
                          ▼

+----------------------------------------------------+
|      Armazenamento de Documentos                   |
+----------------------------------------------------+
```

---

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

# 7.5 Organização do Backend

```

```
src/main/java

br.gov.ife.sgrsc

├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
├── service
├── specification
├── util
└── validation
```

---

# 7.6 Organização do Front-end

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
```

---

# 7.7 Padrões Arquiteturais

O desenvolvimento seguirá os seguintes padrões:

- Arquitetura em Camadas;
- REST;
- SOLID;
- Clean Code;
- Repository Pattern;
- DTO Pattern;
- Service Layer;
- Dependency Injection;
- Separation of Concerns.

---

# 7.8 Segurança

O sistema utilizará autenticação baseada em JWT.

Todos os endpoints protegidos exigirão autenticação.

As permissões serão controladas por perfis de acesso.

---

# 7.9 Perfis

O sistema possuirá inicialmente os seguintes perfis:

- Servidor;
- Comissão;
- DGP;
- Administrador.

Novos perfis poderão ser adicionados futuramente.

---

# 7.10 Auditoria

Todas as operações críticas deverão ser registradas.

Exemplos:

- Login;
- Criação de Solicitação;
- Alteração de Dados;
- Emissão de Parecer;
- Julgamento de Recurso;
- Parametrizações.

---

# 7.11 Escalabilidade

A arquitetura foi concebida para permitir futuras integrações com:

- SIGRH;
- SIPAC;
- SEI;
- Gov.br;
- Sistemas de autenticação institucional.

---

# 7.12 Princípios Arquiteturais

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

# 7.13 Considerações Finais

A arquitetura apresentada neste capítulo estabelece a base técnica para o desenvolvimento do SG-RSC.

As decisões arquiteturais aqui definidas deverão orientar todas as etapas de implementação, testes, implantação e evolução do sistema, garantindo consistência e qualidade ao longo do ciclo de vida do projeto.

---

Fim do Capítulo 7.