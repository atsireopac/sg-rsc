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
    'PARECER_EMITIDO',
    'Parecer técnico emitido',
    'Parecer técnico emitido para a avaliação',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'PARECER_EMITIDO'
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
    'PARECER_ATUALIZADO',
    'Parecer técnico atualizado',
    'Parecer técnico alterado antes da assinatura',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'PARECER_ATUALIZADO'
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
    'PARECER_ASSINADO',
    'Parecer técnico assinado',
    'Parecer técnico assinado pela comissão',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'PARECER_ASSINADO'
);