CREATE TABLE tipo_documento (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT,
    obrigatorio BOOLEAN NOT NULL DEFAULT FALSE,
    permite_pdf BOOLEAN NOT NULL DEFAULT TRUE,
    permite_imagem BOOLEAN NOT NULL DEFAULT TRUE,
    tamanho_maximo_mb INTEGER NOT NULL DEFAULT 10,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE documento (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    solicitacao_id BIGINT NOT NULL,
    tipo_documento_id BIGINT NOT NULL,
    nome_original VARCHAR(255) NOT NULL,
    nome_armazenado VARCHAR(255) NOT NULL UNIQUE,
    caminho_arquivo VARCHAR(500) NOT NULL,
    tamanho_bytes BIGINT NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    hash_arquivo VARCHAR(128) NOT NULL,
    data_envio TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL DEFAULT 'ENVIADO',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_documento_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacao (id),
    CONSTRAINT fk_documento_tipo_documento
        FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento (id)
);

CREATE TABLE comissao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descricao TEXT,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    ativa BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE membro_comissao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    comissao_id BIGINT NOT NULL,
    servidor_id BIGINT NOT NULL,
    papel VARCHAR(50) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_membro_comissao_comissao
        FOREIGN KEY (comissao_id) REFERENCES comissao (id),
    CONSTRAINT fk_membro_comissao_servidor
        FOREIGN KEY (servidor_id) REFERENCES servidor (id)
);

CREATE TABLE legislacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    numero VARCHAR(50) NOT NULL,
    ano INTEGER NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_publicacao DATE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE requisito (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    legislacao_id BIGINT NOT NULL,
    codigo VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_requisito_legislacao
        FOREIGN KEY (legislacao_id) REFERENCES legislacao (id)
);

CREATE TABLE criterio (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    requisito_id BIGINT NOT NULL,
    codigo VARCHAR(20) NOT NULL,
    descricao TEXT NOT NULL,
    unidade_medida VARCHAR(100) NOT NULL,
    pontos NUMERIC(6,2) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_criterio_requisito
        FOREIGN KEY (requisito_id) REFERENCES requisito (id)
);

CREATE TABLE avaliacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    solicitacao_id BIGINT NOT NULL,
    comissao_id BIGINT NOT NULL,
    data_inicio TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_fim TIMESTAMPTZ,
    observacoes TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_avaliacao_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacao (id),
    CONSTRAINT fk_avaliacao_comissao
        FOREIGN KEY (comissao_id) REFERENCES comissao (id)
);

CREATE TABLE pontuacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    avaliacao_id BIGINT NOT NULL,
    criterio_id BIGINT NOT NULL,
    pontos_declarados NUMERIC(6,2) NOT NULL,
    pontos_homologados NUMERIC(6,2),
    justificativa TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_pontuacao_avaliacao
        FOREIGN KEY (avaliacao_id) REFERENCES avaliacao (id),
    CONSTRAINT fk_pontuacao_criterio
        FOREIGN KEY (criterio_id) REFERENCES criterio (id)
);

CREATE TABLE tipo_parecer (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE parecer (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    avaliacao_id BIGINT NOT NULL,
    tipo_parecer_id BIGINT NOT NULL,
    texto TEXT NOT NULL,
    conclusao TEXT,
    data_emissao TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    versao INTEGER NOT NULL DEFAULT 1,
    assinado BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_parecer_avaliacao
        FOREIGN KEY (avaliacao_id) REFERENCES avaliacao (id),
    CONSTRAINT fk_parecer_tipo_parecer
        FOREIGN KEY (tipo_parecer_id) REFERENCES tipo_parecer (id)
);

CREATE TABLE recurso (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    solicitacao_id BIGINT NOT NULL,
    texto TEXT NOT NULL,
    data_interposicao TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resultado_solicitacao_id BIGINT,
    data_julgamento TIMESTAMPTZ,
    observacao_julgamento TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_recurso_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacao (id),
    CONSTRAINT fk_recurso_resultado_solicitacao
        FOREIGN KEY (resultado_solicitacao_id) REFERENCES resultado_solicitacao (id)
);

CREATE TABLE tipo_historico (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE historico (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    solicitacao_id BIGINT NOT NULL,
    tipo_historico_id BIGINT NOT NULL,
    descricao TEXT NOT NULL,
    usuario VARCHAR(200) NOT NULL,
    data_evento TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_historico_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacao (id),
    CONSTRAINT fk_historico_tipo_historico
        FOREIGN KEY (tipo_historico_id) REFERENCES tipo_historico (id)
);

CREATE TABLE tipo_notificacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100)
);

CREATE TABLE notificacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    solicitacao_id BIGINT,
    tipo_notificacao_id BIGINT NOT NULL,
    destinatario VARCHAR(200) NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    mensagem TEXT NOT NULL,
    data_envio TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lida BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NOT NULL DEFAULT 'system',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL DEFAULT 'system',
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(100),
    CONSTRAINT fk_notificacao_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacao (id),
    CONSTRAINT fk_notificacao_tipo_notificacao
        FOREIGN KEY (tipo_notificacao_id) REFERENCES tipo_notificacao (id)
);

CREATE INDEX idx_documento_solicitacao_id ON documento (solicitacao_id);
CREATE INDEX idx_documento_tipo_documento_id ON documento (tipo_documento_id);
CREATE INDEX idx_documento_hash_arquivo ON documento (hash_arquivo);

CREATE INDEX idx_membro_comissao_comissao_id ON membro_comissao (comissao_id);
CREATE INDEX idx_membro_comissao_servidor_id ON membro_comissao (servidor_id);

CREATE INDEX idx_requisito_legislacao_id ON requisito (legislacao_id);
CREATE INDEX idx_criterio_requisito_id ON criterio (requisito_id);

CREATE INDEX idx_avaliacao_solicitacao_id ON avaliacao (solicitacao_id);
CREATE INDEX idx_avaliacao_comissao_id ON avaliacao (comissao_id);
CREATE INDEX idx_avaliacao_data_inicio ON avaliacao (data_inicio);

CREATE INDEX idx_pontuacao_avaliacao_id ON pontuacao (avaliacao_id);
CREATE INDEX idx_pontuacao_criterio_id ON pontuacao (criterio_id);

CREATE INDEX idx_parecer_avaliacao_id ON parecer (avaliacao_id);
CREATE INDEX idx_parecer_tipo_parecer_id ON parecer (tipo_parecer_id);
CREATE INDEX idx_parecer_data_emissao ON parecer (data_emissao);

CREATE INDEX idx_recurso_solicitacao_id ON recurso (solicitacao_id);
CREATE INDEX idx_recurso_resultado_solicitacao_id ON recurso (resultado_solicitacao_id);
CREATE INDEX idx_recurso_data_interposicao ON recurso (data_interposicao);

CREATE INDEX idx_historico_solicitacao_id ON historico (solicitacao_id);
CREATE INDEX idx_historico_tipo_historico_id ON historico (tipo_historico_id);
CREATE INDEX idx_historico_data_evento ON historico (data_evento);

CREATE INDEX idx_notificacao_solicitacao_id ON notificacao (solicitacao_id);
CREATE INDEX idx_notificacao_tipo_notificacao_id ON notificacao (tipo_notificacao_id);
CREATE INDEX idx_notificacao_destinatario ON notificacao (destinatario);
CREATE INDEX idx_notificacao_data_envio ON notificacao (data_envio);

INSERT INTO tipo_documento (codigo, nome, descricao, obrigatorio, permite_pdf, permite_imagem, tamanho_maximo_mb, ativo) VALUES
    ('MEMORIAL', 'Memorial Descritivo', 'Memorial descritivo da solicitação', TRUE, TRUE, FALSE, 20, TRUE),
    ('DIPLOMA', 'Diploma ou Certificado', 'Diploma ou certificado de formação', FALSE, TRUE, TRUE, 10, TRUE),
    ('PORTARIA', 'Portaria', 'Portaria institucional', FALSE, TRUE, FALSE, 10, TRUE),
    ('DECLARACAO', 'Declaração Institucional', 'Declaração emitida por instituição', FALSE, TRUE, FALSE, 10, TRUE),
    ('CERTIFICADO', 'Certificado de Capacitação', 'Certificado de capacitação', FALSE, TRUE, TRUE, 10, TRUE),
    ('PUBLICACAO', 'Publicação Técnica ou Científica', 'Publicação técnica ou científica', FALSE, TRUE, TRUE, 10, TRUE),
    ('RELATORIO', 'Relatório Técnico', 'Relatório técnico', FALSE, TRUE, TRUE, 10, TRUE),
    ('OUTRO', 'Outro Documento', 'Outro tipo de documento', FALSE, TRUE, TRUE, 10, TRUE);

INSERT INTO tipo_parecer (codigo, nome, descricao, ativo) VALUES
    ('PRELIMINAR', 'Parecer Preliminar', 'Parecer preliminar emitido na avaliação', TRUE),
    ('FINAL', 'Parecer Final', 'Parecer final da comissão', TRUE),
    ('POS_RECURSO', 'Parecer após Recurso', 'Parecer emitido após análise de recurso', TRUE);

INSERT INTO tipo_historico (codigo, nome, descricao, ativo) VALUES
    ('SOLICITACAO_CRIADA', 'Solicitação criada', 'Solicitação criada no sistema', TRUE),
    ('DOCUMENTO_ANEXADO', 'Documento anexado', 'Documento anexado à solicitação', TRUE),
    ('AVALIACAO_INICIADA', 'Avaliação iniciada', 'Avaliação iniciada pela comissão', TRUE),
    ('PARECER_EMITIDO', 'Parecer emitido', 'Parecer emitido pela comissão', TRUE),
    ('RESULTADO_PUBLICADO', 'Resultado publicado', 'Resultado da solicitação publicado', TRUE),
    ('RECURSO_INTERPOSTO', 'Recurso interposto', 'Recurso interposto pelo servidor', TRUE),
    ('RECURSO_JULGADO', 'Recurso julgado', 'Recurso julgado pela comissão', TRUE),
    ('SOLICITACAO_ENCERRADA', 'Solicitação encerrada', 'Solicitação encerrada no sistema', TRUE);

INSERT INTO tipo_notificacao (codigo, nome, descricao, ativo) VALUES
    ('SOLICITACAO_PROTOCOLADA', 'Solicitação protocolada', 'Notificação de protocolo da solicitação', TRUE),
    ('PENDENCIA_IDENTIFICADA', 'Pendência identificada', 'Notificação de pendência no processo', TRUE),
    ('AVALIACAO_INICIADA', 'Avaliação iniciada', 'Notificação de início de avaliação', TRUE),
    ('PARECER_EMITIDO', 'Parecer emitido', 'Notificação de emissão de parecer', TRUE),
    ('RESULTADO_PUBLICADO', 'Resultado publicado', 'Notificação de publicação do resultado', TRUE),
    ('RECURSO_RECEBIDO', 'Recurso recebido', 'Notificação de recebimento de recurso', TRUE),
    ('PROCESSO_ENCERRADO', 'Processo encerrado', 'Notificação de encerramento do processo', TRUE);
