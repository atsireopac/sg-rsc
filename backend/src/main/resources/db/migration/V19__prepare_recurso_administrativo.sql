CREATE UNIQUE INDEX IF NOT EXISTS
    uk_recurso_solicitacao_pendente
ON recurso (solicitacao_id)
WHERE deleted_at IS NULL
  AND data_julgamento IS NULL;

CREATE INDEX IF NOT EXISTS
    idx_recurso_solicitacao_ativo
ON recurso (solicitacao_id)
WHERE deleted_at IS NULL;