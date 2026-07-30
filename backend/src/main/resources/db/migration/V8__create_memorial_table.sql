CREATE TABLE memorial (
    id BIGSERIAL PRIMARY KEY,

    solicitacao_id BIGINT NOT NULL,

    texto TEXT NOT NULL,

    versao INTEGER NOT NULL DEFAULT 1,

    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,

    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(100) NOT NULL,

    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_memorial_solicitacao
        FOREIGN KEY (solicitacao_id)
        REFERENCES solicitacao(id),

    CONSTRAINT ck_memorial_versao
        CHECK (versao > 0)
);

CREATE INDEX idx_memorial_solicitacao_id
    ON memorial (solicitacao_id);

CREATE INDEX idx_memorial_deleted_at
    ON memorial (deleted_at);

CREATE UNIQUE INDEX uk_memorial_solicitacao_ativo
    ON memorial (solicitacao_id)
    WHERE deleted_at IS NULL;
