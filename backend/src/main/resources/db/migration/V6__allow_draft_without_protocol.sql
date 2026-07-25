ALTER TABLE solicitacao
    ALTER COLUMN numero_protocolo DROP NOT NULL;

ALTER TABLE solicitacao
    ALTER COLUMN data_protocolo DROP NOT NULL;

ALTER TABLE solicitacao
    ALTER COLUMN data_protocolo DROP DEFAULT;
