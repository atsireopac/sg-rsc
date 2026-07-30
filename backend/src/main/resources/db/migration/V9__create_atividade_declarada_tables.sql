CREATE TABLE atividade_declarada (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    solicitacao_id BIGINT NOT NULL,
    criterio_pretendido_id BIGINT,

    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,

    data_inicio DATE,
    data_fim DATE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',

    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_atividade_declarada_solicitacao
        FOREIGN KEY (solicitacao_id)
        REFERENCES solicitacao (id),

    CONSTRAINT fk_atividade_declarada_criterio_pretendido
        FOREIGN KEY (criterio_pretendido_id)
        REFERENCES criterio (id),

    CONSTRAINT ck_atividade_declarada_titulo_nao_vazio
        CHECK (BTRIM(titulo) <> ''),

    CONSTRAINT ck_atividade_declarada_descricao_nao_vazia
        CHECK (BTRIM(descricao) <> ''),

    CONSTRAINT ck_atividade_declarada_periodo
        CHECK (
            data_inicio IS NULL
            OR data_fim IS NULL
            OR data_fim >= data_inicio
        )
);

CREATE TABLE atividade_declarada_documento (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    atividade_declarada_id BIGINT NOT NULL,
    documento_id BIGINT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',

    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_atividade_declarada_documento_atividade
        FOREIGN KEY (atividade_declarada_id)
        REFERENCES atividade_declarada (id),

    CONSTRAINT fk_atividade_declarada_documento_documento
        FOREIGN KEY (documento_id)
        REFERENCES documento (id)
);

CREATE INDEX idx_atividade_declarada_solicitacao_id
    ON atividade_declarada (solicitacao_id);

CREATE INDEX idx_atividade_declarada_criterio_pretendido_id
    ON atividade_declarada (criterio_pretendido_id);

CREATE INDEX idx_atividade_declarada_deleted_at
    ON atividade_declarada (deleted_at);

CREATE INDEX idx_atividade_declarada_documento_atividade_id
    ON atividade_declarada_documento (atividade_declarada_id);

CREATE INDEX idx_atividade_declarada_documento_documento_id
    ON atividade_declarada_documento (documento_id);

CREATE INDEX idx_atividade_declarada_documento_deleted_at
    ON atividade_declarada_documento (deleted_at);

CREATE UNIQUE INDEX uk_atividade_declarada_documento_ativo
    ON atividade_declarada_documento (
        atividade_declarada_id,
        documento_id
    )
    WHERE deleted_at IS NULL;
