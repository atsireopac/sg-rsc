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
    'AVALIACAO_INICIADA',
    'Avaliação iniciada',
    'Registra o início da avaliação de uma solicitação pela Comissão de RSC.',
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'AVALIACAO_INICIADA'
);