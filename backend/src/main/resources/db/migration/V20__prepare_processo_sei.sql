ALTER TABLE solicitacao
    ADD COLUMN IF NOT EXISTS data_abertura_processo
        TIMESTAMP WITH TIME ZONE;

ALTER TABLE solicitacao
    ADD COLUMN IF NOT EXISTS usuario_protocolo
        VARCHAR(100);

CREATE INDEX IF NOT EXISTS
    idx_solicitacao_data_abertura_processo
ON solicitacao (data_abertura_processo)
WHERE data_abertura_processo IS NOT NULL;