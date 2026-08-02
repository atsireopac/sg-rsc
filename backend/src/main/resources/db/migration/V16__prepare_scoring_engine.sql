-- ============================================================================
-- Preparação da estrutura do Motor de Pontuação
--
-- Esta migration:
--   1. adiciona a quantidade declarada à atividade;
--   2. vincula a pontuação à atividade declarada;
--   3. armazena as quantidades declarada e homologada;
--   4. armazena o valor unitário utilizado no cálculo;
--   5. amplia a precisão das pontuações;
--   6. cria restrições de integridade e índices.
--
-- As novas colunas da tabela pontuacao permanecem inicialmente opcionais
-- para preservar compatibilidade com eventuais registros antigos.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- Atividade declarada
-- ----------------------------------------------------------------------------

ALTER TABLE atividade_declarada
    ADD COLUMN quantidade_declarada NUMERIC(10,2);

ALTER TABLE atividade_declarada
    ADD CONSTRAINT ck_atividade_declarada_quantidade_positiva
        CHECK (
            quantidade_declarada IS NULL
            OR quantidade_declarada > 0
        );

-- ----------------------------------------------------------------------------
-- Evolução da tabela pontuacao
-- ----------------------------------------------------------------------------

ALTER TABLE pontuacao
    ADD COLUMN atividade_declarada_id BIGINT;

ALTER TABLE pontuacao
    ADD COLUMN quantidade_declarada NUMERIC(10,2);

ALTER TABLE pontuacao
    ADD COLUMN quantidade_homologada NUMERIC(10,2);

ALTER TABLE pontuacao
    ADD COLUMN pontos_unitarios NUMERIC(6,2);

-- Ampliação da precisão para suportar quantidades maiores.
ALTER TABLE pontuacao
    ALTER COLUMN pontos_declarados TYPE NUMERIC(10,2);

ALTER TABLE pontuacao
    ALTER COLUMN pontos_homologados TYPE NUMERIC(10,2);

-- ----------------------------------------------------------------------------
-- Relacionamentos
-- ----------------------------------------------------------------------------

ALTER TABLE pontuacao
    ADD CONSTRAINT fk_pontuacao_atividade_declarada
        FOREIGN KEY (atividade_declarada_id)
        REFERENCES atividade_declarada (id);

-- ----------------------------------------------------------------------------
-- Restrições de integridade
-- ----------------------------------------------------------------------------

ALTER TABLE pontuacao
    ADD CONSTRAINT ck_pontuacao_quantidade_declarada_positiva
        CHECK (
            quantidade_declarada IS NULL
            OR quantidade_declarada > 0
        );

ALTER TABLE pontuacao
    ADD CONSTRAINT ck_pontuacao_quantidade_homologada_nao_negativa
        CHECK (
            quantidade_homologada IS NULL
            OR quantidade_homologada >= 0
        );

ALTER TABLE pontuacao
    ADD CONSTRAINT ck_pontuacao_pontos_unitarios_nao_negativos
        CHECK (
            pontos_unitarios IS NULL
            OR pontos_unitarios >= 0
        );

ALTER TABLE pontuacao
    ADD CONSTRAINT ck_pontuacao_pontos_declarados_nao_negativos
        CHECK (
            pontos_declarados >= 0
        );

ALTER TABLE pontuacao
    ADD CONSTRAINT ck_pontuacao_pontos_homologados_nao_negativos
        CHECK (
            pontos_homologados IS NULL
            OR pontos_homologados >= 0
        );

-- A quantidade homologada não poderá ultrapassar a quantidade declarada.
ALTER TABLE pontuacao
    ADD CONSTRAINT ck_pontuacao_quantidade_homologada_limite
        CHECK (
            quantidade_homologada IS NULL
            OR quantidade_declarada IS NULL
            OR quantidade_homologada <= quantidade_declarada
        );

-- A pontuação homologada não poderá ultrapassar a pontuação declarada.
ALTER TABLE pontuacao
    ADD CONSTRAINT ck_pontuacao_pontos_homologados_limite
        CHECK (
            pontos_homologados IS NULL
            OR pontos_homologados <= pontos_declarados
        );

-- ----------------------------------------------------------------------------
-- Índices
-- ----------------------------------------------------------------------------

CREATE INDEX idx_pontuacao_atividade_declarada_id
    ON pontuacao (atividade_declarada_id);

CREATE INDEX idx_pontuacao_avaliacao_status
    ON pontuacao (avaliacao_id, status);

CREATE INDEX idx_pontuacao_avaliacao_criterio
    ON pontuacao (avaliacao_id, criterio_id);

-- Impede a geração de mais de uma pontuação ativa para a mesma atividade
-- dentro da mesma avaliação.
CREATE UNIQUE INDEX uk_pontuacao_avaliacao_atividade_ativa
    ON pontuacao (
        avaliacao_id,
        atividade_declarada_id
    )
    WHERE deleted_at IS NULL
      AND atividade_declarada_id IS NOT NULL;

-- ----------------------------------------------------------------------------
-- Normalização dos registros existentes
-- ----------------------------------------------------------------------------

-- A tabela normalmente ainda estará vazia nesta fase.
-- Este comando apenas garante um status válido caso existam registros antigos.
UPDATE pontuacao
SET status = 'PENDENTE'
WHERE status IS NULL
   OR BTRIM(status) = '';

-- ----------------------------------------------------------------------------
-- Validações estruturais
-- ----------------------------------------------------------------------------

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'atividade_declarada'
          AND column_name = 'quantidade_declarada'
    ) THEN
        RAISE EXCEPTION
            'A coluna atividade_declarada.quantidade_declarada não foi criada.';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'pontuacao'
          AND column_name = 'atividade_declarada_id'
    ) THEN
        RAISE EXCEPTION
            'A coluna pontuacao.atividade_declarada_id não foi criada.';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'pontuacao'
          AND column_name = 'quantidade_declarada'
    ) THEN
        RAISE EXCEPTION
            'A coluna pontuacao.quantidade_declarada não foi criada.';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'pontuacao'
          AND column_name = 'quantidade_homologada'
    ) THEN
        RAISE EXCEPTION
            'A coluna pontuacao.quantidade_homologada não foi criada.';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'pontuacao'
          AND column_name = 'pontos_unitarios'
    ) THEN
        RAISE EXCEPTION
            'A coluna pontuacao.pontos_unitarios não foi criada.';
    END IF;
END $$;