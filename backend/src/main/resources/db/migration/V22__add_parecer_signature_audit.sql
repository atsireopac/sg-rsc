ALTER TABLE parecer
    ADD COLUMN data_assinatura TIMESTAMP WITH TIME ZONE;

ALTER TABLE parecer
    ADD COLUMN usuario_assinatura VARCHAR(100);

COMMENT ON COLUMN parecer.data_assinatura IS
    'Data e hora em que o parecer foi assinado logicamente no SG-RSC.';

COMMENT ON COLUMN parecer.usuario_assinatura IS
    'Identificador do usuário responsável pela assinatura lógica do parecer.';