CREATE TABLE decisao_administrativa (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    avaliacao_id BIGINT NOT NULL,
    parecer_id BIGINT NOT NULL,
    resultado_solicitacao_id BIGINT NOT NULL,

    fundamentacao TEXT NOT NULL,
    data_decisao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    versao INTEGER NOT NULL DEFAULT 1,
    assinada BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',

    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',

    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_decisao_administrativa_avaliacao
        FOREIGN KEY (avaliacao_id)
        REFERENCES avaliacao(id),

    CONSTRAINT fk_decisao_administrativa_parecer
        FOREIGN KEY (parecer_id)
        REFERENCES parecer(id),

    CONSTRAINT fk_decisao_administrativa_resultado
        FOREIGN KEY (resultado_solicitacao_id)
        REFERENCES resultado_solicitacao(id),

    CONSTRAINT ck_decisao_administrativa_versao
        CHECK (versao > 0)
);

CREATE INDEX idx_decisao_administrativa_avaliacao_id
    ON decisao_administrativa(avaliacao_id);

CREATE INDEX idx_decisao_administrativa_parecer_id
    ON decisao_administrativa(parecer_id);

CREATE INDEX idx_decisao_administrativa_resultado_id
    ON decisao_administrativa(resultado_solicitacao_id);

CREATE INDEX idx_decisao_administrativa_data_decisao
    ON decisao_administrativa(data_decisao);

CREATE UNIQUE INDEX uk_decisao_administrativa_avaliacao_versao_ativa
    ON decisao_administrativa(avaliacao_id, versao)
    WHERE deleted_at IS NULL;