INSERT INTO tipo_historico (
    codigo,
    nome,
    descricao,
    ativo
)
SELECT
    'PROCESSO_SEI_VINCULADO',
    'Processo SEI vinculado',
    'Processo administrativo do SEI vinculado à solicitação',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM tipo_historico
    WHERE codigo = 'PROCESSO_SEI_VINCULADO'
      AND deleted_at IS NULL
);