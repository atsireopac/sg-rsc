-- ============================================================================
-- Carga dos critérios oficiais do RSC-PCCTAE
--
-- Total:
--   Grupo I   = 10 critérios
--   Grupo II  = 11 critérios
--   Grupo III =  3 critérios
--   Grupo IV  =  8 critérios
--   Grupo V   =  8 critérios
--   Grupo VI  = 19 critérios
--   Total      = 59 critérios
--
-- Tipos de cálculo:
--   POR_OCORRENCIA
--   POR_MES
--   POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES
-- ============================================================================

DO $$
BEGIN
    IF (
        SELECT COUNT(*)
        FROM grupo_criterio
        WHERE codigo IN (
            'GRUPO_I',
            'GRUPO_II',
            'GRUPO_III',
            'GRUPO_IV',
            'GRUPO_V',
            'GRUPO_VI'
        )
          AND ativo = TRUE
          AND deleted_at IS NULL
    ) <> 6 THEN
        RAISE EXCEPTION
            'Os seis grupos oficiais de critérios não estão cadastrados.';
    END IF;
END $$;

WITH criterios_oficiais (
    grupo_codigo,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ordem,
    tipo_calculo,
    observacao
) AS (

    VALUES

    -- ========================================================================
    -- GRUPO I
    -- Participação em Grupos de Trabalho, Comissões, Comitês, Núcleos,
    -- Representações ou Similares
    -- ========================================================================

    (
        'GRUPO_I',
        'G1-01',
        'Exercício do mandato como membro de conselhos superiores e conselhos de unidades e órgãos colegiados da Instituição Federal de Ensino.',
        'Por ano ou fração acima de seis meses',
        3.00,
        1,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-02',
        'Coordenação ou presidência de núcleos, representações, grupos de trabalho ou similares, comissões ou comitês previstos no âmbito da administração pública, regularmente instituídos ou reconhecidos pelo órgão ou pela entidade.',
        'Por designação',
        4.50,
        2,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-03',
        'Participação como membro de núcleos, representações, grupos de trabalho ou similares, comissões ou comitês previstos no âmbito da administração pública, regularmente instituídos.',
        'Por designação',
        3.00,
        3,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-04',
        'Participação como defensor dativo ou como membro de equipe designada em processos de apuração de materialidade e responsabilidade, como sindicância, processo administrativo disciplinar e tomada de contas especial.',
        'Por designação',
        3.00,
        4,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-05',
        'Atuação em atividades de organização, fiscalização ou execução de exame de seleção, vestibular ou concursos.',
        'Por designação',
        4.50,
        5,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-06',
        'Atuação em atividades de elaboração, revisão e/ou correção de provas de exame de seleção, vestibular ou concursos.',
        'Por designação',
        3.00,
        6,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-07',
        'Exercício de mandato em entidade sindical da categoria.',
        'Por ano ou fração acima de seis meses',
        1.50,
        7,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-08',
        'Participação como membro em programas ou projetos de políticas públicas externas à Instituição Federal de Ensino, desde que comprovada a obtenção de resultados institucionais relevantes.',
        'Por designação',
        3.00,
        8,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-09',
        'Representação legal da Instituição Federal de Ensino junto a órgãos e entidades do Poder Público ou responsabilidade técnica junto a órgãos de fiscalização, controle e regulação.',
        'Por designação',
        7.50,
        9,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_I',
        'G1-10',
        'Atuação técnica externa, formalmente autorizada ou reconhecida pela Instituição Federal de Ensino de lotação, em órgãos estatais ou paraestatais, escolas de governo, agências reguladoras ou organismos internacionais, com contribuição ou repercussão institucional.',
        'Por produto',
        4.50,
        10,
        'POR_OCORRENCIA',
        NULL
    ),

    -- ========================================================================
    -- GRUPO II
    -- Participação e Atuação em Projetos Institucionais, na Gestão, no Apoio
    -- ao Ensino, à Pesquisa, à Extensão, à Inovação e Assistência Especializada
    -- ========================================================================

    (
        'GRUPO_II',
        'G2-01',
        'Coordenação de projetos institucionais de ensino, pesquisa, extensão, gestão e inovação.',
        'Por projeto',
        7.50,
        1,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-02',
        'Participação em atividades técnicas e/ou especializadas em projetos, incluída a elaboração de projetos pedagógicos, programas e/ou ações institucionais de ensino, pesquisa, extensão, gestão e inovação.',
        'Por projeto',
        4.50,
        2,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-03',
        'Participação em comissão ou conselho editorial de livros, revistas, publicações científicas ou outras publicações acadêmicas.',
        'Por mandato',
        7.50,
        3,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-04',
        'Participação em atividade de Cooperação Técnica Interinstitucional em projetos institucionais.',
        'Por projeto',
        3.00,
        4,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-05',
        'Participação em atividades de orientação, tutoria, preceptoria ou supervisão.',
        'Por designação',
        3.00,
        5,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-06',
        'Participação em atividades de produção ou reformulação de material acessível ou técnico de referência, como manuais e roteiros técnicos.',
        'Por produto',
        3.00,
        6,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-07',
        'Participação em atividade de avaliação de trabalho ou atuação como jurado em eventos acadêmicos, científicos, culturais, esportivos e técnicos.',
        'Por evento',
        3.00,
        7,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-08',
        'Participação em atividade institucional de produção audiovisual, artística, exposição, podcast ou outras formas de apresentação.',
        'Por projeto',
        3.00,
        8,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-09',
        'Participação em programas de formação continuada e/ou ações de desenvolvimento de competências, desde que não utilizada para fins de aceleração da promoção na carreira.',
        'Por capacitação',
        1.00,
        9,
        'POR_OCORRENCIA',
        'Carga horária mínima de dez horas.'
    ),
    (
        'GRUPO_II',
        'G2-10',
        'Desempenho de atividade técnica especializada, formalmente reconhecida pela Instituição Federal de Ensino, com demonstração de domínio técnico diferenciado e contribuição institucional relevante na área de atuação.',
        'Por ano ou fração acima de seis meses',
        1.00,
        10,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_II',
        'G2-11',
        'Participação em capacitação, fórum, oficina, workshop e congresso, vinculada aos interesses da Instituição Federal de Ensino.',
        'Por evento',
        1.00,
        11,
        'POR_OCORRENCIA',
        'Carga horária mínima de dez horas.'
    ),

    -- ========================================================================
    -- GRUPO III
    -- Recebimento de Premiação em Evento de Reconhecimento Público
    -- ========================================================================

    (
        'GRUPO_III',
        'G3-01',
        'Recebimento de premiação de âmbito internacional por projeto implementado na administração pública.',
        'Por prêmio',
        20.00,
        1,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_III',
        'G3-02',
        'Recebimento de premiação de âmbito nacional por projeto implementado na administração pública.',
        'Por prêmio',
        15.00,
        2,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_III',
        'G3-03',
        'Recebimento de premiação de âmbito local ou institucional, formalmente instituído, por projeto implementado na administração pública.',
        'Por prêmio',
        7.50,
        3,
        'POR_OCORRENCIA',
        NULL
    ),

    -- ========================================================================
    -- GRUPO IV
    -- Designação para Assunção de Responsabilidades Técnico-Administrativas
    -- ou Especializadas
    -- ========================================================================

    (
        'GRUPO_IV',
        'G4-01',
        'Atuação tecnicamente qualificada na operação, implantação, suporte ou apoio ao desenvolvimento, parametrização ou aperfeiçoamento de sistemas estruturantes da administração pública.',
        'Por sistema',
        4.50,
        1,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_IV',
        'G4-02',
        'Elaboração de projeto básico ou de termo de referência, ou participação como membro de equipe de planejamento de contratação.',
        'Por designação',
        3.00,
        2,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_IV',
        'G4-03',
        'Exercício de atividades de gestão ou fiscalização de contratos de aquisição, serviços, convênios, acordos ou instrumentos correlatos.',
        'Por designação',
        4.50,
        3,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_IV',
        'G4-04',
        'Exercício de atividades relacionadas à licitação e às suas excepcionalidades.',
        'Por ano ou fração acima de seis meses',
        3.00,
        4,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_IV',
        'G4-05',
        'Participação em atividades de apoio técnico especializado em políticas, programas e ações de promoção na área de saúde humana, animal e ambiente, de acessibilidade ou diversidade, de interesse institucional.',
        'Por ano ou fração acima de seis meses',
        3.00,
        5,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_IV',
        'G4-06',
        'Atuação tecnicamente qualificada em ambientes ou processos que demandem condições especiais de segurança, cuidado ou conformidade com requisitos legais e regulatórios.',
        'Por ano ou fração acima de seis meses',
        3.00,
        6,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        'Aplicável desde que o servidor não receba adicional de periculosidade ou insalubridade em razão das mesmas condições.'
    ),
    (
        'GRUPO_IV',
        'G4-07',
        'Atuação em sistemas e/ou processos de trabalho institucionais em ensino, pesquisa, extensão, gestão e inovação, desde que não constitua atividade habitual do cargo.',
        'Por designação',
        3.00,
        7,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_IV',
        'G4-08',
        'Atuação como responsável por setor ou unidade, formalmente designado, desde que a designação não gere pagamento de remuneração.',
        'Por ano ou fração acima de seis meses',
        4.50,
        8,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),

    -- ========================================================================
    -- GRUPO V
    -- Exercício de Função ou Cargo de Direção ou Assessoramento Institucional
    -- ========================================================================

    (
        'GRUPO_V',
        'G5-01',
        'Exercício de cargo de direção CD-02 ou equivalente, como titular.',
        'Por ano ou fração acima de seis meses',
        9.00,
        1,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_V',
        'G5-02',
        'Exercício de cargo de direção CD-02 ou equivalente, como substituto.',
        'Por ano ou fração acima de seis meses',
        4.50,
        2,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_V',
        'G5-03',
        'Exercício de cargo de direção CD-03 ou CD-04, ou equivalente, como titular.',
        'Por ano ou fração acima de seis meses',
        7.50,
        3,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_V',
        'G5-04',
        'Exercício de cargo de direção CD-03 ou CD-04, ou equivalente, como substituto.',
        'Por ano ou fração acima de seis meses',
        3.00,
        4,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_V',
        'G5-05',
        'Exercício de função gratificada FG-01 ou FG-02, ou equivalente, como titular.',
        'Por ano ou fração acima de seis meses',
        4.50,
        5,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_V',
        'G5-06',
        'Exercício de função gratificada FG-01 ou FG-02, ou equivalente, como substituto.',
        'Por ano ou fração acima de seis meses',
        1.50,
        6,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_V',
        'G5-07',
        'Exercício de função gratificada a partir da FG-03, ou equivalente, como titular.',
        'Por ano ou fração acima de seis meses',
        3.00,
        7,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),
    (
        'GRUPO_V',
        'G5-08',
        'Exercício de função gratificada a partir da FG-03, ou equivalente, como substituto.',
        'Por ano ou fração acima de seis meses',
        1.00,
        8,
        'POR_ANO_OU_FRACAO_ACIMA_SEIS_MESES',
        NULL
    ),

    -- ========================================================================
    -- GRUPO VI
    -- Produção, Prospecção e Difusão de Conhecimento Científico ou Técnico
    -- ========================================================================

    (
        'GRUPO_VI',
        'G6-01',
        'Carta patente relacionada aos interesses institucionais.',
        'Por patente',
        30.00,
        1,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-02',
        'Participação relevante no desenvolvimento de protótipos, depósitos e/ou registros de propriedade intelectual ou privilégio de invenção relacionada aos interesses institucionais.',
        'Por projeto',
        25.00,
        2,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-03',
        'Participação em transferência de tecnologia, licenciamento ou exploração de ativo tecnológico, como autor ou inventor, relacionada aos interesses institucionais.',
        'Por produto',
        20.00,
        3,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-04',
        'Conclusão de curso de educação formal superior ao exigido para o ingresso no cargo de que é titular e que não seja utilizado para percepção de Incentivo à Qualificação.',
        'Por curso',
        15.00,
        4,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-05',
        'Participação relevante na implantação ou desenvolvimento de produto, projeto, processo, técnica ou tecnologia de interesse institucional.',
        'Por produto',
        15.00,
        5,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-06',
        'Atuação em atividade de liderança ou vice-liderança de grupo de pesquisa ou extensão registrado em órgão ou sistema oficial de reconhecimento institucional.',
        'Por grupo de pesquisa',
        7.50,
        6,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-07',
        'Participação como membro em grupo de pesquisa devidamente registrado em órgão ou sistema oficial de reconhecimento institucional.',
        'Por projeto',
        3.00,
        7,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-08',
        'Aprovação de projeto para a captação de recursos para a Instituição Federal de Ensino.',
        'Por projeto',
        7.50,
        8,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-09',
        'Publicação ou organização de livro relacionado aos interesses institucionais, com ISBN e Conselho Editorial.',
        'Por produto',
        20.00,
        9,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-10',
        'Autoria ou coautoria de capítulo de livro, artigo publicado em revista especializada, jornal científico ou periódico, relacionado aos interesses institucionais.',
        'Por publicação',
        7.50,
        10,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-11',
        'Apresentação de trabalho de interesse institucional em congresso, seminário ou outros eventos.',
        'Por produto',
        4.50,
        11,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-12',
        'Produção de material técnico, científico, metodológico ou administrativo estruturado que vise à difusão do conhecimento.',
        'Por produto',
        4.50,
        12,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-13',
        'Avaliação de projeto de ensino, pesquisa, extensão e/ou inovação.',
        'Por projeto',
        4.50,
        13,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-14',
        'Participação em atividade de difusão ou apoio à formação institucional, como expositor, facilitador ou colaborador.',
        'Por evento',
        3.00,
        14,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-15',
        'Atuação formalmente autorizada como instrutor, tutor, palestrante, autor de conteúdo técnico ou orientador em ação formativa estruturada de interesse institucional, prevista em plano ou programa de desenvolvimento de pessoas.',
        'Por curso',
        4.50,
        15,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-16',
        'Atuação na coordenação de congresso, simpósio ou seminário de interesse institucional.',
        'Por evento',
        3.50,
        16,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-17',
        'Exercício de atividade de coorientação de trabalho de conclusão de curso em diferentes modalidades de ensino.',
        'Por evento',
        4.50,
        17,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-18',
        'Autoria de obra artística ou cultural registrada, com contribuição ou repercussão institucional comprovada.',
        'Por produto',
        3.00,
        18,
        'POR_OCORRENCIA',
        NULL
    ),
    (
        'GRUPO_VI',
        'G6-19',
        'Atuação institucional no enfrentamento de situações de surto, epidemia e pandemia.',
        'Por mês',
        1.00,
        19,
        'POR_MES',
        NULL
    )
)

INSERT INTO criterio (
    requisito_id,
    grupo_criterio_id,
    codigo,
    descricao,
    unidade_medida,
    pontos,
    ordem,
    tipo_calculo,
    observacao,
    ativo,
    created_at,
    created_by,
    updated_at,
    updated_by
)
SELECT
    NULL,
    gc.id,
    co.codigo,
    co.descricao,
    co.unidade_medida,
    co.pontos,
    co.ordem,
    co.tipo_calculo,
    co.observacao,
    TRUE,
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP,
    'system'
FROM criterios_oficiais co
JOIN grupo_criterio gc
  ON gc.codigo = co.grupo_codigo
 AND gc.ativo = TRUE
 AND gc.deleted_at IS NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM criterio c
    WHERE c.codigo = co.codigo
      AND c.grupo_criterio_id = gc.id
      AND c.deleted_at IS NULL
);

-- ============================================================================
-- Validação da carga
-- ============================================================================

DO $$
DECLARE
    quantidade_criterios INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO quantidade_criterios
    FROM criterio c
    JOIN grupo_criterio gc
      ON gc.id = c.grupo_criterio_id
    WHERE c.ativo = TRUE
      AND c.deleted_at IS NULL
      AND gc.codigo IN (
          'GRUPO_I',
          'GRUPO_II',
          'GRUPO_III',
          'GRUPO_IV',
          'GRUPO_V',
          'GRUPO_VI'
      );

    IF quantidade_criterios <> 59 THEN
        RAISE EXCEPTION
            'Carga oficial incompleta. Esperados 59 critérios, encontrados %.',
            quantidade_criterios;
    END IF;
END $$;