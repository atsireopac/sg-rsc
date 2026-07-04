# Modelo de Domínio do SG-RSC

# Sistema de Gestão do Reconhecimento de Saberes e Competências

Versão: 1.0

---

# 1. Objetivo

Este documento define o Modelo de Domínio do Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC).

Seu propósito é identificar, organizar e descrever todos os elementos de negócio envolvidos no processo de solicitação, avaliação, concessão e acompanhamento do Reconhecimento de Saberes e Competências (RSC), conforme previsto na legislação vigente.

O Modelo de Domínio servirá como principal referência para:

- modelagem do banco de dados;
- implementação das entidades Java;
- definição das APIs REST;
- desenvolvimento das interfaces do sistema;
- implementação das regras de negócio;
- construção do motor de avaliação;
- integração com sistemas externos.

Este documento não descreve detalhes técnicos de implementação, mas representa o funcionamento do negócio sob a ótica administrativa e funcional.

---

# 2. Visão Geral do Domínio

O SG-RSC é um sistema orientado ao processo administrativo de concessão do Reconhecimento de Saberes e Competências aos servidores técnico-administrativos em educação.

O processo inicia quando um servidor formaliza uma solicitação de RSC e encerra-se somente após a decisão administrativa definitiva, incluindo eventual fase recursal.

Todo o domínio do sistema está organizado em torno da Solicitação de RSC.

A Solicitação representa o processo administrativo principal e funciona como elemento central de integração entre todas as demais entidades do sistema.

Ao redor da Solicitação gravitam diversos elementos do domínio, como:

- servidor requerente;
- documentos comprobatórios;
- memorial descritivo;
- critérios previstos na legislação;
- avaliação da comissão;
- pontuação;
- parecer técnico;
- recurso administrativo;
- notificações;
- histórico do processo.

Essa abordagem permite que o sistema represente fielmente o fluxo administrativo previsto na legislação, mantendo rastreabilidade completa de todas as decisões tomadas durante o processo.

---

# 3. Princípios do Modelo de Domínio

O Modelo de Domínio do SG-RSC deverá seguir os seguintes princípios.

## 3.1 Domínio acima da tecnologia

O sistema será modelado a partir das regras de negócio e do processo administrativo, e não das limitações da tecnologia utilizada.

As entidades existirão porque representam conceitos reais do processo de RSC, e não apenas porque correspondem a tabelas do banco de dados.

---

## 3.2 Responsabilidade Única

Cada entidade deverá possuir uma única responsabilidade dentro do domínio.

Exemplos:

- a Solicitação representa o processo administrativo;
- o Documento representa uma comprovação documental;
- o Parecer representa uma manifestação técnica;
- a Avaliação representa a análise realizada pela comissão.

Evita-se, dessa forma, entidades excessivamente grandes ou com múltiplas responsabilidades.

---

## 3.3 Baixo Acoplamento

As entidades deverão possuir o menor nível possível de dependência entre si.

Sempre que possível, cada módulo deverá conhecer apenas as informações estritamente necessárias para cumprir sua função.

Esse princípio facilitará futuras evoluções do sistema.

---

## 3.4 Alta Coesão

Todas as informações pertencentes a um mesmo conceito deverão permanecer agrupadas.

Por exemplo:

Toda a lógica relacionada à Avaliação permanecerá dentro do módulo de Avaliação.

Toda a lógica relacionada à Legislação permanecerá dentro do módulo de Legislação.

Essa organização reduzirá a complexidade do sistema.

---

## 3.5 Rastreabilidade

Todas as operações realizadas pelo sistema deverão ser passíveis de auditoria.

O sistema deverá ser capaz de responder perguntas como:

- Quem realizou determinada ação?
- Quando ela ocorreu?
- Qual era o estado anterior?
- Qual foi o novo estado?
- Qual dispositivo legal fundamentou a decisão?

Esse princípio orientará tanto o banco de dados quanto o desenvolvimento das APIs.

---

## 3.6 Parametrização

Sempre que possível, regras de negócio deverão ser parametrizadas.

Critérios de pontuação, requisitos, anexos, legislação e demais elementos sujeitos a alterações normativas não deverão ser implementados diretamente no código-fonte.

Essa decisão permitirá que futuras alterações legais sejam realizadas por meio de parametrizações administrativas, reduzindo a necessidade de novas versões da aplicação.

---

## 3.7 Integridade Jurídica

Toda decisão registrada pelo sistema deverá possuir fundamentação legal.

Sempre que houver atribuição de pontuação, indeferimento, deferimento ou emissão de parecer, o sistema deverá registrar a legislação correspondente.

Esse princípio garantirá transparência e segurança jurídica aos processos administrativos.

---

## 3.8 Evolução Contínua

O modelo foi concebido para permitir crescimento contínuo.

Novas legislações, critérios, módulos e integrações poderão ser incorporados sem necessidade de reestruturação completa do sistema.

Essa característica permitirá que o SG-RSC seja reutilizado por diferentes Instituições Federais de Ensino.

---

# 4. Atores do Domínio

Os atores representam pessoas, grupos ou sistemas que interagem diretamente com o SG-RSC durante o ciclo de vida de uma solicitação.

A identificação dos atores é fundamental para compreender as responsabilidades de cada participante e delimitar corretamente as regras de negócio do sistema.

## 4.1 Servidor Requerente

É o servidor ocupante de cargo técnico-administrativo em educação que solicita o Reconhecimento de Saberes e Competências.

Responsabilidades:

- iniciar uma solicitação de RSC;
- informar os dados da solicitação;
- anexar documentos comprobatórios;
- elaborar e enviar o memorial descritivo;
- acompanhar o andamento da solicitação;
- responder diligências;
- interpor recurso administrativo, quando cabível;
- consultar o resultado final.

O servidor é o principal usuário do sistema.

---

## 4.2 Diretoria de Gestão de Pessoas (DGP)

Representa o setor responsável pela gestão administrativa do processo.

Responsabilidades:

- receber solicitações;
- conferir documentação obrigatória;
- realizar análise administrativa inicial;
- encaminhar solicitações para a Comissão Avaliadora;
- acompanhar prazos;
- publicar resultados;
- homologar decisões;
- concluir processos administrativos.

A DGP atua como gestora do fluxo administrativo do sistema.

---

## 4.3 Comissão Avaliadora

Representa a comissão formalmente designada para analisar as solicitações de RSC.

Responsabilidades:

- analisar documentos;
- avaliar critérios previstos na legislação;
- validar comprovações;
- atribuir pontuações;
- emitir pareceres;
- solicitar diligências;
- julgar recursos administrativos.

A Comissão é responsável exclusivamente pela análise técnica da solicitação.

---

## 4.4 Administrador do Sistema

Representa os usuários responsáveis pela administração do SG-RSC.

Responsabilidades:

- cadastrar usuários;
- configurar perfis de acesso;
- parametrizar critérios;
- cadastrar legislação;
- administrar requisitos;
- administrar pontuações;
- acompanhar auditorias;
- gerenciar configurações gerais.

O Administrador não participa das avaliações.

---

## 4.5 Sistemas Externos

O SG-RSC poderá integrar-se futuramente com sistemas institucionais.

Exemplos:

- SIGRH;
- SIPAC;
- SEI;
- Gov.br;
- Sistemas de autenticação institucional.

Esses sistemas poderão fornecer ou consumir informações do SG-RSC por meio de APIs.

---

# 5. Entidades do Domínio

As entidades representam os principais conceitos de negócio do SG-RSC.

Cada entidade possui identidade própria, ciclo de vida e responsabilidades específicas.

O sistema deverá ser modelado a partir dessas entidades, evitando criar estruturas motivadas exclusivamente pela implementação técnica.

---

## 5.1 Solicitação

A Solicitação representa o processo administrativo de concessão do RSC.

Ela constitui a entidade central do domínio.

Todo o fluxo do sistema inicia e termina em uma Solicitação.

Responsabilidades:

- registrar o pedido do servidor;
- controlar o ciclo de vida do processo;
- armazenar informações gerais da solicitação;
- vincular documentos;
- vincular memorial;
- vincular avaliações;
- vincular pareceres;
- vincular recursos;
- controlar o status do processo.

Relacionamentos:

- pertence a um Servidor;
- possui um Memorial;
- possui diversos Documentos;
- possui uma ou mais Avaliações;
- possui um ou mais Pareceres;
- pode possuir Recursos;
- possui Histórico;
- gera Notificações.

---

## 5.2 Servidor

Representa o requerente da solicitação.

Responsabilidades:

- manter seus dados funcionais;
- protocolar solicitações;
- acompanhar análises;
- responder diligências;
- apresentar recursos.

Relacionamentos:

- um Servidor pode possuir várias Solicitações.

---

## 5.3 Documento

Representa qualquer arquivo anexado durante o processo.

Exemplos:

- certificados;
- diplomas;
- declarações;
- portarias;
- artigos científicos;
- livros;
- comprovantes;
- documentos institucionais.

Responsabilidades:

- comprovar critérios;
- registrar origem;
- armazenar metadados;
- permitir validação.

Relacionamentos:

- pertence a uma Solicitação;
- pode comprovar um ou mais Critérios;
- pode ser aceito ou rejeitado pela Comissão.

---

## 5.4 Memorial

Representa o memorial descritivo apresentado pelo servidor.

Responsabilidades:

- registrar a trajetória profissional;
- descrever experiências;
- apresentar justificativas;
- complementar os documentos comprobatórios.

Relacionamentos:

- pertence a uma Solicitação.

---

## 5.5 Avaliação

Representa a análise técnica realizada pela Comissão.

Responsabilidades:

- validar documentos;
- avaliar critérios;
- calcular pontuações;
- consolidar resultados;
- fundamentar decisões.

Relacionamentos:

- pertence a uma Solicitação;
- é realizada por uma Comissão;
- possui diversas Pontuações;
- gera Pareceres.

---

## 5.6 Pontuação

Representa a pontuação atribuída durante uma avaliação.

A Pontuação não pertence diretamente à Solicitação.

Ela pertence à Avaliação.

Essa decisão garante total rastreabilidade do processo de análise.

Responsabilidades:

- registrar pontuação prevista;
- registrar pontuação homologada;
- registrar justificativas;
- registrar observações da comissão.

Relacionamentos:

- pertence a uma Avaliação;
- referencia um Critério.

---

## 5.7 Critério

Representa cada item previsto na legislação que pode gerar pontuação.

Responsabilidades:

- definir requisitos;
- definir unidade de medida;
- definir pontuação máxima;
- indicar documentos aceitos;
- estabelecer regras de validação.

Relacionamentos:

- pertence a um Requisito;
- pode possuir diversas Pontuações.

---

## 5.8 Parecer

O Parecer representa a manifestação formal emitida pela Comissão Avaliadora durante o processo de análise.

O parecer constitui o documento técnico que fundamenta a decisão da comissão quanto ao deferimento ou indeferimento da solicitação.

Responsabilidades:

- registrar a conclusão da avaliação;
- fundamentar tecnicamente a decisão;
- justificar pontuações concedidas ou negadas;
- indicar dispositivos legais utilizados;
- registrar observações da comissão.

Relacionamentos:

- pertence a uma Solicitação;
- pode estar associado a uma Avaliação;
- pode existir mais de um Parecer durante o ciclo de vida da Solicitação.

Exemplos:

- Parecer Preliminar;
- Parecer Final;
- Parecer após Recurso.

---

## 5.9 Recurso

Representa a manifestação apresentada pelo servidor quando discordar do resultado da avaliação.

Responsabilidades:

- registrar a fundamentação do recurso;
- permitir anexação de novos documentos, quando permitido pela legislação;
- controlar prazos;
- registrar o resultado do julgamento.

Relacionamentos:

- pertence a uma Solicitação;
- poderá gerar nova Avaliação;
- poderá gerar novo Parecer.

---

## 5.10 Comissão

Representa a Comissão Avaliadora oficialmente designada pela instituição.

Responsabilidades:

- receber solicitações;
- distribuir avaliações;
- realizar análises técnicas;
- emitir pareceres;
- julgar recursos.

Relacionamentos:

- realiza Avaliações;
- emite Pareceres;
- possui diversos Membros.

Observação:

Os membros da comissão serão modelados em entidade própria, permitindo registrar períodos de atuação, substituições e histórico das designações.

---

## 5.11 Histórico

Representa a linha do tempo completa da Solicitação.

Toda alteração relevante realizada no processo deverá gerar automaticamente um registro de histórico.

Responsabilidades:

- registrar movimentações;
- registrar alterações de status;
- registrar ações realizadas pelos usuários;
- manter rastreabilidade administrativa.

Exemplos de eventos:

- Solicitação criada;
- Documento anexado;
- Solicitação enviada;
- Solicitação distribuída para comissão;
- Avaliação iniciada;
- Parecer emitido;
- Recurso protocolado;
- Processo encerrado.

Relacionamentos:

- pertence a uma Solicitação.

---

## 5.12 Notificação

Representa qualquer comunicação enviada automaticamente pelo sistema aos usuários.

Responsabilidades:

- informar mudanças de status;
- comunicar pendências;
- avisar sobre pareceres;
- avisar sobre recursos;
- comunicar decisões finais.

Exemplos:

- Solicitação protocolada.
- Documento rejeitado.
- Parecer disponível.
- Prazo de recurso iniciado.
- Processo concluído.

Relacionamentos:

- pertence a um usuário do sistema;
- pode estar vinculada a uma Solicitação.

---

## 5.13 Legislação

Representa toda a base normativa utilizada pelo SG-RSC.

A legislação deverá ser parametrizada, permitindo futuras alterações sem necessidade de modificar o código-fonte.

Responsabilidades:

- cadastrar leis;
- cadastrar decretos;
- cadastrar artigos;
- cadastrar anexos;
- cadastrar requisitos;
- cadastrar critérios;
- cadastrar pontuações.

Relacionamentos:

- possui diversos Requisitos;
- fundamenta Critérios;
- fundamenta Avaliações.

---

## 5.14 Requisito

Representa cada grupo de critérios previsto na legislação.

Exemplo:

- Requisito I
- Requisito II
- Requisito III
- Requisito IV
- Requisito V
- Requisito VI

Responsabilidades:

- agrupar critérios;
- definir limites de pontuação;
- organizar a estrutura do decreto.

Relacionamentos:

- pertence a uma Legislação;
- possui diversos Critérios.

---

# 6. Relacionamentos do Domínio

O diagrama conceitual do domínio pode ser representado da seguinte forma:

```text
Servidor
    │
    └──────────────┐
                   │
                   ▼
             Solicitação
                   │
     ┌─────────────┼────────────────────┐
     │             │                    │
     ▼             ▼                    ▼
 Memorial      Documento          Histórico
                                   │
                                   ▼
                              Notificação

                   │
                   ▼
              Avaliação
                   │
     ┌─────────────┼───────────────┐
     ▼             ▼               ▼
 Pontuação     Parecer        Comissão
     │
     ▼
 Critério
     │
     ▼
 Requisito
     │
     ▼
 Legislação

Solicitação
      │
      ▼
   Recurso
      │
      ▼
 Nova Avaliação (quando houver)
```

---

# 6.1 Regras Gerais de Relacionamento

O Modelo de Domínio estabelece as seguintes regras:

- Um Servidor pode possuir diversas Solicitações.
- Cada Solicitação pertence a um único Servidor.
- Uma Solicitação poderá possuir diversos Documentos.
- Uma Solicitação possuirá um Memorial.
- Uma Solicitação poderá possuir diversas Avaliações durante seu ciclo de vida.
- Cada Avaliação poderá gerar diversas Pontuações.
- Cada Pontuação estará vinculada a um único Critério.
- Cada Critério pertence a um único Requisito.
- Cada Requisito pertence a uma Legislação.
- Uma Solicitação poderá possuir diversos Pareceres.
- Uma Solicitação poderá possuir Recursos.
- Todo evento relevante deverá gerar um registro no Histórico.

---

# 7. Agregados do Domínio (DDD)

O SG-RSC adota os princípios do Domain-Driven Design (DDD), organizando as entidades em agregados.

Cada agregado possui uma Raiz (Aggregate Root), responsável por garantir a consistência das regras de negócio.

As demais entidades pertencentes ao agregado somente poderão ser manipuladas por intermédio da sua respectiva raiz.

---

## 7.1 Agregado Solicitação

Raiz do Agregado:

- Solicitação

Entidades pertencentes:

- Memorial
- Documento
- Histórico
- Recurso
- Notificação

Responsabilidades da Raiz:

- controlar o ciclo de vida da solicitação;
- validar mudanças de status;
- registrar movimentações;
- controlar anexos;
- permitir recursos.

A Solicitação representa o processo administrativo completo.

---

## 7.2 Agregado Avaliação

Raiz do Agregado:

- Avaliação

Entidades pertencentes:

- Pontuação
- Parecer

Responsabilidades da Raiz:

- iniciar avaliação;
- validar critérios;
- consolidar pontuação;
- emitir parecer;
- finalizar avaliação.

Toda decisão técnica da Comissão ocorrerá dentro deste agregado.

---

## 7.3 Agregado Legislação

Raiz do Agregado:

- Legislação

Entidades pertencentes:

- Requisito
- Critério

Responsabilidades da Raiz:

- organizar normas;
- manter requisitos;
- manter critérios;
- disponibilizar regras para o motor de avaliação.

---

## 7.4 Agregado Comissão

Raiz do Agregado:

- Comissão

Entidades pertencentes:

- Membro da Comissão
- Designação
- Mandato

Responsabilidades:

- manter composição da comissão;
- distribuir avaliações;
- registrar participação dos membros.

---

# 8. Decisões de Modelagem

As decisões abaixo deverão orientar toda a implementação do SG-RSC.

---

## DM001

A Solicitação será a entidade central do domínio.

Nenhuma entidade do processo administrativo existirá sem estar vinculada a uma Solicitação.

---

## DM002

A Avaliação representa a análise técnica da Comissão.

A Solicitação não calculará pontuações diretamente.

Toda pontuação será produzida pela Avaliação.

---

## DM003

Os Critérios serão totalmente parametrizados.

Nenhum critério previsto na legislação deverá ser implementado diretamente no código-fonte.

---

## DM004

Toda decisão deverá possuir fundamentação legal.

O sistema registrará:

- Lei;
- Decreto;
- Artigo;
- Anexo;
- Requisito;
- Critério.

---

## DM005

Os documentos não armazenarão apenas arquivos.

Também possuirão metadados como:

- tipo;
- data;
- responsável pelo envio;
- situação;
- critérios comprovados;
- parecer da comissão.

---

## DM006

O Histórico será automático.

Toda alteração relevante realizada pelo sistema deverá gerar um evento de auditoria.

Não dependerá da ação manual do usuário.

---

## DM007

Os status da Solicitação serão controlados por máquina de estados.

Exemplo:

Rascunho

↓

Enviada

↓

Em análise documental

↓

Em avaliação

↓

Aguardando parecer

↓

Resultado publicado

↓

Em recurso

↓

Encerrada

Não será permitido alterar livremente o status.

Cada transição deverá obedecer às regras do processo administrativo.

---

## DM008

O sistema será orientado por parametrização.

Sempre que possível:

- regras;
- critérios;
- pontuações;
- legislação;
- requisitos;
- notificações;

deverão ser cadastrados no banco de dados.

---

# 9. Diretrizes Arquiteturais

O Modelo de Domínio deverá orientar todas as camadas da aplicação.

As entidades de domínio não deverão depender de frameworks.

O domínio deverá permanecer independente da tecnologia utilizada.

---

## Organização

Cada módulo possuirá sua própria estrutura.

Exemplo:

features/

solicitacao/

controller/

service/

repository/

entity/

dto/

mapper/

validation/

Essa organização permitirá alta coesão e baixo acoplamento.

---

## Separação entre Domínio e Infraestrutura

Conceitos do negócio permanecerão separados dos conceitos técnicos.

Exemplo:

Domínio

- Servidor
- Solicitação
- Avaliação

Infraestrutura

- Usuário
- JWT
- Spring Security
- Configurações
- Banco
- Cache

---

## Auditoria

Todas as operações deverão ser rastreáveis.

O sistema deverá responder:

- quem fez;
- quando fez;
- o que foi alterado;
- qual era o valor anterior;
- qual é o novo valor;
- qual legislação fundamentou a alteração.

---

## Escalabilidade

O domínio foi concebido para permitir:

- novos decretos;
- novos critérios;
- novos tipos de RSC;
- novas integrações;
- novos módulos.

Sem necessidade de reescrever a arquitetura.

---

# 10. Considerações Finais

O Modelo de Domínio representa a principal referência para o desenvolvimento do SG-RSC.

Toda implementação deverá respeitar os conceitos apresentados neste documento.

Antes da criação de novas entidades, tabelas, APIs ou telas, deverá ser verificado se a solução está coerente com o Modelo de Domínio.

Esse documento deverá evoluir continuamente durante o projeto, acompanhando a evolução das regras de negócio e das alterações na legislação.

---

# Histórico do Documento

| Versão | Data | Descrição |
|--------|------|-----------|
| 1.0 | Julho/2026 | Primeira versão do Modelo de Domínio do SG-RSC. |

---

Fim do Documento.