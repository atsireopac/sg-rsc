ALTER TABLE status_solicitacao
    ALTER COLUMN codigo TYPE VARCHAR(50);

INSERT INTO status_solicitacao (codigo, nome, descricao) VALUES
    ('DOCUMENTACAO_INCOMPLETA', 'Documentação incompleta',
     'Solicitação com documentação obrigatória incompleta'),

    ('AGUARDANDO_ENVIO', 'Aguardando envio',
     'Solicitação pronta, aguardando confirmação de envio pelo servidor'),

    ('PROTOCOLADA', 'Protocolada',
     'Solicitação protocolada e encaminhada para análise'),

    ('AGUARDANDO_COMPLEMENTACAO', 'Aguardando complementação',
     'Solicitação aguardando complementação documental pelo servidor'),

    ('COMPLEMENTACAO_RECEBIDA', 'Complementação recebida',
     'Documentação complementar recebida'),

    ('EM_REANALISE', 'Em reanálise',
     'Solicitação em nova análise após complementação'),

    ('DEFERIDA', 'Deferida',
     'Solicitação deferida'),

    ('INDEFERIDA', 'Indeferida',
     'Solicitação indeferida'),

    ('RECURSO', 'Recurso',
     'Solicitação em fase de recurso'),

    ('DECISAO_FINAL', 'Decisão final',
     'Solicitação com decisão final registrada')
ON CONFLICT (codigo) DO NOTHING;
