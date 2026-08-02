-- ============================================================================
-- Evolução da modelagem da Base Legal do RSC-PCCTAE
--
-- Esta migration cria:
--   1. grupos oficiais de critérios;
--   2. novos atributos dos critérios;
--   3. regras dos níveis de RSC;
--   4. regras parametrizadas de complexidade por grupo.
--
-- Os dados referenciais simplificados da V10 serão tratados posteriormente,
-- durante a carga oficial dos grupos e critérios.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- Grupo de critérios
-- ----------------------------------------------------------------------------

CREATE TABLE grupo_criterio (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    legislacao_id BIGINT NOT NULL,

    codigo VARCHAR(20) NOT NULL,
    numero_romano VARCHAR(10) NOT NULL,
    nome VARCHAR(500) NOT NULL,
    descricao TEXT,
    ordem INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',

    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_grupo_criterio_legislacao
        FOREIGN KEY (legislacao_id)
        REFERENCES legislacao (id),

    CONSTRAINT uk_grupo_criterio_legislacao_codigo
        UNIQUE (legislacao_id, codigo),

    CONSTRAINT uk_grupo_criterio_legislacao_ordem
        UNIQUE (legislacao_id, ordem),

    CONSTRAINT ck_grupo_criterio_ordem_positiva
        CHECK (ordem > 0)
);

CREATE INDEX idx_grupo_criterio_legislacao_id
    ON grupo_criterio (legislacao_id);

CREATE INDEX idx_grupo_criterio_ativo
    ON grupo_criterio (ativo);

-- ----------------------------------------------------------------------------
-- Evolução da tabela criterio
-- ----------------------------------------------------------------------------

ALTER TABLE criterio
    ADD COLUMN grupo_criterio_id BIGINT;

ALTER TABLE criterio
    ADD COLUMN ordem INTEGER;

ALTER TABLE criterio
    ADD COLUMN tipo_calculo VARCHAR(60);

ALTER TABLE criterio
    ADD COLUMN observacao TEXT;

ALTER TABLE criterio
    ADD CONSTRAINT fk_criterio_grupo_criterio
        FOREIGN KEY (grupo_criterio_id)
        REFERENCES grupo_criterio (id);

ALTER TABLE criterio
    ADD CONSTRAINT ck_criterio_ordem_positiva
        CHECK (ordem IS NULL OR ordem > 0);

CREATE INDEX idx_criterio_grupo_criterio_id
    ON criterio (grupo_criterio_id);

CREATE INDEX idx_criterio_tipo_calculo
    ON criterio (tipo_calculo);

-- O requisito_id permanece obrigatório nesta etapa para preservar os registros
-- simplificados criados pela V10. Na migration da carga oficial, os critérios
-- oficiais serão associados aos grupos e a obrigatoriedade será reavaliada.

-- ----------------------------------------------------------------------------
-- Evolução da tabela nivel_rsc
-- ----------------------------------------------------------------------------

ALTER TABLE nivel_rsc
    ADD COLUMN pontos_minimos NUMERIC(6,2);

ALTER TABLE nivel_rsc
    ADD COLUMN itens_minimos INTEGER;

ALTER TABLE nivel_rsc
    ADD CONSTRAINT ck_nivel_rsc_pontos_minimos
        CHECK (pontos_minimos IS NULL OR pontos_minimos >= 0);

ALTER TABLE nivel_rsc
    ADD CONSTRAINT ck_nivel_rsc_itens_minimos
        CHECK (itens_minimos IS NULL OR itens_minimos >= 0);

-- ----------------------------------------------------------------------------
-- Regra de complexidade do nível
--
-- Exemplo:
-- RSC IV exige pelo menos um item pertencente a um dos grupos II, IV, V ou VI.
-- ----------------------------------------------------------------------------

CREATE TABLE regra_complexidade_nivel (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    nivel_rsc_id BIGINT NOT NULL,
    quantidade_minima_itens INTEGER NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',

    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_regra_complexidade_nivel
        FOREIGN KEY (nivel_rsc_id)
        REFERENCES nivel_rsc (id),

    CONSTRAINT ck_regra_complexidade_quantidade_positiva
        CHECK (quantidade_minima_itens > 0)
);

CREATE INDEX idx_regra_complexidade_nivel_rsc_id
    ON regra_complexidade_nivel (nivel_rsc_id);

-- ----------------------------------------------------------------------------
-- Grupos aceitos em cada regra de complexidade
-- ----------------------------------------------------------------------------

CREATE TABLE regra_complexidade_grupo (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    regra_complexidade_nivel_id BIGINT NOT NULL,
    grupo_criterio_id BIGINT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',

    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_regra_complexidade_grupo_regra
        FOREIGN KEY (regra_complexidade_nivel_id)
        REFERENCES regra_complexidade_nivel (id),

    CONSTRAINT fk_regra_complexidade_grupo_grupo
        FOREIGN KEY (grupo_criterio_id)
        REFERENCES grupo_criterio (id),

    CONSTRAINT uk_regra_complexidade_grupo
        UNIQUE (
            regra_complexidade_nivel_id,
            grupo_criterio_id
        )
);

CREATE INDEX idx_regra_complexidade_grupo_regra_id
    ON regra_complexidade_grupo (regra_complexidade_nivel_id);

CREATE INDEX idx_regra_complexidade_grupo_grupo_id
    ON regra_complexidade_grupo (grupo_criterio_id);