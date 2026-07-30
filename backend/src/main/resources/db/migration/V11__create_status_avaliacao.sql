CREATE TABLE status_avaliacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),

    updated_at TIMESTAMP,
    updated_by VARCHAR(255),

    deleted_at TIMESTAMP,
    deleted_by VARCHAR(255)
);

INSERT INTO status_avaliacao (
    codigo,
    nome,
    descricao,
    ativo,
    created_at
) VALUES
(
    'EM_ANDAMENTO',
    'Em Andamento',
    'Avaliação iniciada pela comissão.',
    TRUE,
    CURRENT_TIMESTAMP
),
(
    'CONCLUIDA',
    'Concluída',
    'Avaliação concluída pela comissão.',
    TRUE,
    CURRENT_TIMESTAMP
),
(
    'CANCELADA',
    'Cancelada',
    'Avaliação cancelada.',
    TRUE,
    CURRENT_TIMESTAMP
);

ALTER TABLE avaliacao
ADD COLUMN status_avaliacao_id BIGINT;

UPDATE avaliacao
SET status_avaliacao_id = (
    SELECT id
    FROM status_avaliacao
    WHERE codigo = 'EM_ANDAMENTO'
);

ALTER TABLE avaliacao
ALTER COLUMN status_avaliacao_id SET NOT NULL;

ALTER TABLE avaliacao
ADD CONSTRAINT fk_avaliacao_status_avaliacao
FOREIGN KEY (status_avaliacao_id)
REFERENCES status_avaliacao(id);
