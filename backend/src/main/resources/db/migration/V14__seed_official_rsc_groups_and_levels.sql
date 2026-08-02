-- ============================================================================
-- Carga oficial dos grupos e níveis do RSC-PCCTAE
--
-- Esta migration:
--   1. permite que critérios oficiais não dependam dos requisitos fictícios;
--   2. cadastra os seis grupos oficiais de critérios;
--   3. atualiza os seis níveis de RSC;
--   4. cadastra as regras de complexidade dos níveis IV, V e VI;
--   5. inativa os critérios simplificados da V10.
--
-- Os critérios oficiais serão carregados na migration V15.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- Permitir critérios vinculados diretamente aos grupos oficiais
-- ----------------------------------------------------------------------------

ALTER TABLE criterio
    ALTER COLUMN requisito_id DROP NOT NULL;

-- ----------------------------------------------------------------------------
-- Localizar a legislação oficial
-- ----------------------------------------------------------------------------

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM legislacao
        WHERE tipo = 'DECRETO'
          AND numero = '13.048'
          AND ano = 2026
          AND deleted_at IS NULL
    ) THEN
        RAISE EXCEPTION
            'Legislação Decreto nº 13.048/2026 não encontrada.';
    END IF;
END $$;

-- ----------------------------------------------------------------------------
-- Grupo I
-- ----------------------------------------------------------------------------

INSERT INTO grupo_criterio (
    legislacao_id,
    codigo,
    numero_romano,
    nome,
    descricao,
    ordem,
    ativo
)
SELECT
    l.id,
    'GRUPO_I',
    'I',
    'Participação em Grupos de Trabalho, Comissões, Comitês, Núcleos, Representações ou Similares',
    'Participação em grupos de trabalho, comissões, comitês, núcleos, representações ou similares, formalmente instituídos ou reconhecidos pelo órgão ou pela entidade.',
    1,
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM grupo_criterio gc
      WHERE gc.legislacao_id = l.id
        AND gc.codigo = 'GRUPO_I'
  );

-- ----------------------------------------------------------------------------
-- Grupo II
-- ----------------------------------------------------------------------------

INSERT INTO grupo_criterio (
    legislacao_id,
    codigo,
    numero_romano,
    nome,
    descricao,
    ordem,
    ativo
)
SELECT
    l.id,
    'GRUPO_II',
    'II',
    'Participação e Atuação em Projetos Institucionais',
    'Participação e atuação em projetos institucionais, na gestão, no apoio ao ensino, à pesquisa, à extensão, à inovação e à assistência especializada.',
    2,
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM grupo_criterio gc
      WHERE gc.legislacao_id = l.id
        AND gc.codigo = 'GRUPO_II'
  );

-- ----------------------------------------------------------------------------
-- Grupo III
-- ----------------------------------------------------------------------------

INSERT INTO grupo_criterio (
    legislacao_id,
    codigo,
    numero_romano,
    nome,
    descricao,
    ordem,
    ativo
)
SELECT
    l.id,
    'GRUPO_III',
    'III',
    'Recebimento de Premiação por Projetos Implementados na Administração Pública',
    'Recebimento de premiação em evento de reconhecimento público por projetos implementados na administração pública.',
    3,
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM grupo_criterio gc
      WHERE gc.legislacao_id = l.id
        AND gc.codigo = 'GRUPO_III'
  );

-- ----------------------------------------------------------------------------
-- Grupo IV
-- ----------------------------------------------------------------------------

INSERT INTO grupo_criterio (
    legislacao_id,
    codigo,
    numero_romano,
    nome,
    descricao,
    ordem,
    ativo
)
SELECT
    l.id,
    'GRUPO_IV',
    'IV',
    'Designação para Responsabilidades Técnico-Administrativas ou Especializadas',
    'Designação para assunção de responsabilidades técnico-administrativas ou especializadas.',
    4,
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM grupo_criterio gc
      WHERE gc.legislacao_id = l.id
        AND gc.codigo = 'GRUPO_IV'
  );

-- ----------------------------------------------------------------------------
-- Grupo V
-- ----------------------------------------------------------------------------

INSERT INTO grupo_criterio (
    legislacao_id,
    codigo,
    numero_romano,
    nome,
    descricao,
    ordem,
    ativo
)
SELECT
    l.id,
    'GRUPO_V',
    'V',
    'Exercício de Função, Cargo de Direção ou Assessoramento Institucional',
    'Exercício de função ou cargo de direção ou de assessoramento institucional.',
    5,
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM grupo_criterio gc
      WHERE gc.legislacao_id = l.id
        AND gc.codigo = 'GRUPO_V'
  );

-- ----------------------------------------------------------------------------
-- Grupo VI
-- ----------------------------------------------------------------------------

INSERT INTO grupo_criterio (
    legislacao_id,
    codigo,
    numero_romano,
    nome,
    descricao,
    ordem,
    ativo
)
SELECT
    l.id,
    'GRUPO_VI',
    'VI',
    'Produção, Prospecção e Difusão de Conhecimento Científico ou Técnico',
    'Produção, prospecção e difusão de conhecimento científico ou técnico relacionado aos interesses institucionais.',
    6,
    TRUE
FROM legislacao l
WHERE l.tipo = 'DECRETO'
  AND l.numero = '13.048'
  AND l.ano = 2026
  AND l.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM grupo_criterio gc
      WHERE gc.legislacao_id = l.id
        AND gc.codigo = 'GRUPO_VI'
  );

-- ----------------------------------------------------------------------------
-- Atualização dos níveis de RSC
-- ----------------------------------------------------------------------------

UPDATE nivel_rsc
SET nome = 'RSC-PCCTAE I',
    descricao = 'Primeiro nível do Reconhecimento de Saberes e Competências.',
    percentual_incentivo = 10.00,
    pontos_minimos = 10.00,
    itens_minimos = 1,
    ativo = TRUE,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE codigo = 'NIVEL_1'
  AND deleted_at IS NULL;

UPDATE nivel_rsc
SET nome = 'RSC-PCCTAE II',
    descricao = 'Segundo nível do Reconhecimento de Saberes e Competências.',
    percentual_incentivo = 15.00,
    pontos_minimos = 15.00,
    itens_minimos = 2,
    ativo = TRUE,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE codigo = 'NIVEL_2'
  AND deleted_at IS NULL;

UPDATE nivel_rsc
SET nome = 'RSC-PCCTAE III',
    descricao = 'Terceiro nível do Reconhecimento de Saberes e Competências.',
    percentual_incentivo = 25.00,
    pontos_minimos = 25.00,
    itens_minimos = 2,
    ativo = TRUE,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE codigo = 'NIVEL_3'
  AND deleted_at IS NULL;

-- ----------------------------------------------------------------------------
-- Inserção dos níveis IV, V e VI, caso ainda não existam
-- ----------------------------------------------------------------------------

INSERT INTO nivel_rsc (
    codigo,
    nome,
    descricao,
    percentual_incentivo,
    pontos_minimos,
    itens_minimos,
    ativo
)
SELECT
    'NIVEL_4',
    'RSC-PCCTAE IV',
    'Quarto nível do Reconhecimento de Saberes e Competências.',
    30.00,
    30.00,
    3,
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM nivel_rsc
    WHERE codigo = 'NIVEL_4'
      AND deleted_at IS NULL
);

INSERT INTO nivel_rsc (
    codigo,
    nome,
    descricao,
    percentual_incentivo,
    pontos_minimos,
    itens_minimos,
    ativo
)
SELECT
    'NIVEL_5',
    'RSC-PCCTAE V',
    'Quinto nível do Reconhecimento de Saberes e Competências.',
    52.00,
    52.00,
    5,
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM nivel_rsc
    WHERE codigo = 'NIVEL_5'
      AND deleted_at IS NULL
);

INSERT INTO nivel_rsc (
    codigo,
    nome,
    descricao,
    percentual_incentivo,
    pontos_minimos,
    itens_minimos,
    ativo
)
SELECT
    'NIVEL_6',
    'RSC-PCCTAE VI',
    'Sexto nível do Reconhecimento de Saberes e Competências.',
    75.00,
    75.00,
    7,
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM nivel_rsc
    WHERE codigo = 'NIVEL_6'
      AND deleted_at IS NULL
);

-- Garantir os valores oficiais mesmo se os níveis IV, V e VI já existirem.

UPDATE nivel_rsc
SET nome = 'RSC-PCCTAE IV',
    percentual_incentivo = 30.00,
    pontos_minimos = 30.00,
    itens_minimos = 3,
    ativo = TRUE,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE codigo = 'NIVEL_4'
  AND deleted_at IS NULL;

UPDATE nivel_rsc
SET nome = 'RSC-PCCTAE V',
    percentual_incentivo = 52.00,
    pontos_minimos = 52.00,
    itens_minimos = 5,
    ativo = TRUE,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE codigo = 'NIVEL_5'
  AND deleted_at IS NULL;

UPDATE nivel_rsc
SET nome = 'RSC-PCCTAE VI',
    percentual_incentivo = 75.00,
    pontos_minimos = 75.00,
    itens_minimos = 7,
    ativo = TRUE,
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE codigo = 'NIVEL_6'
  AND deleted_at IS NULL;

-- ----------------------------------------------------------------------------
-- Regra de complexidade do RSC IV
--
-- Exige no mínimo um item dos grupos II, IV, V ou VI.
-- ----------------------------------------------------------------------------

INSERT INTO regra_complexidade_nivel (
    nivel_rsc_id,
    quantidade_minima_itens,
    descricao,
    ativo
)
SELECT
    nr.id,
    1,
    'Exige no mínimo um item pertencente aos grupos II, IV, V ou VI.',
    TRUE
FROM nivel_rsc nr
WHERE nr.codigo = 'NIVEL_4'
  AND nr.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM regra_complexidade_nivel rcn
      WHERE rcn.nivel_rsc_id = nr.id
        AND rcn.deleted_at IS NULL
  );

INSERT INTO regra_complexidade_grupo (
    regra_complexidade_nivel_id,
    grupo_criterio_id
)
SELECT
    rcn.id,
    gc.id
FROM regra_complexidade_nivel rcn
JOIN nivel_rsc nr
  ON nr.id = rcn.nivel_rsc_id
JOIN grupo_criterio gc
  ON gc.codigo IN (
      'GRUPO_II',
      'GRUPO_IV',
      'GRUPO_V',
      'GRUPO_VI'
  )
WHERE nr.codigo = 'NIVEL_4'
  AND rcn.deleted_at IS NULL
  AND gc.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM regra_complexidade_grupo rcg
      WHERE rcg.regra_complexidade_nivel_id = rcn.id
        AND rcg.grupo_criterio_id = gc.id
  );

-- ----------------------------------------------------------------------------
-- Regra de complexidade do RSC V
--
-- Exige no mínimo um item dos grupos IV, V ou VI.
-- ----------------------------------------------------------------------------

INSERT INTO regra_complexidade_nivel (
    nivel_rsc_id,
    quantidade_minima_itens,
    descricao,
    ativo
)
SELECT
    nr.id,
    1,
    'Exige no mínimo um item pertencente aos grupos IV, V ou VI.',
    TRUE
FROM nivel_rsc nr
WHERE nr.codigo = 'NIVEL_5'
  AND nr.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM regra_complexidade_nivel rcn
      WHERE rcn.nivel_rsc_id = nr.id
        AND rcn.deleted_at IS NULL
  );

INSERT INTO regra_complexidade_grupo (
    regra_complexidade_nivel_id,
    grupo_criterio_id
)
SELECT
    rcn.id,
    gc.id
FROM regra_complexidade_nivel rcn
JOIN nivel_rsc nr
  ON nr.id = rcn.nivel_rsc_id
JOIN grupo_criterio gc
  ON gc.codigo IN (
      'GRUPO_IV',
      'GRUPO_V',
      'GRUPO_VI'
  )
WHERE nr.codigo = 'NIVEL_5'
  AND rcn.deleted_at IS NULL
  AND gc.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM regra_complexidade_grupo rcg
      WHERE rcg.regra_complexidade_nivel_id = rcn.id
        AND rcg.grupo_criterio_id = gc.id
  );

-- ----------------------------------------------------------------------------
-- Regra de complexidade do RSC VI
--
-- Exige no mínimo um item do grupo VI.
-- ----------------------------------------------------------------------------

INSERT INTO regra_complexidade_nivel (
    nivel_rsc_id,
    quantidade_minima_itens,
    descricao,
    ativo
)
SELECT
    nr.id,
    1,
    'Exige no mínimo um item pertencente ao grupo VI.',
    TRUE
FROM nivel_rsc nr
WHERE nr.codigo = 'NIVEL_6'
  AND nr.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM regra_complexidade_nivel rcn
      WHERE rcn.nivel_rsc_id = nr.id
        AND rcn.deleted_at IS NULL
  );

INSERT INTO regra_complexidade_grupo (
    regra_complexidade_nivel_id,
    grupo_criterio_id
)
SELECT
    rcn.id,
    gc.id
FROM regra_complexidade_nivel rcn
JOIN nivel_rsc nr
  ON nr.id = rcn.nivel_rsc_id
JOIN grupo_criterio gc
  ON gc.codigo = 'GRUPO_VI'
WHERE nr.codigo = 'NIVEL_6'
  AND rcn.deleted_at IS NULL
  AND gc.deleted_at IS NULL
  AND NOT EXISTS (
      SELECT 1
      FROM regra_complexidade_grupo rcg
      WHERE rcg.regra_complexidade_nivel_id = rcn.id
        AND rcg.grupo_criterio_id = gc.id
  );

-- ----------------------------------------------------------------------------
-- Inativação dos critérios simplificados da V10
-- ----------------------------------------------------------------------------

UPDATE criterio
SET ativo = FALSE,
    observacao =
        'Critério simplificado utilizado apenas durante o desenvolvimento inicial.',
    updated_at = CURRENT_TIMESTAMP,
    updated_by = 'system'
WHERE codigo IN (
    'CRIT-01',
    'CRIT-02',
    'CRIT-03',
    'CRIT-04',
    'CRIT-05',
    'CRIT-06'
)
AND deleted_at IS NULL;