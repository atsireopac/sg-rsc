CREATE TABLE situacao_funcional (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE nivel_rsc (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE status_solicitacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE resultado_solicitacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE servidor (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    siape VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(200) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(200) NOT NULL UNIQUE,
    cargo VARCHAR(200) NOT NULL,
    classe VARCHAR(50) NOT NULL,
    nivel VARCHAR(20) NOT NULL,
    padrao VARCHAR(20) NOT NULL,
    unidade VARCHAR(200) NOT NULL,
    campus VARCHAR(100),
    situacao_funcional_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_servidor_situacao_funcional
        FOREIGN KEY (situacao_funcional_id) REFERENCES situacao_funcional (id)
);

CREATE TABLE solicitacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numero_protocolo VARCHAR(30) NOT NULL UNIQUE,
    numero_processo VARCHAR(50) UNIQUE,
    servidor_id BIGINT NOT NULL,
    nivel_rsc_id BIGINT NOT NULL,
    status_solicitacao_id BIGINT NOT NULL,
    resultado_solicitacao_id BIGINT,
    data_solicitacao TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_protocolo TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_encerramento TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_solicitacao_servidor
        FOREIGN KEY (servidor_id) REFERENCES servidor (id),
    CONSTRAINT fk_solicitacao_nivel_rsc
        FOREIGN KEY (nivel_rsc_id) REFERENCES nivel_rsc (id),
    CONSTRAINT fk_solicitacao_status
        FOREIGN KEY (status_solicitacao_id) REFERENCES status_solicitacao (id),
    CONSTRAINT fk_solicitacao_resultado
        FOREIGN KEY (resultado_solicitacao_id) REFERENCES resultado_solicitacao (id)
);

CREATE INDEX idx_servidor_situacao_funcional_id ON servidor (situacao_funcional_id);
CREATE INDEX idx_solicitacao_servidor_id ON solicitacao (servidor_id);
CREATE INDEX idx_solicitacao_nivel_rsc_id ON solicitacao (nivel_rsc_id);
CREATE INDEX idx_solicitacao_status_solicitacao_id ON solicitacao (status_solicitacao_id);
CREATE INDEX idx_solicitacao_resultado_solicitacao_id ON solicitacao (resultado_solicitacao_id);
CREATE INDEX idx_solicitacao_data_protocolo ON solicitacao (data_protocolo);

INSERT INTO situacao_funcional (codigo, nome, descricao) VALUES
    ('ATIVO', 'Ativo', 'Servidor ativo em exercício'),
    ('INATIVO', 'Inativo', 'Servidor afastado ou inativo'),
    ('APOSENTADO', 'Aposentado', 'Servidor aposentado');

INSERT INTO nivel_rsc (codigo, nome, descricao) VALUES
    ('NIVEL_1', 'Nível 1', 'Reconhecimento de saberes e competências de nível 1'),
    ('NIVEL_2', 'Nível 2', 'Reconhecimento de saberes e competências de nível 2'),
    ('NIVEL_3', 'Nível 3', 'Reconhecimento de saberes e competências de nível 3');

INSERT INTO status_solicitacao (codigo, nome, descricao) VALUES
    ('RASCUNHO', 'Rascunho', 'Solicitação em elaboração'),
    ('ENVIADA', 'Enviada', 'Solicitação enviada para análise'),
    ('EM_ANALISE', 'Em análise', 'Solicitação em avaliação'),
    ('CONCLUIDA', 'Concluída', 'Solicitação concluída');

INSERT INTO resultado_solicitacao (codigo, nome, descricao) VALUES
    ('PENDENTE', 'Pendente', 'Resultado ainda não definido'),
    ('DEFERIDO', 'Deferido', 'Solicitação deferida'),
    ('INDEFERIDO', 'Indeferido', 'Solicitação indeferida');
