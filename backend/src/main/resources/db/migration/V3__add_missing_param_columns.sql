DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'nivel_rsc'
          AND column_name = 'ativo'
    ) THEN
        ALTER TABLE nivel_rsc ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'situacao_funcional'
          AND column_name = 'ativo'
    ) THEN
        ALTER TABLE situacao_funcional ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'status_solicitacao'
          AND column_name = 'ativo'
    ) THEN
        ALTER TABLE status_solicitacao ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'resultado_solicitacao'
          AND column_name = 'ativo'
    ) THEN
        ALTER TABLE resultado_solicitacao ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
    END IF;
END $$;
