-- ============================================================================
-- Dados referenciais mínimos para desenvolvimento e testes
--
-- ATENÇÃO:
-- Estes registros não representam a regulamentação completa e definitiva
-- do RSC-PCCTAE. Devem ser substituídos ou complementados quando a matriz
-- oficial de requisitos e critérios estiver consolidada no projeto.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- Legislação de referência
-- ----------------------------------------------------------------------------

INSERT INTO legislacao (
    tipo,
    numero,
    ano,
    titulo,
    descricao,
    data_publicacao,
    ativo
)
SELECT
    'DECRETO',
    '13.048',
    2026,
    'Regulamentação do Reconhecimento de Saberes e Competências',
    'Legislação de referência utilizada no desenvolvimento do SG-RSC.',
    NULL,
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM legislacao
    WHERE tipo = 'DECRETO'
      AND numero = '13.048'
      AND ano = 2026
      AND deleted_at IS NULL
);

-- ----------------------------------------------------------------------------
-- Requisitos mínimos de desenvolvimento
-- ----------------------------------------------------------------------------

INSERT INTO requisito (
    legislacao_id,
    codigo,
    nome,
    descricao,
    ativo
)
SELECT
    l.id,
    'REQ-01',
    'Experiência profissional e institucional',
    'Atividades relacionadas à experiência profissional, técnica, administrativa e institucional do servidor.',
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM requisito r
      WHERE r.codigo = 'REQ-01'
        AND r.legislacao_id = l.id
        AND r.deleted_at IS NULL
  );

INSERT INTO requisito (
    legislacao_id,
    codigo,
    nome,
    descricao,
    ativo
)
SELECT
    l.id,
    'REQ-02',
    'Capacitação e desenvolvimento profissional',
    'Atividades de capacitação, aperfeiçoamento, formação e desenvolvimento profissional.',
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM requisito r
      WHERE r.codigo = 'REQ-02'
        AND r.legislacao_id = l.id
        AND r.deleted_at IS NULL
  );

INSERT INTO requisito (
    legislacao_id,
    codigo,
    nome,
    descricao,
    ativo
)
SELECT
    l.id,
    'REQ-03',
    'Produção técnica, científica e inovação',
    'Atividades relacionadas à produção técnica, científica, acadêmica, tecnológica e de inovação.',
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM requisito r
      WHERE r.codigo = 'REQ-03'
        AND r.legislacao_id = l.id
        AND r.deleted_at IS NULL
  );

-- ----------------------------------------------------------------------------
-- Critérios mínimos de desenvolvimento
-- ----------------------------------------------------------------------------

INSERT INTO criterio (
    requisito_id,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ativo
)
SELECT
    r.id,
    'CRIT-01',
    'Participação em projeto ou atividade institucional.',
    'PROJETO',
    10.00,
    TRUE
FROM requisito r
WHERE r.codigo = 'REQ-01'
  AND r.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM criterio c
      WHERE c.codigo = 'CRIT-01'
        AND c.requisito_id = r.id
        AND c.deleted_at IS NULL
  );

INSERT INTO criterio (
    requisito_id,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ativo
)
SELECT
    r.id,
    'CRIT-02',
    'Exercício de função de gestão, coordenação ou responsabilidade institucional.',
    'MÊS',
    1.00,
    TRUE
FROM requisito r
WHERE r.codigo = 'REQ-01'
  AND r.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM criterio c
      WHERE c.codigo = 'CRIT-02'
        AND c.requisito_id = r.id
        AND c.deleted_at IS NULL
  );

INSERT INTO criterio (
    requisito_id,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ativo
)
SELECT
    r.id,
    'CRIT-03',
    'Conclusão de curso de capacitação ou aperfeiçoamento profissional.',
    'CURSO',
    5.00,
    TRUE
FROM requisito r
WHERE r.codigo = 'REQ-02'
  AND r.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM criterio c
      WHERE c.codigo = 'CRIT-03'
        AND c.requisito_id = r.id
        AND c.deleted_at IS NULL
  );

INSERT INTO criterio (
    requisito_id,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ativo
)
SELECT
    r.id,
    'CRIT-04',
    'Participação como instrutor, palestrante ou facilitador em ação de desenvolvimento.',
    'EVENTO',
    5.00,
    TRUE
FROM requisito r
WHERE r.codigo = 'REQ-02'
  AND r.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM criterio c
      WHERE c.codigo = 'CRIT-04'
        AND c.requisito_id = r.id
        AND c.deleted_at IS NULL
  );

INSERT INTO criterio (
    requisito_id,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ativo
)
SELECT
    r.id,
    'CRIT-05',
    'Produção ou publicação de trabalho técnico ou científico.',
    'PUBLICAÇÃO',
    10.00,
    TRUE
FROM requisito r
WHERE r.codigo = 'REQ-03'
  AND r.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM criterio c
      WHERE c.codigo = 'CRIT-05'
        AND c.requisito_id = r.id
        AND c.deleted_at IS NULL
  );

INSERT INTO criterio (
    requisito_id,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ativo
)
SELECT
    r.id,
    'CRIT-06',
    'Desenvolvimento ou participação no desenvolvimento de sistema, solução tecnológica ou inovação institucional.',
    'SOLUÇÃO',
    15.00,
    TRUE
FROM requisito r
WHERE r.codigo = 'REQ-03'
  AND r.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM criterio c
      WHERE c.codigo = 'CRIT-06'
        AND c.requisito_id = r.id
        AND c.deleted_at IS NULL
  );
