# Modelo Entidade-Relacionamento (MER)

# SG-RSC

Versão: 1.0

---

# 1. Objetivo

Este documento descreve o Modelo Entidade-Relacionamento (MER) do Sistema de Gestão do Reconhecimento de Saberes e Competências (SG-RSC).

Seu objetivo é definir a estrutura lógica do banco de dados a partir do Modelo de Domínio, garantindo consistência, rastreabilidade, escalabilidade e facilidade de manutenção.

O MER servirá como base para:

- criação das entidades Java;
- implementação das migrações Flyway;
- criação do banco PostgreSQL;
- desenvolvimento das APIs REST.

---

# 2. Diretrizes Gerais

O banco de dados seguirá os seguintes princípios:

- normalização até, no mínimo, a Terceira Forma Normal (3FN);
- uso de chaves primárias simples;
- uso de chaves estrangeiras para relacionamentos;
- auditoria em todas as entidades de negócio;
- parametrização de regras de negócio sempre que possível;
- independência entre domínio e infraestrutura.

---

# 3. Convenções

## 3.1 Tabelas

Todas as tabelas utilizarão nomes em singular e no padrão `snake_case`.

Exemplos:

- servidor
- solicitacao
- documento
- avaliacao

---

## 3.2 Colunas

Todas as colunas seguirão o padrão `snake_case`.

Exemplos:

- data_solicitacao
- numero_processo
- pontuacao_total

---

## 3.3 Chaves Primárias

Todas as entidades utilizarão:

```sql
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY
```

Não serão utilizadas chaves compostas.

---

## 3.4 Auditoria

Todas as entidades deverão possuir os seguintes campos:

```text
created_at
created_by

updated_at
updated_by

deleted_at
deleted_by
```

Esses campos permitirão auditoria e evolução futura para exclusão lógica.

---

# 4. Entidades Persistentes

O banco de dados será composto inicialmente pelas seguintes entidades:

- nivel_rsc
- servidor
- solicitacao
- documento
- avaliacao
- pontuacao
- criterio
- requisito
- legislacao
- parecer
- recurso
- comissao
- historico
- notificacao

As próximas seções detalharão atributos, relacionamentos e restrições de cada entidade.

---

---

# 5. MER Conceitual

O MER Conceitual representa as entidades principais do SG-RSC e seus relacionamentos, sem detalhar ainda os atributos de cada tabela.

## 5.1 Relacionamentos Principais

| Origem | Cardinalidade | Destino |
|--------|---------------|---------|
| Servidor | 1:N | Solicitação |
| Solicitação | 1:N | Documento |
| Solicitação | 1:N | Avaliação |
| Solicitação | 1:N | Parecer |
| Solicitação | 1:N | Recurso |
| Solicitação | 1:N | Histórico |
| Solicitação | 1:N | Notificação |
| Avaliação | 1:N | Pontuação |
| Comissão | 1:N | Avaliação |
| Critério | 1:N | Pontuação |
| Requisito | 1:N | Critério |
| Legislação | 1:N | Requisito |

---

# 6. Regras de Cardinalidade

## 6.1 Servidor e Solicitação

Um servidor poderá possuir várias solicitações.

Cada solicitação pertence obrigatoriamente a um único servidor.

---

## 6.2 Solicitação e Memorial

O Memorial Descritivo não possuirá tabela própria.

Ele será armazenado como um Documento do tipo MEMORIAL.

---

## 6.3 Solicitação e Documento

Uma solicitação poderá possuir vários documentos.

Cada documento pertence a uma única solicitação.

---

## 6.4 Solicitação e Avaliação

Uma solicitação poderá possuir uma ou mais avaliações.

Essa modelagem permite reavaliações, avaliações complementares e avaliações após recurso.

---

## 6.5 Avaliação e Pontuação

Uma avaliação poderá possuir várias pontuações.

Cada pontuação representa a análise de um critério específico.

---

## 6.6 Critério, Requisito e Legislação

Cada critério pertence a um requisito.

Cada requisito pertence a uma legislação.

Essa estrutura permite parametrizar a base legal e reduzir regras fixas no código-fonte.

---

# 7. Próxima Etapa

A próxima etapa será o MER Lógico, no qual serão definidos os atributos, chaves primárias, chaves estrangeiras, índices e restrições de cada entidade.

---

# 8. MER Lógico

O MER Lógico detalha a estrutura de cada entidade persistente do SG-RSC.

Para cada entidade serão definidos:

- atributos;
- tipos de dados;
- obrigatoriedade;
- chaves primárias;
- chaves estrangeiras;
- restrições;
- índices;
- observações de modelagem.

As entidades serão apresentadas na seguinte ordem:

1. Servidor
2. Solicitação
3. Nível RSC
4. Documento
5. Avaliação
6. Pontuação
7. Critério
8. Requisito
9. Legislação
10. Parecer
11. Recurso
12. Comissão
13. Histórico
14. Notificação

## 8.1 Servidor

### Objetivo

Representa o servidor Técnico-Administrativo em Educação que solicita o Reconhecimento de Saberes e Competências (RSC).

Os dados desta entidade identificam o requerente e fornecem as informações funcionais necessárias para abertura e acompanhamento das solicitações.

O SG-RSC não será o sistema mestre dos dados funcionais do servidor, podendo futuramente integrar-se ao SIGRH ou outro sistema corporativo da instituição.

---

### Tabela

**servidor**

---

### Chave Primária

| Campo | Tipo |
|--------|------|
| id | BIGINT |

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| siape | VARCHAR(20) | Sim | Sim | Matrícula SIAPE |
| nome | VARCHAR(200) | Sim | Não | Nome completo |
| cpf | VARCHAR(11) | Sim | Sim | Apenas números |
| email | VARCHAR(200) | Sim | Sim | E-mail institucional |
| cargo | VARCHAR(200) | Sim | Não | Cargo ocupado |
| classe | VARCHAR(50) | Sim | Não | Classe da carreira |
| nivel | VARCHAR(20) | Sim | Não | Nível da carreira |
| padrao | VARCHAR(20) | Sim | Não | Padrão funcional |
| unidade | VARCHAR(200) | Sim | Não | Unidade de lotação |
| campus | VARCHAR(100) | Não | Não | Campus de atuação |
| situacao_funcional_id | BIGINT | Sim | Não | FK para situacao_funcional |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Servidor → Solicitação | 1:N |
| Situação Funcional → Servidor | 1:N |

---

### Índices

| Campo | Tipo |
|--------|------|
| siape | UNIQUE |
| cpf | UNIQUE |
| email | UNIQUE |

---

### Restrições

- SIAPE deve ser único.
- CPF deve ser único.
- E-mail institucional deve ser único.
- Nome é obrigatório.
- O servidor deverá estar vinculado a um cargo válido.

---

### Auditoria

A entidade possuirá os seguintes campos de auditoria:

- created_at
- created_by
- updated_at
- updated_by
- deleted_at
- deleted_by

---

### Observações

Esta entidade representa apenas os dados necessários ao funcionamento do SG-RSC.

Informações adicionais do servidor poderão ser obtidas futuramente por integração com sistemas corporativos da instituição.

## 8.2 Solicitação

### Objetivo

Representa o processo administrativo de Reconhecimento de Saberes e Competências (RSC).

A Solicitação constitui a principal entidade do domínio do SG-RSC, sendo responsável por controlar todo o ciclo de vida do processo, desde sua criação até o encerramento.

Todas as demais entidades do sistema estarão direta ou indiretamente vinculadas a uma Solicitação.

---

### Tabela

**solicitacao**

---

### Chave Primária

| Campo | Tipo |
|--------|------|
| id | BIGINT |

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| numero_protocolo | VARCHAR(30) | Sim | Sim | Número gerado pelo SG-RSC |
| numero_processo | VARCHAR(50) | Não | Sim | Processo no SEI ou sistema equivalente |
| servidor_id | BIGINT | Sim | Não | FK para Servidor |
| nivel_rsc_id | BIGINT | Sim | Não | FK para nivel_rsc |
| status_solicitacao_id | BIGINT | Sim | Não | FK para status_solicitacao |
| resultado_solicitacao_id | BIGINT | Não | Não | FK para resultado_solicitacao |
| data_solicitacao | TIMESTAMP | Sim | Não | Data da criação |
| data_protocolo | TIMESTAMP | Sim | Não | Data do protocolo |
| data_encerramento | TIMESTAMP | Não | Não | Data de encerramento |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Servidor → Solicitação | 1:N |
| Nível RSC → Solicitação | 1:N |
| Status Solicitação → Solicitação | 1:N |
| Resultado Solicitação → Solicitação | 1:N |
| Solicitação → Documento | 1:N |
| Solicitação → Avaliação | 1:N |
| Solicitação → Recurso | 1:N |
| Solicitação → Histórico | 1:N |
| Solicitação → Notificação | 1:N |

### Observação

O Memorial Descritivo será armazenado como um Documento do tipo MEMORIAL.

Essa abordagem elimina a necessidade de uma tabela específica para memorial, reutilizando a estrutura de documentos para upload, armazenamento, validação, auditoria e rastreabilidade.

---

### Índices

- numero_protocolo (UNIQUE)
- numero_processo (UNIQUE)
- servidor_id
- status_solicitacao_id
- data_protocolo

---

### Restrições

- Toda Solicitação deve pertencer a um Servidor.
- O número de protocolo deve ser único.
- O tipo de RSC deve ser válido.
- O status deve seguir a máquina de estados definida pelo sistema.
- Uma Solicitação encerrada não poderá sofrer alterações.

---

### Auditoria

A entidade possuirá os seguintes campos:

- created_at
- created_by
- updated_at
- updated_by
- deleted_at
- deleted_by

---

### Observações

A Solicitação é a Aggregate Root do domínio do SG-RSC.

Todas as alterações nas entidades diretamente relacionadas deverão respeitar as regras de negócio definidas por esta entidade.

## 8.3 Nível RSC

### Objetivo

Representa os níveis possíveis do RSC-PCCTAE.

Essa entidade parametriza os níveis de reconhecimento previstos na legislação, evitando que valores fixos sejam gravados diretamente na tabela de solicitação.

---

### Tabela

**nivel_rsc**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(10) | Sim | Sim | I, II, III, IV, V ou VI |
| nome | VARCHAR(100) | Sim | Sim | Exemplo: RSC-PCCTAE VI |
| descricao | TEXT | Não | Não | Descrição do nível |
| percentual_incentivo | NUMERIC(5,2) | Sim | Não | Percentual do incentivo |
| ativo | BOOLEAN | Sim | Não | Indica se o nível está ativo |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Nível RSC → Solicitação | 1:N |

---

### Dados iniciais previstos

| Código | Nome | Percentual |
|--------|------|------------|
| I | RSC-PCCTAE I | 10 |
| II | RSC-PCCTAE II | 15 |
| III | RSC-PCCTAE III | 25 |
| IV | RSC-PCCTAE IV | 30 |
| V | RSC-PCCTAE V | 52 |
| VI | RSC-PCCTAE VI | 75 |

---

## 8.4 Status da Solicitação

### Objetivo

Representa os estados possíveis de uma Solicitação durante seu ciclo de vida.

Essa entidade permite controlar a máquina de estados da solicitação de forma parametrizada, evitando o uso de textos livres na tabela principal.

---

### Tabela

**status_solicitacao**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(50) | Sim | Sim | Código interno do status |
| nome | VARCHAR(100) | Sim | Sim | Nome exibido ao usuário |
| descricao | TEXT | Não | Não | Descrição do status |
| ordem | INTEGER | Sim | Não | Ordem lógica no fluxo |
| permite_edicao | BOOLEAN | Sim | Não | Indica se a solicitação pode ser editada nesse status |
| ativo | BOOLEAN | Sim | Não | Indica se o status está ativo |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Status Solicitação → Solicitação | 1:N |

---

### Dados iniciais previstos

| Código | Nome | Permite edição |
|--------|------|----------------|
| RASCUNHO | Rascunho | Sim |
| PROTOCOLADA | Protocolada | Não |
| CONFERENCIA_DOCUMENTAL | Em Conferência Documental | Não |
| EM_AVALIACAO | Em Avaliação | Não |
| AGUARDANDO_PARECER | Aguardando Parecer | Não |
| RESULTADO_PUBLICADO | Resultado Publicado | Não |
| EM_RECURSO | Em Recurso | Não |
| ENCERRADA | Encerrada | Não |

## 8.5 Resultado da Solicitação

### Objetivo

Representa o resultado administrativo de uma Solicitação após a análise e emissão de parecer.

Essa entidade evita o uso de textos livres para registrar decisões como deferimento, indeferimento ou arquivamento.

---

### Tabela

**resultado_solicitacao**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(50) | Sim | Sim | Código interno do resultado |
| nome | VARCHAR(100) | Sim | Sim | Nome exibido ao usuário |
| descricao | TEXT | Não | Não | Descrição do resultado |
| ativo | BOOLEAN | Sim | Não | Indica se o resultado está ativo |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Resultado Solicitação → Solicitação | 1:N |

---

### Dados iniciais previstos

| Código | Nome |
|--------|------|
| DEFERIDA | Deferida |
| INDEFERIDA | Indeferida |
| ARQUIVADA | Arquivada |
| CANCELADA | Cancelada |

## 8.6 Situação Funcional

### Objetivo

Representa a situação funcional do servidor no momento da solicitação.

Essa entidade evita valores livres como Ativo, Afastado, Aposentado ou Cedido, permitindo padronização e melhor integração futura com sistemas institucionais.

---

### Tabela

**situacao_funcional**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(50) | Sim | Sim | Código interno |
| nome | VARCHAR(100) | Sim | Sim | Nome exibido ao usuário |
| descricao | TEXT | Não | Não | Descrição da situação |
| ativo | BOOLEAN | Sim | Não | Indica se a situação pode ser utilizada |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Situação Funcional → Servidor | 1:N |

---

### Dados iniciais previstos

| Código | Nome |
|--------|------|
| ATIVO | Ativo |
| AFASTADO | Afastado |
| CEDIDO | Cedido |
| APOSENTADO | Aposentado |
| EXONERADO | Exonerado |

## 8.7 Documento

### Objetivo

Representa um arquivo anexado à Solicitação para comprovar informações, experiências, atividades ou requisitos declarados pelo servidor.

---

### Tabela

**documento**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| solicitacao_id | BIGINT | Sim | Não | FK para solicitacao |
| tipo_documento_id | BIGINT | Sim | Não | FK para tipo_documento |
| nome_original | VARCHAR(255) | Sim | Não | Nome original do arquivo |
| nome_armazenado | VARCHAR(255) | Sim | Sim | Nome usado no armazenamento |
| caminho_arquivo | VARCHAR(500) | Sim | Não | Caminho físico ou lógico do arquivo |
| tamanho_bytes | BIGINT | Sim | Não | Tamanho do arquivo |
| mime_type | VARCHAR(100) | Sim | Não | Tipo MIME do arquivo |
| hash_arquivo | VARCHAR(128) | Sim | Não | Hash para controle de integridade |
| data_envio | TIMESTAMP | Sim | Não | Data de envio |
| status | VARCHAR(30) | Sim | Não | Enviado, Validado, Rejeitado |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Solicitação → Documento | 1:N |
| Tipo Documento → Documento | 1:N |

---

### Índices

- solicitacao_id
- tipo_documento_id
- hash_arquivo

## 8.8 Tipo de Documento

### Objetivo

Representa os tipos de documentos aceitos pelo SG-RSC.

Essa entidade permite parametrizar documentos obrigatórios, formatos aceitos e regras básicas de upload.

---

### Tabela

**tipo_documento**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(50) | Sim | Sim | Código interno |
| nome | VARCHAR(100) | Sim | Sim | Nome exibido ao usuário |
| descricao | TEXT | Não | Não | Descrição do tipo |
| obrigatorio | BOOLEAN | Sim | Não | Indica se é obrigatório |
| permite_pdf | BOOLEAN | Sim | Não | Permite arquivo PDF |
| permite_imagem | BOOLEAN | Sim | Não | Permite imagem |
| tamanho_maximo_mb | INTEGER | Sim | Não | Tamanho máximo permitido |
| ativo | BOOLEAN | Sim | Não | Indica se pode ser utilizado |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Tipo Documento → Documento | 1:N |

---

### Dados iniciais previstos

| Código | Nome | Obrigatório |
|--------|------|-------------|
| MEMORIAL | Memorial Descritivo | Sim |
| DIPLOMA | Diploma ou Certificado | Não |
| PORTARIA | Portaria | Não |
| DECLARACAO | Declaração Institucional | Não |
| CERTIFICADO | Certificado de Capacitação | Não |
| PUBLICACAO | Publicação Técnica ou Científica | Não |
| RELATORIO | Relatório Técnico | Não |
| OUTRO | Outro Documento | Não |

## 8.9 Avaliação

### Objetivo

Representa o processo de análise técnica realizado pela Comissão Avaliadora sobre uma Solicitação de RSC.

A Avaliação é responsável por consolidar a análise dos documentos apresentados, atribuir pontuações aos critérios previstos na legislação e subsidiar a emissão do parecer.

Uma Solicitação poderá possuir mais de uma Avaliação, permitindo reavaliações ou novas análises decorrentes de recursos administrativos.

---

### Tabela

**avaliacao**

---

### Chave Primária

| Campo | Tipo |
|--------|------|
| id | BIGINT |

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| solicitacao_id | BIGINT | Sim | Não | FK para solicitacao |
| comissao_id | BIGINT | Sim | Não | FK para comissao |
| data_inicio | TIMESTAMP | Sim | Não | Início da avaliação |
| data_fim | TIMESTAMP | Não | Não | Encerramento da avaliação |
| observacoes | TEXT | Não | Não | Observações gerais da comissão |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Solicitação → Avaliação | 1:N |
| Comissão → Avaliação | 1:N |
| Avaliação → Pontuação | 1:N |
| Avaliação → Parecer | 1:N |

---

### Índices

- solicitacao_id
- comissao_id
- data_inicio

---

### Restrições

- Toda Avaliação deve estar vinculada a uma Solicitação.
- Toda Avaliação deve ser realizada por uma Comissão.
- A data de término não poderá ser anterior à data de início.

---

### Auditoria

A entidade possuirá os seguintes campos:

- created_at
- created_by
- updated_at
- updated_by
- deleted_at
- deleted_by

---

### Observações

A Avaliação representa a execução da análise técnica da Comissão.

Ela concentra as pontuações atribuídas aos critérios e fundamenta os pareceres emitidos no processo administrativo.

## 8.10 Pontuação

### Objetivo

Representa a pontuação atribuída a um critério durante uma Avaliação.

A Pontuação registra o vínculo entre a Avaliação e o Critério analisado, permitindo rastrear os pontos declarados pelo servidor, os pontos homologados pela Comissão e a justificativa da análise.

---

### Tabela

**pontuacao**

---

### Chave Primária

| Campo | Tipo |
|--------|------|
| id | BIGINT |

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| avaliacao_id | BIGINT | Sim | Não | FK para avaliacao |
| criterio_id | BIGINT | Sim | Não | FK para criterio |
| pontos_declarados | NUMERIC(6,2) | Sim | Não | Pontos informados pelo servidor |
| pontos_homologados | NUMERIC(6,2) | Não | Não | Pontos aceitos pela comissão |
| justificativa | TEXT | Não | Não | Justificativa da comissão |
| status | VARCHAR(30) | Sim | Não | Pendente, Homologada ou Rejeitada |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Avaliação → Pontuação | 1:N |
| Critério → Pontuação | 1:N |

---

### Índices

- avaliacao_id
- criterio_id

---

### Restrições

- Toda Pontuação deve pertencer a uma Avaliação.
- Toda Pontuação deve estar vinculada a um Critério.
- Pontos homologados não podem ser negativos.
- A pontuação homologada não deve ultrapassar o limite previsto para o Critério.

---

### Auditoria

A entidade possuirá os seguintes campos:

- created_at
- created_by
- updated_at
- updated_by
- deleted_at
- deleted_by

---

### Observações

A Pontuação não será armazenada diretamente na Solicitação.

Ela será calculada a partir das Pontuações vinculadas à Avaliação, garantindo rastreabilidade por critério.

## 8.11 Legislação

### Objetivo

Representa a norma legal que fundamenta os requisitos e critérios de pontuação do RSC.

---

### Tabela

**legislacao**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| tipo | VARCHAR(50) | Sim | Não | Lei, Decreto, Portaria etc. |
| numero | VARCHAR(50) | Sim | Não | Número da norma |
| ano | INTEGER | Sim | Não | Ano da norma |
| titulo | VARCHAR(255) | Sim | Não | Título resumido |
| descricao | TEXT | Não | Não | Descrição da norma |
| data_publicacao | DATE | Não | Não | Data de publicação |
| ativo | BOOLEAN | Sim | Não | Indica se a norma está ativa |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Legislação → Requisito | 1:N |

---

## 8.12 Requisito

### Objetivo

Representa os grupos de requisitos previstos na legislação, como Requisito I, II, III, IV, V e VI.

---

### Tabela

**requisito**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| legislacao_id | BIGINT | Sim | Não | FK para legislacao |
| codigo | VARCHAR(20) | Sim | Não | Exemplo: I, II, III |
| nome | VARCHAR(255) | Sim | Não | Nome do requisito |
| descricao | TEXT | Não | Não | Descrição detalhada |
| ativo | BOOLEAN | Sim | Não | Indica se está ativo |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Legislação → Requisito | 1:N |
| Requisito → Critério | 1:N |

---

## 8.13 Critério

### Objetivo

Representa cada item pontuável previsto nos anexos da legislação.

---

### Tabela

**criterio**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| requisito_id | BIGINT | Sim | Não | FK para requisito |
| codigo | VARCHAR(20) | Sim | Não | Item do anexo |
| descricao | TEXT | Sim | Não | Descrição do critério |
| unidade_medida | VARCHAR(100) | Sim | Não | Por ano, por produto, por projeto etc. |
| pontos | NUMERIC(6,2) | Sim | Não | Pontuação do critério |
| ativo | BOOLEAN | Sim | Não | Indica se está ativo |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Requisito → Critério | 1:N |
| Critério → Pontuação | 1:N |

---

### Observações

Os critérios serão parametrizados no banco de dados.

Nenhum critério de pontuação deverá ser implementado diretamente no código-fonte.

## 8.14 Parecer

### Objetivo

Representa a manifestação formal emitida pela Comissão Avaliadora ao término de uma Avaliação.

O Parecer consolida a análise técnica realizada pela comissão, fundamenta a decisão administrativa e registra a conclusão da avaliação.

Uma Avaliação poderá possuir mais de um Parecer, permitindo registrar versões preliminares, complementações ou pareceres emitidos após recursos administrativos.

---

### Tabela

**parecer**

---

### Chave Primária

| Campo | Tipo |
|--------|------|
| id | BIGINT |

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| avaliacao_id | BIGINT | Sim | Não | FK para avaliacao |
| tipo_parecer_id | BIGINT | Sim | Não | FK para tipo_parecer |
| texto | TEXT | Sim | Não | Conteúdo do parecer |
| conclusao | TEXT | Não | Não | Síntese da decisão |
| data_emissao | TIMESTAMP | Sim | Não | Data de emissão |
| versao | INTEGER | Sim | Não | Controle de versões |
| assinado | BOOLEAN | Sim | Não | Indica se foi assinado |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Avaliação → Parecer | 1:N |
| Tipo Parecer → Parecer | 1:N |

---

### Índices

- avaliacao_id
- tipo_parecer_id
- data_emissao

---

### Restrições

- Todo Parecer deve pertencer a uma Avaliação.
- Todo Parecer deve possuir um Tipo de Parecer válido.
- Um Parecer assinado não poderá ser alterado.

---

### Auditoria

A entidade possuirá os seguintes campos:

- created_at
- created_by
- updated_at
- updated_by
- deleted_at
- deleted_by

---

### Observações

O Parecer representa o documento oficial da Comissão Avaliadora.

Sua emissão encerra a fase técnica da Avaliação e fundamenta a decisão administrativa da Solicitação.

## 8.15 Tipo de Parecer

### Objetivo

Parametriza os tipos de parecer utilizados pelo SG-RSC.

---

### Tabela

**tipo_parecer**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(50) | Sim | Sim | Código interno |
| nome | VARCHAR(100) | Sim | Sim | Nome do tipo |
| descricao | TEXT | Não | Não | Descrição |
| ativo | BOOLEAN | Sim | Não | Indica se está ativo |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Tipo Parecer → Parecer | 1:N |

---

### Dados iniciais previstos

| Código | Nome |
|--------|------|
| PRELIMINAR | Parecer Preliminar |
| FINAL | Parecer Final |
| POS_RECURSO | Parecer após Recurso |

## 8.16 Recurso

### Objetivo

Representa o recurso administrativo apresentado pelo servidor após a publicação do resultado da Solicitação.

---

### Tabela

**recurso**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| solicitacao_id | BIGINT | Sim | Não | FK para solicitacao |
| texto | TEXT | Sim | Não | Fundamentação do recurso |
| data_interposicao | TIMESTAMP | Sim | Não | Data do recurso |
| resultado_solicitacao_id | BIGINT | Não | Não | Resultado após julgamento |
| data_julgamento | TIMESTAMP | Não | Não | Data do julgamento |
| observacao_julgamento | TEXT | Não | Não | Observação da comissão |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Solicitação → Recurso | 1:N |
| Resultado Solicitação → Recurso | 1:N |

---

### Índices

- solicitacao_id
- resultado_solicitacao_id
- data_interposicao

---

### Restrições

- Todo Recurso deve pertencer a uma Solicitação.
- Recurso só poderá existir após resultado publicado.
- Recurso julgado deverá possuir data de julgamento.

## 8.17 Comissão

### Objetivo

Representa a comissão responsável pela análise das solicitações de RSC.

---

### Tabela

**comissao**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| nome | VARCHAR(200) | Sim | Não | Nome da comissão |
| descricao | TEXT | Não | Não | Descrição |
| data_inicio | DATE | Sim | Não | Início da vigência |
| data_fim | DATE | Não | Não | Fim da vigência |
| ativa | BOOLEAN | Sim | Não | Indica se está ativa |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Comissão → Avaliação | 1:N |
| Comissão → Membro Comissão | 1:N |

---

## 8.18 Membro da Comissão

### Objetivo

Representa os servidores que compõem uma Comissão.

---

### Tabela

**membro_comissao**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| comissao_id | BIGINT | Sim | Não | FK para comissao |
| servidor_id | BIGINT | Sim | Não | FK para servidor |
| papel | VARCHAR(50) | Sim | Não | Presidente, membro, suplente etc. |
| data_inicio | DATE | Sim | Não | Início da participação |
| data_fim | DATE | Não | Não | Fim da participação |
| ativo | BOOLEAN | Sim | Não | Indica se está ativo |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Comissão → Membro Comissão | 1:N |
| Servidor → Membro Comissão | 1:N |

---

### Índices

- comissao_id
- servidor_id

## 8.19 Histórico

### Objetivo

Representa a linha do tempo de eventos de uma Solicitação.

Cada alteração relevante realizada no processo deverá gerar um registro de histórico, permitindo rastreabilidade completa das ações executadas no sistema.

---

### Tabela

**historico**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| solicitacao_id | BIGINT | Sim | Não | FK para solicitacao |
| tipo_historico_id | BIGINT | Sim | Não | FK para tipo_historico |
| descricao | TEXT | Sim | Não | Descrição do evento ocorrido |
| usuario | VARCHAR(200) | Sim | Não | Usuário responsável pela ação |
| data_evento | TIMESTAMP | Sim | Não | Data e hora do evento |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Solicitação → Histórico | 1:N |
| Tipo Histórico → Histórico | 1:N |

---

### Índices

- solicitacao_id
- tipo_historico_id
- data_evento

---

### Restrições

- Todo registro de Histórico deve estar vinculado a uma Solicitação.
- O Histórico não poderá ser alterado após sua criação.
- A data do evento será registrada automaticamente pelo sistema.

---

### Auditoria

A entidade possuirá os seguintes campos:

- created_at
- created_by
- updated_at
- updated_by
- deleted_at
- deleted_by

---

### Observações

O Histórico representa a trilha de auditoria funcional da Solicitação.

Ele complementa os campos de auditoria das entidades, registrando eventos de negócio, como criação da solicitação, envio de documentos, início da avaliação, emissão de parecer, interposição de recurso e encerramento do processo.

## 8.20 Tipo de Histórico

### Objetivo

Parametriza os tipos de eventos registrados na linha do tempo da Solicitação.

---

### Tabela

**tipo_historico**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(50) | Sim | Sim | Código interno |
| nome | VARCHAR(100) | Sim | Sim | Nome do evento |
| descricao | TEXT | Não | Não | Descrição do evento |
| ativo | BOOLEAN | Sim | Não | Indica se pode ser utilizado |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Tipo Histórico → Histórico | 1:N |

---

### Dados iniciais previstos

| Código | Nome |
|--------|------|
| SOLICITACAO_CRIADA | Solicitação criada |
| DOCUMENTO_ANEXADO | Documento anexado |
| AVALIACAO_INICIADA | Avaliação iniciada |
| PARECER_EMITIDO | Parecer emitido |
| RESULTADO_PUBLICADO | Resultado publicado |
| RECURSO_INTERPOSTO | Recurso interposto |
| RECURSO_JULGADO | Recurso julgado |
| SOLICITACAO_ENCERRADA | Solicitação encerrada |

## 8.21 Notificação

### Objetivo

Representa uma comunicação enviada pelo sistema ao usuário sobre eventos relacionados à Solicitação.

---

### Tabela

**notificacao**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| solicitacao_id | BIGINT | Não | Não | FK para solicitacao |
| tipo_notificacao_id | BIGINT | Sim | Não | FK para tipo_notificacao |
| destinatario | VARCHAR(200) | Sim | Não | E-mail ou identificação do usuário |
| titulo | VARCHAR(200) | Sim | Não | Título da notificação |
| mensagem | TEXT | Sim | Não | Conteúdo da notificação |
| data_envio | TIMESTAMP | Sim | Não | Data de envio |
| lida | BOOLEAN | Sim | Não | Indica se foi lida |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Solicitação → Notificação | 1:N |
| Tipo Notificação → Notificação | 1:N |

---

### Índices

- solicitacao_id
- tipo_notificacao_id
- destinatario
- data_envio

---

### Observações

A Notificação poderá estar vinculada a uma Solicitação, mas essa relação não será obrigatória.

Isso permite notificações gerais do sistema, sem vínculo direto com um processo específico.

## 8.22 Tipo de Notificação

### Objetivo

Parametriza os tipos de notificações utilizadas pelo sistema.

---

### Tabela

**tipo_notificacao**

---

### Atributos

| Campo | Tipo PostgreSQL | Obrigatório | Único | Observação |
|--------|-----------------|-------------|--------|------------|
| id | BIGINT | Sim | Sim | Chave primária |
| codigo | VARCHAR(50) | Sim | Sim | Código interno |
| nome | VARCHAR(100) | Sim | Sim | Nome do tipo |
| descricao | TEXT | Não | Não | Descrição |
| ativo | BOOLEAN | Sim | Não | Indica se pode ser utilizado |

---

### Relacionamentos

| Relacionamento | Cardinalidade |
|----------------|---------------|
| Tipo Notificação → Notificação | 1:N |

---

### Dados iniciais previstos

| Código | Nome |
|--------|------|
| SOLICITACAO_PROTOCOLADA | Solicitação protocolada |
| PENDENCIA_IDENTIFICADA | Pendência identificada |
| AVALIACAO_INICIADA | Avaliação iniciada |
| PARECER_EMITIDO | Parecer emitido |
| RESULTADO_PUBLICADO | Resultado publicado |
| RECURSO_RECEBIDO | Recurso recebido |
| PROCESSO_ENCERRADO | Processo encerrado |

# 9. Revisão de Parametrização

Esta seção identifica campos que não devem ser armazenados apenas como texto livre, pois representam configurações, estados ou classificações reutilizáveis do sistema.

O objetivo é evitar valores inconsistentes, facilitar manutenção e permitir evolução futura sem alteração estrutural no banco.

## 9.1 Tabelas Parametrizadoras Identificadas

| Conceito | Tabela sugerida | Justificativa |
|----------|-----------------|---------------|
| Nível do RSC | nivel_rsc | Parametriza os níveis RSC-PCCTAE I a VI |
| Status da Solicitação | status_solicitacao | Controla a máquina de estados da solicitação |
| Resultado da Solicitação | resultado_solicitacao | Evita textos livres como Deferido/Indeferido |
| Situação Funcional do Servidor | situacao_funcional | Evita valores soltos como Ativo/Afastado/Aposentado |
| Tipo de Documento | tipo_documento | Classifica documentos anexados |
| Tipo de Parecer | tipo_parecer | Diferencia parecer preliminar, final e pós-recurso |
| Tipo de Recurso | tipo_recurso | Classifica recursos administrativos |
| Tipo de Notificação | tipo_notificacao | Padroniza notificações do sistema |
| Tipo de Histórico | tipo_historico | Padroniza eventos da linha do tempo |
| Decisão da Avaliação | decisao_avaliacao | Parametriza deferimento, indeferimento ou diligência |

---

## 9.2 Decisão de Modelagem

Nem toda parametrização será implementada imediatamente.

Para o MVP, serão priorizadas as tabelas que impactam diretamente o fluxo principal:

1. nivel_rsc
2. status_solicitacao
3. resultado_solicitacao
4. situacao_funcional
5. tipo_documento

As demais poderão ser incluídas em versões futuras, conforme a evolução do sistema.

---

## 9.3 Ajustes Necessários no MER Lógico

A entidade solicitacao deverá substituir:

| Campo atual | Novo campo |
|------------|------------|
| status | status_solicitacao_id |
| resultado | resultado_solicitacao_id |
| tipo_rsc | nivel_rsc_id |

A entidade servidor deverá substituir:

| Campo atual | Novo campo |
|------------|------------|
| situacao | situacao_funcional_id |

A entidade documento deverá possuir futuramente:

| Campo |
|-------|
| tipo_documento_id |

---

## 9.4 Observação

As tabelas parametrizadoras devem possuir, no mínimo:

| Campo | Finalidade |
|-------|------------|
| id | Chave primária |
| codigo | Código interno |
| nome | Nome exibido ao usuário |
| descricao | Explicação da opção |
| ativo | Indica se pode ser utilizado |

## 10. Diagrama Geral do MER

Servidor
    │
    ├───────────────┐
    ▼               │
Solicitação ◄────── Nível RSC
    │
    ├────────────── Status Solicitação
    │
    ├────────────── Resultado Solicitação
    │
    ├────────────── Documento ◄──────── Tipo Documento
    │
    ├────────────── Avaliação ◄──────── Comissão
    │                     │
    │                     ├──────── Pontuação ◄──── Critério ◄──── Requisito ◄──── Legislação
    │                     │
    │                     └──────── Parecer ◄────── Tipo Parecer
    │
    ├────────────── Recurso
    │
    ├────────────── Histórico ◄────── Tipo Histórico
    │
    └────────────── Notificação ◄──── Tipo Notificação

Comissão
    │
    └────────────── Membro Comissão ◄──── Servidor

    
# 11. Resumo das Entidades

## Entidades de Negócio

- servidor
- solicitacao
- documento
- avaliacao
- pontuacao
- parecer
- recurso
- comissao
- membro_comissao
- historico
- notificacao

## Entidades Parametrizadoras

- nivel_rsc
- status_solicitacao
- resultado_solicitacao
- situacao_funcional
- tipo_documento
- tipo_parecer
- tipo_historico
- tipo_notificacao
- legislacao
- requisito
- criterio

