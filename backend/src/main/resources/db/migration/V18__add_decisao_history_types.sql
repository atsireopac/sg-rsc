INSERT INTO tipo_historico (
    codigo,
    nome,
    descricao,
    ativo,
    created_at,
    created_by,
    updated_at,
    updated_by
)
SELECT
    'DECISAO_ADMINISTRATIVA_CRIADA',
    'Decisão administrativa criada',
    'Decisão administrativa registrada para a avaliação',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'DECISAO_ADMINISTRATIVA_CRIADA'
);

INSERT INTO tipo_historico (
    codigo,
    nome,
    descricao,
    ativo,
    created_at,
    created_by,
    updated_at,
    updated_by
)
SELECT
    'DECISAO_ADMINISTRATIVA_ATUALIZADA',
    'Decisão administrativa atualizada',
    'Decisão administrativa alterada antes da assinatura',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'DECISAO_ADMINISTRATIVA_ATUALIZADA'
);

INSERT INTO tipo_historico (
    codigo,
    nome,
    descricao,
    ativo,
    created_at,
    created_by,
    updated_at,
    updated_by
)
SELECT
    'DECISAO_ADMINISTRATIVA_ASSINADA',
    'Decisão administrativa assinada',
    'Decisão administrativa assinada pela comissão',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'DECISAO_ADMINISTRATIVA_ASSINADA'
);

INSERT INTO tipo_historico (
    codigo,
    nome,
    descricao,
    ativo,
    created_at,
    created_by,
    updated_at,
    updated_by
)
SELECT
    'SOLICITACAO_DEFERIDA',
    'Solicitação deferida',
    'Solicitação encerrada com resultado deferido',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'SOLICITACAO_DEFERIDA'
);

INSERT INTO tipo_historico (
    codigo,
    nome,
    descricao,
    ativo,
    created_at,
    created_by,
    updated_at,
    updated_by
)
SELECT
    'SOLICITACAO_INDEFERIDA',
    'Solicitação indeferida',
    'Solicitação encerrada com resultado indeferido',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'SOLICITACAO_INDEFERIDA'
);

INSERT INTO tipo_historico (
    codigo,
    nome,
    descricao,
    ativo,
    created_at,
    created_by,
    updated_at,
    updated_by
)
SELECT
    'AVALIACAO_CONCLUIDA',
    'Avaliação concluída',
    'Avaliação encerrada após assinatura da decisão administrativa',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'AVALIACAO_CONCLUIDA'
);