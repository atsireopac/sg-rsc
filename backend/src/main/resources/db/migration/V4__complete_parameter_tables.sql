DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'nivel_rsc'
          AND column_name = 'percentual_incentivo'
    ) THEN
        ALTER TABLE nivel_rsc
            ADD COLUMN percentual_incentivo NUMERIC(5,2);
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'status_solicitacao'
          AND column_name = 'ordem'
    ) THEN
        ALTER TABLE status_solicitacao
            ADD COLUMN ordem INTEGER;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'status_solicitacao'
          AND column_name = 'permite_edicao'
    ) THEN
        ALTER TABLE status_solicitacao
            ADD COLUMN permite_edicao BOOLEAN NOT NULL DEFAULT TRUE;
    END IF;
END $$;
